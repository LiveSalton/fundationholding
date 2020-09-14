package com.salton123.app;

import android.app.Application;
import android.content.Context;

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/9 17:50
 * ModifyTime: 17:50
 * Description:
 */
public class BaseApplication extends Application {
    private BaseAppDelegate mBaseAppDelegate;
    public static Application sInstance;

    public static <T extends Application> T getInstance() {
        return (T) sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        mBaseAppDelegate = getAppDelegate();
    }

    public BaseAppDelegate getAppDelegate() {
        if (mBaseAppDelegate == null) {
            return new BaseAppDelegate(this);
        } else {
            return mBaseAppDelegate;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBaseAppDelegate != null) {
            mBaseAppDelegate.onCreate();
        }
    }
}
