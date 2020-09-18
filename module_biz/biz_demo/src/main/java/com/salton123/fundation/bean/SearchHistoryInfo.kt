package com.salton123.fundation.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryInfo(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "KEYWORD") var keyword: String,
        @ColumnInfo(name = "timeStamp") var index: Long = 0
) {
    override fun equals(other: Any?): Boolean {
        val okeyword = (other as SearchHistoryInfo).keyword
        return keyword == okeyword
    }
}