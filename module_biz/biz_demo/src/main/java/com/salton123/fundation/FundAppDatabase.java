package com.salton123.fundation;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/7 20:37
 * ModifyTime: 20:37
 * Description:
 */

import android.content.Context;

import com.salton123.app.BaseApplication;
import com.salton123.fundation.chicang.FundStock;
import com.salton123.fundation.daima.DaiMaData;
import com.salton123.fundation.dao.FundDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FundStock.class, DaiMaData.class}, version = 1)
public abstract class FundAppDatabase extends RoomDatabase {
    public static final String DB_PATH = BaseApplication.sInstance.getDatabasePath("fund.db").getAbsolutePath();

    public abstract FundDao fundDao();

    // public abstract DaiMaDao daimaDao();

    private static FundAppDatabase mInstance;

    public static FundAppDatabase getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("please init TingAppDatabase first,call TingAppDatabase.init(context)");
        }
        return mInstance;
    }

    public static void init(Context context) {
        mInstance = Room.databaseBuilder(context, FundAppDatabase.class, DB_PATH).build();
    }
}