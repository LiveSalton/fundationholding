package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

/**
 * @author wanglikun
 */
public class AlignRulerConfig {
    public static boolean isAlignRulerOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.ALIGN_RULER_OPEN, false);
    }

    public static void setAlignRulerOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.ALIGN_RULER_OPEN, open);
    }
}