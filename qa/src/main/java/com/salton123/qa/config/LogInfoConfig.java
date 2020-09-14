package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

/**
 * @author wanglikun
 */
public class LogInfoConfig {
    public static boolean isLogInfoOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.LOG_INFO_OPEN, false);
    }

    public static void setLogInfoOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.LOG_INFO_OPEN, open);
    }
}