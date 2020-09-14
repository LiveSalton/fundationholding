package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

/**
 * Created by wanglikun on 2018/12/14.
 */

public class LogInfoFloatLayoutConfig {

    public static int getLastPosX(Context context) {
        return SharedPrefsUtil.getInt(context, SharedPrefsKey.LOG_INFO_FLOAT_LAYOUT_POS_X, 0);
    }

    public static int getLastPosY(Context context) {
        return SharedPrefsUtil.getInt(context, SharedPrefsKey.LOG_INFO_FLOAT_LAYOUT_POS_Y, 0);
    }

    public static void saveLastPosY(Context context, int val) {
        SharedPrefsUtil.putInt(context, SharedPrefsKey.LOG_INFO_FLOAT_LAYOUT_POS_Y, val);
    }

    public static void saveLastPosX(Context context, int val) {
        SharedPrefsUtil.putInt(context, SharedPrefsKey.LOG_INFO_FLOAT_LAYOUT_POS_X, val);
    }
}
