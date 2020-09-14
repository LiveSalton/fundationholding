package com.salton123.fundation.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/10 12:52
 * ModifyTime: 12:52
 * Description:
 */

//@Entity
//data class FundInfo(
//        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "index") var index: Int,
//        @ColumnInfo(name = "CODE") var code: String,
//        @SerializedName("GPDM") @ColumnInfo(name = "GPDM") var gpdm: String,
//        @SerializedName("GPJC") @ColumnInfo(name = "GPJC") var gpjc: String,
//        @SerializedName("JZBL") @ColumnInfo(name = "JZBL") var jzbl: String,
//        @SerializedName("TEXCH") @ColumnInfo(name = "TEXCH") var texch: String,
//        @SerializedName("ISINVISBL") @ColumnInfo(name = "ISINVISBL") var isinvisbl: String,
//        @SerializedName("PCTNVCHGTYPE") @ColumnInfo(name = "PCTNVCHGTYPE") var pctnvchgtype: String,
//        @SerializedName("PCTNVCHG") @ColumnInfo(name = "PCTNVCHG") var pctnvchg: String,
//        @SerializedName("NEWTEXCH") @ColumnInfo(name = "NEWTEXCH") var newtexch: String,
//        @SerializedName("FCODE") @ColumnInfo(name = "FCODE") var fcode: String,
//        @SerializedName("SHORTNAME") @ColumnInfo(name = "SHORTNAME") var shortname: String,
//        @SerializedName("FUNDTYPE") @ColumnInfo(name = "FUNDTYPE") var fundtype: String
//) {
//    companion object {
//        const val TABLE_NAME: String = "FundInfo"
//    }
//}