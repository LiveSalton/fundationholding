package com.salton123.lib_demo.projection

import android.content.Intent
import cc.suitalk.ipcinvoker.BaseIPCService
import com.salton123.log.XLog

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/5/25 12:01
 * ModifyTime: 12:01
 * Description:
 */
class ProjectionService : BaseIPCService() {
    companion object {
        val TAG = "ProjectionService"
    }

    override fun onCreate() {
        XLog.i(TAG, "[onCreate]")
        super.onCreate()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        XLog.i(TAG, "[onStart]")
        ForegroundNotificationUtils.startForegroundNotification(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        XLog.i(TAG, "[onStartCommand]")
        ForegroundNotificationUtils.startForegroundNotification(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        XLog.i(TAG, "onUnbind")
        ForegroundNotificationUtils.deleteForegroundNotification(this)
        return super.onUnbind(intent)
    }

    override fun getProcessName(): String = ProjectionManager.PROJECTION_PROCESS_NAME

    override fun onDestroy() {
        super.onDestroy()
        XLog.i(TAG, "[onDestroy]")
        ForegroundNotificationUtils.deleteForegroundNotification(this)
    }
}