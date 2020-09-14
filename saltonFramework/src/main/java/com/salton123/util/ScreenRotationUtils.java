package com.salton123.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Looper;
import android.view.WindowManager;

import com.salton123.log.XLog;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/1 11:25
 * Time: 11:25
 * Description:
 */
public class ScreenRotationUtils {

    private static final String TAG = ScreenRotationUtils.class.getSimpleName();

    public  static void setScreenPortrait(Activity activity) {
        try {
            if (Looper.myLooper() != null) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } catch (Throwable throwable) {
            XLog.e(TAG, "setScreenPortrait throwable = " + throwable.getMessage()   );
        }
    }

    /**
     * 设置横屏
     */
    public static void setScreenLandscape(Activity activity) {
        try {
            if (Looper.myLooper() != null) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            }
        } catch (Throwable throwable) {
            XLog.e(TAG, "setScreenPortrait throwable = " + throwable.getMessage());
        }
    }
}
