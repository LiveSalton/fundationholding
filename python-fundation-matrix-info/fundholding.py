# -*- coding:utf-8 -*-
from datetime import datetime
from requests import exceptions
import requests
import random
import re
import queue
import threading
import json
# 导入数据库相关:
from sqlalchemy import Column, String, create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.sql.sqltypes import DateTime, Float
from contextlib import contextmanager
from bs4 import BeautifulSoup

# 创建对象的基类:
Base = declarative_base()


# 定义User对象:
class Fund(Base):
    # 表的名字:
    __tablename__ = 'fund'

    # 表的结构:
    fundcode = Column(String(6), primary_key=True)
    name = Column(String(20))
    dwjz = Column(Float()) # 单位净值
    jzrq = Column(DateTime()) # 单位净值日期
    gsz = Column(Float()) # 净值估算
    gszzl = Column(Float()) # 净值估算涨跌
    gztime = Column(DateTime()) # 净值估算日期
    # 持仓数据
    poscode = Column(String(6))  # 持仓数最多的股票代码
    posname = Column(String(20))  # 持仓数最多的股票名
    poscost = Column(Float())  # 持仓数额(万元)


# 初始化数据库连接:
engine = create_engine(
    'postgresql+psycopg2://username:xaECbyg6ygFpzE@192.168.1.152:5433/db00f172a094d14d0db9c20b9d32174632qq')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)

# 创建表结构
Base.metadata.create_all(engine)


@contextmanager
def get_session():
    s = DBSession()
    try:
        yield s
        s.commit()
    except Exception as e:
        s.rollback()
        raise e
    finally:
        s.close()


# user_agent列表
user_agent_list = [
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0",
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.3.4000 Chrome/30.0.1599.101 Safari/537.36",
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36",
]

# referer列表
referer_list = [
    "http://fund.eastmoney.com/110022.html",
    "http://fund.eastmoney.com/110023.html",
    "http://fund.eastmoney.com/110024.html",
    "http://fund.eastmoney.com/110025.html",
]


# 获取代理
def get_proxy():
    return requests.get("http://127.0.0.1:5010/get/").json()


# 获取所有基金代码
def get_fund_code():
    # 获取一个随机user_agent和Referer
    header = {
        "User-Agent": random.choice(user_agent_list),
        "Referer": random.choice(referer_list),
    }

    # 访问网页接口
    req = requests.get("http://fund.eastmoney.com/js/fundcode_search.js",
                       timeout=5,
                       headers=header)

    # 获取所有基金代码
    fund_code = req.content.decode()
    fund_code = fund_code.replace("var r = [", "").replace("];", "")

    # 正则批量提取
    fund_code = re.findall(r"[\[](.*?)[\]]", fund_code)

    # 对每行数据进行处理，并存储到fund_code_list列表中
    fund_code_list = []
    for sub_data in fund_code:
        data = sub_data.replace('"', "").replace("'", "")
        data_list = data.split(",")
        fund_code_list.append(data_list)

    return fund_code_list


# 获取基金数据
def get_fund_data():

    # 当队列不为空时
    while not fund_code_queue.empty():

        # 从队列读取一个基金代码
        # 读取是阻塞操作
        fund_code = fund_code_queue.get()

        # 获取一个代理，格式为ip:端口
        proxy = get_proxy().get("proxy")

        # 获取一个随机user_agent和Referer
        header = {
            "User-Agent": random.choice(user_agent_list),
            "Referer": random.choice(referer_list),
        }

        # 使用try、except来捕获异常
        # 如果不捕获异常，程序可能崩溃
        try:
            # 使用代理访问
            req = requests.get(
                "http://fundgz.1234567.com.cn/js/" + str(fund_code) + ".js",
                proxies={"http": "http://{}".format(proxy)},
                timeout=3,
                headers=header,
            )

            # 没有报异常，说明访问成功
            # 获得返回数据
            data = ((req.content.decode()).replace("jsonpgz(", "").replace(
                ");", "").replace("'", '"'))
            data_dict = json.loads(data)

            # 如果获取数据错误，则继续，并将code再放回队列
            if data_dict == None or data_dict['fundcode'] == None:
                fund_code_queue.put(fund_code)
                continue

            # 获取股票投资明细
            req = requests.get(
                "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jjcc&code="
                + str(fund_code) + "&topline=10&year=2021&month=3",
                proxies={"http": "http://{}".format(proxy)},
                timeout=3,
                headers=header,
            )

            text = req.text

            text = text.replace('var apidata={ content:"', '')

            pattern = re.compile(r'",arryear:.*')

            html = re.sub(pattern, '', text)

            soup = BeautifulSoup(html, 'lxml')

            trs = soup.table.tbody.find_all("tr")[0:1]

            tds = trs[0].find_all("td")

            with get_session() as s:
                fundcode = data_dict['fundcode']
                fund = s.query(Fund).filter(Fund.fundcode == fundcode).first()
                create = False
                if (fund == None):
                    fund = Fund()
                    create = True

                fund.fundcode = fundcode
                fund.name = data_dict['name']
                fund.dwjz = data_dict['dwjz']
                fund.gsz = data_dict['gsz']
                fund.gszzl = data_dict['gszzl']
                fund.gztime = datetime.strptime(data_dict['gztime'],
                                                '%Y-%m-%d %H:%M')
                fund.jzrq = datetime.strptime(data_dict['jzrq'], '%Y-%m-%d')
                fund.poscode = tds[1].string
                fund.posname = tds[2].string
                fund.poscost = float(tds[8].string.replace(',', ''))

                if (create):
                    s.add(fund)

                s.commit()

                # 写入数据成功，打印日志
                print(data_dict)

        except (exceptions.ReadTimeout, exceptions.ConnectTimeout,
                exceptions.ConnectionError):
            # 访问失败了，所以要把我们刚才取出的数据再放回去队列中
            fund_code_queue.put(fund_code)
            print("访问失败，尝试使用其他代理访问")

        except Exception as e:
            # 其他错误, 可能是获取不到详细数据
            # fund_code_queue.put(fund_code)
            print(e)


if __name__ == "__main__":

    # 获取所有基金代码
    fund_code_list = get_fund_code()

    fund_len = len(fund_code_list)
    print('基金总数为{}'.format(fund_len))

    # 创建一个队列
    fund_code_queue = queue.Queue(fund_len)
    # 写入基金代码数据到队列
    for i in range(fund_len):
        # fund_code_list[i]也是list类型，其中该list中的第0个元素存放基金代码
        fund_code_queue.put(fund_code_list[i][0])

    # 在一定范围内，线程数越多，速度越快
    for i in range(50):
        t = threading.Thread(target=get_fund_data, name="LoopThread" + str(i))
        t.start()