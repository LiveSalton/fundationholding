package com.salton123.fundation.db;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/7 20:37
 * ModifyTime: 20:37
 * Description:
 */

import android.content.Context;

import com.salton123.app.BaseApplication;
import com.salton123.fundation.bean.SearchHistoryInfo;
import com.salton123.fundation.poller.chicang.FundStock;
import com.salton123.fundation.poller.daima.DaiMaData;
import com.salton123.fundation.db.dao.FundDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FundStock.class, DaiMaData.class, SearchHistoryInfo.class}, version = 2)
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
        mInstance = Room.databaseBuilder(context, FundAppDatabase.class, DB_PATH)
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `SearchHistoryInfo` (`KEYWORD` TEXT NOT NULL, `timeStamp` INTEGER NOT NULL, PRIMARY KEY(`KEYWORD`))");
        }
    };
}