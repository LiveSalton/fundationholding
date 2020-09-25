# coding=utf-8
import urllib2
import json

from db.DbHelper import DbHelper

fCodeUrl = "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNRank?appType=ttjj&Sort=desc&product=EFund&gToken=ceaf-d3be9c7b62c0bd53aa18ca2d23fed99c&version=6.3.1&DataConstraintType=0&onFundCache=3&ESTABDATE=&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&FundType=0&BUY=true&pageIndex=10086&pageSize=30&SortColumn=SYL_Y&MobileKey=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&plat=Android&ISABNORMAL=true"


class FundRankTask:
    """查询基金持仓任务"""

    def __init__(self):
        pass

    def start(self):
        print("start holding stocks task")

    mDbHelper = DbHelper()

    # 查询基金排名
    def queryFundRank(self, index):
        print("query one fund")
        reqUrl = fCodeUrl.replace("10086", index)
        print(reqUrl)
        req = urllib2.urlopen(reqUrl)
        resp = json.loads(req.read().decode("utf-8"))
        print(resp)
        # jsonData = json.loads(resp)
        # print(type(resp["Datas"]))
        data = resp["Datas"]
        if len(data):
            for item in data:
                # FCODE = data["FCODE"]
                # SHORTNAME = data["SHORTNAME"]
                # FUNDTYPE = data["FUNDTYPE"]
                # BFUNDTYPE = data["BFUNDTYPE"]
                # FEATURE = data["FEATURE"]
                # FSRQ = data["FSRQ"]
                # RZDF = data["RZDF"]
                # DWJZ = data["DWJZ"]
                # HLDWJZ = data["HLDWJZ"]
                # LJJZ = data["LJJZ"]
                # FTYI = data["FTYI"]
                # TEYI = data["TEYI"]
                # TFYI = data["TFYI"]
                # SYL_Z = data["SYL_Z"]
                # SYL_Y = data["SYL_Y"]
                # SYL_3Y = data["SYL_3Y"]
                # SYL_6Y = data["SYL_6Y"]
                # SYL_1N = data["SYL_1N"]
                # SYL_2N = data["SYL_2N"]
                # SYL_3N = data["SYL_3N"]
                # SYL_5N = data["SYL_5N"]
                # SYL_JN = data["SYL_JN"]
                # SYL_LN = data["SYL_LN"]
                # ZJL = data["ZJL"]
                # TARGETYIELD = data["TARGETYIELD"]
                # CYCLE = data["CYCLE"]
                # KFR = data["KFR"]
                # LEVERAGE = data["LEVERAGE"]
                # BAGTYPE = data["BAGTYPE"]
                # BUY = data["BUY"]
                # LISTTEXCH = data["LISTTEXCH"]
                # NEWTEXCH = data["NEWTEXCH"]
                # ISLISTTRADE = data["ISLISTTRADE"]
                # PTDT_Y = data["PTDT_Y"]
                # PTDT_TWY = data["PTDT_TWY"]
                # PTDT_TRY = data["PTDT_TRY"]
                # PTDT_FY = data["PTDT_FY"]
                # MBDT_Y = data["MBDT_Y"]
                # MBDT_TWY = data["MBDT_TWY"]
                # MBDT_TRY = data["MBDT_TRY"]
                # MBDT_FY = data["MBDT_FY"]
                # YDDT_Y = data["YDDT_Y"]
                # YDDT_TWY = data["YDDT_TWY"]
                # YDDT_TRY = data["YDDT_TRY"]
                # YDDT_FY = data["YDDT_FY"]
                # DWDT_Y = data["DWDT_Y"]
                # DWDT_TWY = data["DWDT_TWY"]
                # DWDT_TRY = data["DWDT_TRY"]
                # DWDT_FY = data["DWDT_FY"]
                # ENDNAV = data["ENDNAV"]
                # SALEVOLUME = data["SALEVOLUME"]
                # PV_Y = data["PV_Y"]
                # DTCOUNT_Y = data["DTCOUNT_Y"]
                # ORGSALESRANK = data["ORGSALESRANK"]
                # ISABNORMAL = data["ISABNORMAL"]
                # self.saveToDb(item)
                print(item)
                # FundRankTask.mDbHelper.saveFundRank(data)
                return True
        else:
            FundRankTask.mDbHelper.close()
            print("data error")
            return False

    def queryAllFunRank(self):
        print("query all fund`s rank")
        hasNext = True
        index = 0
        while hasNext:
            print("get next data")
            hasNext = self.queryFundRank(str(index))
            index = index + 1

    def saveToDb(self, data):
        print("save to db")
