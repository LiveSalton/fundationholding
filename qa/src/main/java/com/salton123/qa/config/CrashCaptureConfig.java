package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

public class CrashCaptureConfig {
    public static boolean isCrashCaptureOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, false);
    }

    public static void setCrashCaptureOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, open);
    }
}
