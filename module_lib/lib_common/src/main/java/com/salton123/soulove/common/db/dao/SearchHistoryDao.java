package com.salton123.soulove.common.db.dao;

import com.salton123.soulove.common.bean.SearchHistoryBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/20 10:49
 * ModifyTime: 10:49
 * Description:
 */
@Dao
public interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SearchHistoryBean searchHistory);

    @Query("DELETE FROM " + SearchHistoryBean.TABLE_NAME + " WHERE keyword == :keyword")
    void delete(String keyword);

    @Delete
    void delete(SearchHistoryBean entity);

    @Query("SELECT * FROM " + SearchHistoryBean.TABLE_NAME)
    Observable<List<SearchHistoryBean>> getAllRecord();

    @Query("SELECT * FROM " + SearchHistoryBean.TABLE_NAME + " ORDER BY datatime DESC ")
    Observable<List<SearchHistoryBean>> getAllRecordDesc();

    @Query("DELETE FROM " + SearchHistoryBean.TABLE_NAME)
    void deleteAll();
}
