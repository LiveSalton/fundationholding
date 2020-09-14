package com.salton123.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.salton123.qa.ui.base.FloatPageManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {

    private static List<ActivityLifecycleListener> sListeners = new ArrayList<>();

    private static WeakReference<Activity> sCurrentResumedActivity;

    public static void install(final Application app) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            int startedActivityCounts;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyForeground();
                }
                startedActivityCounts++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityResumed(activity);
                }
                sCurrentResumedActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityPaused(activity);
                }
                sCurrentResumedActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                startedActivityCounts--;
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyBackground();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public interface ActivityLifecycleListener {
        void onActivityResumed(Activity activity);

        void onActivityPaused(Activity activity);
    }

    public static void registerListener(ActivityLifecycleListener listener) {
        sListeners.add(listener);
    }

    public static void unRegisterListener(ActivityLifecycleListener listener) {
        sListeners.remove(listener);
    }

    public static Activity getCurrentResumedActivity() {
        if (sCurrentResumedActivity != null && sCurrentResumedActivity.get() != null) {
            return sCurrentResumedActivity.get();
        }
        return null;
    }
}
