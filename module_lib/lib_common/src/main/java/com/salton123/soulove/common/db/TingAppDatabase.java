package com.salton123.soulove.common.db;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/7 20:37
 * ModifyTime: 20:37
 * Description:
 */

import android.content.Context;

import com.salton123.soulove.common.bean.PlayHistoryBean;
import com.salton123.soulove.common.bean.SearchHistoryBean;
import com.salton123.soulove.common.db.converter.ScheduleConverter;
import com.salton123.soulove.common.db.converter.TrackConverter;
import com.salton123.soulove.common.db.dao.PlayHistoryDao;
import com.salton123.soulove.common.db.dao.SearchHistoryDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {SearchHistoryBean.class, PlayHistoryBean.class}, version = 1)
@TypeConverters({TrackConverter.class, ScheduleConverter.class})
public abstract class TingAppDatabase extends RoomDatabase {
    public abstract SearchHistoryDao searchHistoryDao();
    public abstract PlayHistoryDao playHistoryDao();

    private static TingAppDatabase mInstance;

    public static TingAppDatabase getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("please init TingAppDatabase first,call TingAppDatabase.init(context)");
        }
        return mInstance;
    }

    public static void init(Context context) {
        mInstance = Room.databaseBuilder(context, TingAppDatabase.class, "soulove.db").build();
    }
}