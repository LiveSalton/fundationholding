package com.salton123.app;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Process;

import com.qw.soul.permission.SoulPermission;
import com.salton123.C;
import com.salton123.app.future.FutureTaskAppDelegate;
import com.salton123.log.XLog;
import com.salton123.log.XLogConfig;
import com.salton123.model.manager.lifecycle.IActivityLifeCycle;

import androidx.annotation.NonNull;

/**
 * User: newSalton@outlook.com
 * Date: 2019/12/27 15:06
 * ModifyTime: 15:06
 * Description:
 */
public class BaseAppDelegate extends FutureTaskAppDelegate {

    public BaseAppDelegate(@NonNull Application application) {
        super(application);
    }

    @Override
    public void lowPriority() {
        super.lowPriority();
        openXLog();
        openCrashHanlder();
        openLifeCycleHandler();
        SoulPermission.init(sInstance);
    }

    public void openLifeCycleHandler() {
        IActivityLifeCycle.Factory.get().init(sInstance);
    }

    public void openCrashHanlder() {

    }

    public void openXLog() {
        boolean hasPermission = sInstance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(),
                Process.myUid()) == PackageManager.PERMISSION_GRANTED;
        XLog.config(new XLogConfig()
                .setWhetherToSaveLog(hasPermission)
                .setSavePath(C.BASE_PATH));
    }

}
