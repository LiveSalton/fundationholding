package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

/**
 * Created by wanglikun on 2018/9/20.
 */

public class GpsMockConfig {
    public static boolean isGPSMockOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.GPS_MOCK_OPEN, false);
    }

    public static void setGPSMockOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.GPS_MOCK_OPEN, open);
    }
}