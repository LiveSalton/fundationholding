package com.salton123.soulove.common.db.converter;

import com.google.gson.Gson;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import androidx.room.TypeConverter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/28 18:11
 * ModifyTime: 18:11
 * Description:
 */
public class TrackConverter {
    @TypeConverter
    public static Track convertToEntityProperty(String value) {
        return new Gson().fromJson(value, Track.class);
    }

    @TypeConverter
    public static String convertToDatabaseValue(Track track) {
        return new Gson().toJson(track);
    }
}
