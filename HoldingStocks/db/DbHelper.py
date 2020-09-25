# coding=utf-8
import pymysql


class DbHelper:
    """数据库保存类"""
    conn = pymysql.connect(
        "192.168.64.2",
        "root",
        "",
        "fund"
    )

    # conn = pymysql.connect(
    #     host="192.168.64.2",
    #     port=3306,
    #     user="root",
    #     password="",
    #     database="fund"
    # )
    cursor = conn.cursor()

    def __init__(self):
        # DbHelper.conn = pymysql.connect(
        #     host="localhost",
        #     user="root",
        #     password="",
        #     database="fund",
        #     charset="utf-8"
        # )
        pass

    def saveFundRank(self, data):
        print("save fund rank")

        # INSERT INTO FundRank(FCODE, SHORTNAME) VALUES("1008611", "中国移动")
        sql = 'insert into FundRank(FCODE,SHORTNAME),values()'
        DbHelper.cursor.execute(sql, [data["FCODE"], data["SHORTNAME"]])
        DbHelper.conn.commit()

    def saveFCode(self):
        print("save FCode")

    def close(self):
        DbHelper.cursor.close()
        DbHelper.conn.close()
