package com.salton123.model.manager.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.salton123.log.XLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: newSalton@outlook.com
 * Date: 2018/3/7 17:24
 * ModifyTime: 17:24
 * Description: Activity的生命周期管理者
 */
public enum ActivityLifeCycleManager implements Application.ActivityLifecycleCallbacks, IActivityLifeCycle {
    INSTANCE;
    private static final String TAG = "ActivityLifeCycleManager";
    private static Application sApplication;
    HandlerThread mLifeCycleHandlerThread;
    private Map<String, Activity> sActivityPool = new ConcurrentHashMap<>();

    @Override
    public void init(Application sApplication) {
        if (this.sApplication == null) {
            this.sApplication = sApplication;
        }
        //注册Activity生命周期回调
        this.sApplication.registerActivityLifecycleCallbacks(this);
        mLifeCycleHandlerThread = new HandlerThread("lifeCycleHandler");
        mLifeCycleHandlerThread.start();
    }

    public void finishAty(Class<? extends Activity>... atys) {
        for (final Class<? extends Activity> atyClz : atys) {
            for (final String keyItem : sActivityPool.keySet()) {
                if (sActivityPool.get(keyItem).getClass().getName().equals(atyClz.getName())) {
                    new Handler(mLifeCycleHandlerThread.getLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            sActivityPool.get(keyItem).finish();
                            XLog.d(TAG, "[onActivityDestroyed] finishAty -> " + atyClz.getSimpleName());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void unInit() {
        mLifeCycleHandlerThread.getLooper().quit();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity != null) {
            sActivityPool.put(activity.toString(), activity);
            XLog.d(this, "[onActivityCreated] activity created -> " + activity.toString());
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        if (activity != null && sActivityPool.get(activity.toString()) == activity) {
            new Handler(mLifeCycleHandlerThread.getLooper()).post(new Runnable() {
                @Override
                public void run() {
                    sActivityPool.remove(activity.toString());
                    XLog.d(this, "[onActivityDestroyed] activity destroy -> " + activity.getClass().getName());
                }
            });
        }
    }
}
