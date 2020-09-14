package com.salton123.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;

import com.salton123.app.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * User: newSalton@outlook.com
 * Date: 2019-05-09 00:08
 * ModifyTime: 00:08
 * Description:
 */
public class CommonUtils {
    /**
     * Return the name of current process.
     * <p>It's faster than ActivityManager.</p>
     *
     * @return the name of current process
     */
    public static String getCurrentProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * Return whether app running in the main process.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMainProcess() {
        return BaseApplication.getInstance().getPackageName().equals(getCurrentProcessName(BaseApplication.getInstance()));
    }

    public static boolean isPermissionGrant(Context context, String[] permissions) {
        for (String item : permissions) {
            boolean isGranted = context.checkPermission(item, Process.myPid(),
                    Process.myUid()) == PackageManager.PERMISSION_GRANTED;
            if (!isGranted) {
                return false;
            }
        }
        return true;
    }

}
