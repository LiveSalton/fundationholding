package com.salton123.soulove.common.db.converter;

import com.google.gson.Gson;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;

import androidx.room.TypeConverter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/24 12:22
 * ModifyTime: 12:22
 * Description:
 */
public class ScheduleConverter {
    @TypeConverter
    public static Schedule convertToEntityProperty(String value) {
        return new Gson().fromJson(value, Schedule.class);
    }

    @TypeConverter
    public static String convertToDatabaseValue(Schedule schedule) {
        return new Gson().toJson(schedule);
    }
}
