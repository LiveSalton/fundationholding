package com.salton123.fundation.poller.daima

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/10 12:44
 * ModifyTime: 12:44
 * Description:
 */
@Entity
data class DaiMaData(
        @PrimaryKey @SerializedName("FCODE") @ColumnInfo(name = "FCODE") var fcode: String,
        @SerializedName("SHORTNAME") @ColumnInfo(name = "SHORTNAME") var shortname: String,
        @SerializedName("FUNDTYPE") @ColumnInfo(name = "FUNDTYPE") var fundtype: String
)

data class DaiMaResp(
        @SerializedName("ErrCode") var errorCode: Int,
        @SerializedName("TotalCount") var totalCount: Int,
        @SerializedName("Expansion") var expansion: String,
        @SerializedName("Datas") var fundData: MutableList<DaiMaData>
)