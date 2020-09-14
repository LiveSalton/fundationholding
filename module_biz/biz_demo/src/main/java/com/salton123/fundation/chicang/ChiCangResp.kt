package com.salton123.fundation.chicang

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/9 17:23
 * ModifyTime: 17:23
 * Description:
 */

@Entity
data class FundStock(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "index") var index: Int,
        @ColumnInfo(name = "CODE") var code: String,
        @SerializedName("GPDM") @ColumnInfo(name = "GPDM") var gpdm: String,
        @SerializedName("GPJC") @ColumnInfo(name = "GPJC") var gpjc: String,
        @SerializedName("JZBL") @ColumnInfo(name = "JZBL") var jzbl: String,
        @SerializedName("TEXCH") @ColumnInfo(name = "TEXCH") var texch: String,
        @SerializedName("ISINVISBL") @ColumnInfo(name = "ISINVISBL") var isinvisbl: String,
        @SerializedName("PCTNVCHGTYPE") @ColumnInfo(name = "PCTNVCHGTYPE") var pctnvchgtype: String,
        @SerializedName("PCTNVCHG") @ColumnInfo(name = "PCTNVCHG") var pctnvchg: String,
        @SerializedName("NEWTEXCH") @ColumnInfo(name = "NEWTEXCH") var newtexch: String
)

data class FundBood(
        @SerializedName("ZQDM") var zqdm: String,
        @SerializedName("ZQMC") var zqmc: String,
        @SerializedName("ZJZBL") var zjzbl: String,
        @SerializedName("ISBROKEN") var isbroken: String
)

data class FundData(
        @SerializedName("fundboods") var fundboods: MutableList<FundBood>,
        @SerializedName("fundStocks") var fundStocks: MutableList<FundStock>
)

data class FundResp(
        @SerializedName("ErrCode") var errorCode: Int,
        @SerializedName("TotalCount") var totalCount: Int,
        @SerializedName("Expansion") var expansion: String,
        @SerializedName("Datas") var fundData: FundData
)

