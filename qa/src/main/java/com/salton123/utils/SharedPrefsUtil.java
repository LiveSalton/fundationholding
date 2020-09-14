package com.salton123.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

/**
 * Created by wanglikun on 2018/9/14.
 */

public class SharedPrefsUtil {
    private static final String SHARED_PREFS_DORAEMON = "shared_prefs_doraemon";

    private static SharedPreferences getSharedPrefs(Context context) {
        return getSharedPrefs(context, SHARED_PREFS_DORAEMON);
    }

    @Nullable
    public static SharedPreferences getSharedPrefs(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key, String defVal) {
        return getSharedPrefs(context).getString(key, defVal);
    }

    public static void putString(Context context, String key, String value) {
        putString(context, SHARED_PREFS_DORAEMON, key, value);
    }

    public static void putString(Context context, String table, String key, String value) {
        getSharedPrefs(context, table).edit().putString(key, value).apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null) {
            return;
        }
        putBoolean(context, SHARED_PREFS_DORAEMON, key, value);
    }

    public static void putBoolean(Context context, String table, String key, boolean value) {
        getSharedPrefs(context, table).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defVal) {
        return context != null && getSharedPrefs(context).getBoolean(key, defVal);
    }

    public static void putInt(Context context, String key, int value) {
        putInt(context, SHARED_PREFS_DORAEMON, key, value);
    }

    public static void putInt(Context context, String table, String key, Integer value) {
        getSharedPrefs(context, table).edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defVal) {
        return getSharedPrefs(context).getInt(key, defVal);
    }

    public static void putFloat(Context context, String table, String key, Float value) {
        getSharedPrefs(context, table).edit().putFloat(key, value).apply();
    }

    public static void putFloat(Context context, String key, Float value) {
        getSharedPrefs(context, SHARED_PREFS_DORAEMON).edit().putFloat(key, value).apply();
    }

    public static void putLong(Context context, String table, String key, Long value) {
        getSharedPrefs(context, table).edit().putLong(key, value).apply();
    }
}
