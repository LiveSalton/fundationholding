package com.salton123.soulove.common;

import android.app.Application;

import com.salton123.xmly.TingAppHelper;


/**
 * Author: Thomas.
 * <br/>Date: 2019/9/18 8:36
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:App初始化帮助类
 */
public class AppHelper {

    private static final String TAG = "AppHelper";

    private static Application mApplication;
    private static volatile AppHelper instance;


    private AppHelper() {
    }

    public static AppHelper getInstance(Application application) {
        if (instance == null) {
            synchronized (AppHelper.class) {
                if (instance == null) {
                    mApplication = application;
                    instance = new AppHelper();
                }
            }
        }
        return instance;
    }

    public AppHelper initXmly() {
        TingAppHelper.INSTANCE.init(mApplication);
        return this;
    }
}
