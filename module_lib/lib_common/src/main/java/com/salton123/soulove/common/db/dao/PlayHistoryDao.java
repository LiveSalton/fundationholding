package com.salton123.soulove.common.db.dao;

import com.salton123.soulove.common.bean.PlayHistoryBean;
import com.salton123.soulove.common.bean.SearchHistoryBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/24 12:27
 * ModifyTime: 12:27
 * Description:
 */
@Dao
public interface PlayHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insert(PlayHistoryBean bean);

    @Query("SELECT * FROM  " + PlayHistoryBean.TABLE_NAME + " WHERE soundId == :dataId AND kind == :kind ORDER BY datatime DESC")
    Observable<List<PlayHistoryBean>> listPlayHistory(long dataId, String kind);

    @Query("SELECT * FROM  " + PlayHistoryBean.TABLE_NAME + " WHERE groupId == :groupId AND kind == :kind ORDER BY datatime DESC")
    Observable<List<PlayHistoryBean>> listPlayHistoryByGroupId(String groupId, String kind);

    @Query("SELECT * FROM " + PlayHistoryBean.TABLE_NAME + " ORDER BY datatime DESC ")
    Observable<List<PlayHistoryBean>> listDesc();

    @Query("DELETE FROM " + PlayHistoryBean.TABLE_NAME)
    void deleteAll();

    // @Query("SELECT * FROM " + PlayHistoryBean.TABLE_NAME + " ORDER BY datatime DESC LIMIT :pagesize OFFSET :page")
    // Observable<List<PlayHistoryBean>> getPlayHistory(int page, int pagesize);

    @Query("SELECT a.* FROM PlayHistoryBean a WHERE 1>(SELECT COUNT(*) FROM PlayHistoryBean WHERE groupId = a.groupId AND datatime > a.datatime) ORDER BY a.DATATIME DESC LIMIT :pagesize OFFSET :page")
    Observable<List<PlayHistoryBean>> getPlayHistory(int page, int pagesize);
    // SELECT a.* FROM PLAY_HISTORY_BEAN a WHERE 1>( SELECT COUNT(*) FROM PLAY_HISTORY_BEAN WHERE GROUP_ID = a.GROUP_ID AND DATATIME > a.DATATIME)  ORDER BY a.DATATIME DESC LIMIT 20 OFFSET 0
}
