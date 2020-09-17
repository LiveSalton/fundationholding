package com.salton123.fundation.bean

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/12 13:08
 * ModifyTime: 13:08
 * Description:
 */// SELECT DaiMaData.FCODE,DaiMaData.SHORTNAME,FundStock.GPJC FROM com.salton123.fundation.poller.daima.DaiMaData JOIN FundStock ON  DaiMaData.FCODE = FundStock.CODE WHERE FundStock.GPJC = "中兴通讯"
//https://www.jianshu.com/p/f4923374885b
data class CodeStocksInnerJoinInfo(
        @SerializedName("GPDM") @ColumnInfo(name = "GPDM") var gpdm: String,
        @SerializedName("GPJC") @ColumnInfo(name = "GPJC") var gpjc: String,
        @SerializedName("FCODE") @ColumnInfo(name = "FCODE") var fcode: String,
        @SerializedName("SHORTNAME") @ColumnInfo(name = "SHORTNAME") var shortname: String
)