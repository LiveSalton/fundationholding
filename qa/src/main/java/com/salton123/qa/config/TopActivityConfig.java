package com.salton123.qa.config;

import android.content.Context;

import com.salton123.qa.constant.SharedPrefsKey;
import com.salton123.utils.SharedPrefsUtil;

/**
 * 项目名:    Android
 * 包名       com.salton123.qa.config
 * 文件名:    TopActivityConfig
 * 创建时间:  2019-04-29 on 12:27
 *
 * @author 阿钟
 */

public class TopActivityConfig {
    public static boolean isTopActivityOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.TOP_ACTIVITY_OPEN, false);
    }

    public static void setTopActivityOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.TOP_ACTIVITY_OPEN, open);
    }
}
