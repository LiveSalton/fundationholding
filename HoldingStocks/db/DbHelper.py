import pymysql


class DbHelper:
    """数据库保存类"""

    def __init__(self):
        pass

    def connect(self):
        conn = pymysql.connect(
            host="",
            user="",
            password="",
            database="",
            charset="utf-8"
        )

    def saveFCode(self):
        print("save FCode")
