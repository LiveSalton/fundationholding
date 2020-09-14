package com.salton123.soulove.common.bean;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/20 10:46
 * ModifyTime: 10:46
 * Description:
 */
@Entity
public class SearchHistoryBean {
    public static final String TABLE_NAME = "SearchHistoryBean";

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "keyword")
    public String keyword;

    @ColumnInfo(name = "datatime")
    private long datatime;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getDatatime() {
        return datatime;
    }

    public void setDatatime(long datatime) {
        this.datatime = datatime;
    }
}
