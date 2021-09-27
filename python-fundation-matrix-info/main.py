# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import json
import queue
import random
import re
import threading
import urllib.request

import pymysql
import requests
from bs4 import BeautifulSoup

import Property
from Property import SqlEntity

# 成员变量
sqlEntity = SqlEntity()
database = pymysql.connect(host=sqlEntity.dbHost, user=sqlEntity.userName, password=sqlEntity.password,
                           port=sqlEntity.port, db=sqlEntity.dbName, charset=SqlEntity.charset)


# 请求所有基金代码
def get_fund_info():
    response_all_funds = urllib.request.urlopen('http://fund.eastmoney.com/js/fundcode_search.js')
    all_funds_txt = response_all_funds.read()
    all_funds_txt = all_funds_txt.decode("utf-8")
    all_funds_txt = all_funds_txt[all_funds_txt.find('=') + 2:all_funds_txt.rfind(';')]
    all_funds_list = json.loads(all_funds_txt)
    return all_funds_list


# 加载数据库中的基金代码
def load_fund_info():
    cursor = database.cursor()
    cursor.execute("SELECT code FROM fund_info where version = {}".format(Property.fund_version))
    fund_list = cursor.fetchall()
    cursor.close()
    return fund_list


# 更新基金代码到数据库
def update_fund_info():
    # 打开数据库连接
    cursor = database.cursor()
    all_funds_list = get_fund_info()
    for fund in all_funds_list:
        sqlStr = "INSERT INTO fund_info(time,code,nameWord,name,type,namePinYin,version) VALUES('{}','{}','{}','{}','{}','0')"
        sqlStrCompile = sqlStr.format(fund[0], fund[1], fund[2], fund[3], fund[4])
        cursor.execute(sqlStrCompile)
    database.commit()
    cursor.close()


def update_fund_state(fund_code):
    # 打开数据库连接
    cursor = database.cursor()
    sqlStr = "UPDATE fund_info SET version = {} WHERE code = {}".format(Property.fund_version + 1, fund_code)
    cursor.execute(sqlStr)
    database.commit()
    cursor.close()


# 更新持仓信息到数据库
def update_fund_holding(fund_code):
    holdings = get_fund_holding(fund_code)
    _cursor = database.cursor()
    for fundHolding in holdings:
        sqlStr = "INSERT INTO fund_holding(fundCode,stockCode,stockName,equity,holdingNum,holdingPrice,version) VALUES('{}','{}','{}','{}','{}','{}','0')"
        sqlStrCompile = sqlStr.format(fundHolding["fundCode"], fundHolding['stockCode'], fundHolding['stockName'],
                                      float(fundHolding['equity']), float(fundHolding['holdingNum']),
                                      float(fundHolding['holdingPrice']))
        log("update_fund_holding", sqlStrCompile)
        _cursor.execute(sqlStrCompile)
    _cursor.close()
    database.commit()


# 请求持仓信息
def get_fund_holding(fund_code):
    header = {
        "User-Agent": random.choice(Property.user_agent_list),
        "Referer": random.choice(Property.referer_list),
    }
    url = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jjcc&code=" + str(
        fund_code) + "&topline=10&year=2021&month=6"
    log("get_fund_holding", url)
    req = requests.get(
        url,
        timeout=3,
        headers=header,
    )
    fundHoldingList = []
    req_content = req.text.replace('var apidata={ content:"', '')
    try:
        pattern = re.compile(r'",arryear:.*')
        html = re.sub(pattern, '', req_content)
        soup = BeautifulSoup(html, 'lxml')
        trs = soup.table.tbody.find_all("tr")
        for tr in trs:
            td = tr.find_all("td")
            fundHolding = {'fundCode': fund_code, 'stockCode': td[1].string, 'stockName': td[2].string.replace("'", ""),
                           'equity': td[6].string.replace("%", "").replace(",", ""),
                           'holdingNum': td[7].string.replace(",", ""), 'holdingPrice': td[8].string.replace(",", "")}
            fundHoldingList.append(fundHolding)
        # log("get_fund_holding", fundHoldingList)
        update_fund_state(fund_code)
        return fundHoldingList
    except Exception as e:
        update_fund_state(fund_code)
        log("get_fund_holding exception:", e)
        log("get_fund_holding", req_content)
        return fundHoldingList


def log(tag, msg):
    print("{}|{}\n".format(tag, msg))


# 获取基金数据
def fetch_fund_data_from_queue():
    # 当队列不为空时
    while not fund_code_queue.empty():

        # 从队列读取一个基金代码
        # 读取是阻塞操作
        fund_code = fund_code_queue.get()
        # 使用try、except来捕获异常
        # 如果不捕获异常，程序可能崩溃
        try:
            # 获取股票投资明细
            update_fund_holding(fund_code)
        except Exception as e:
            # 其他错误, 可能是获取不到详细数据
            # fund_code_queue.put(fund_code)
            log("fetch_fund_data_from_queue exception:", e)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    is_release = False
    if is_release:
        # 获取所有基金代码
        fund_code_list = load_fund_info()
        fund_len = len(fund_code_list)
        log("main", '基金总数为{}'.format(fund_len))
        # 创建一个队列
        fund_code_queue = queue.Queue(fund_len)
        # 写入基金代码数据到队列
        for i in range(fund_len):
            # fund_code_list[i]也是list类型，其中该list中的第0个元素存放基金代码
            fund_code_queue.put(fund_code_list[i][0])

        # 在一定范围内，线程数越多，速度越快
        for i in range(50):
            t = threading.Thread(target=fetch_fund_data_from_queue, name="LoopThread" + str(i))
            t.start()
    else:
        fund_code_list = load_fund_info()
        fund_len = len(fund_code_list)
        log("main", '基金总数为{}'.format(fund_len))
        for i in range(fund_len):
            update_fund_holding(fund_code_list[i][0])
