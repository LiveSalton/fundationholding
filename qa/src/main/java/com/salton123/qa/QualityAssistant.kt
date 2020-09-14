package com.salton123.qa

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Process
import android.util.Log
import com.salton123.infopanel.InfoDispatcher
import com.salton123.qa.kit.KitManager
import com.salton123.qa.kit.blockmonitor.core.BlockMonitorManager
import com.salton123.qa.kit.crash.XCrashManager
import com.salton123.qa.kit.detech.BigImageDetechService
import com.salton123.qa.kit.gpsmock.ServiceHookManager
import com.salton123.qa.kit.timecounter.instrumentation.HandlerHooker
import com.salton123.qa.ui.base.FloatPageManager
import com.salton123.utils.ActivityUtils

/**
 * User: newSalton@outlook.com
 * Date: 2018/10/12 5:02 PM
 * ModifyTime: 5:02 PM
 * Description:
 */
object QualityAssistant {
    lateinit var application: Application

    @JvmStatic
    fun install(qualityContext: QualityContext) {
        Log.d("QualityAssistant", "debug version")
        application = qualityContext.app
        if (qualityContext.isBlockCanaryEnable()) {
            BlockMonitorManager.getInstance().start(qualityContext.app)
        }
        if (qualityContext.isShowInfoPanel()) {
            InfoDispatcher.INSTANCE.install()
            ActivityUtils.install(application)
            HandlerHooker.doHook(application)      //hook系统ActivityThread，统计Aty切换耗时
            ServiceHookManager.getInstance().install()
            KitManager.getInstance().install(application, qualityContext.addSelfKits())
            FloatPageManager.getInstance().init(application)
        }
        if (qualityContext.isBigImageDetech()) {
            BigImageDetechService.hookSetImageBitmap()
        }
//        ClassLoaderHook.hook()
//        ThreadHook.hook()
        XCrashManager.init(qualityContext)
    }

    fun checkStoragePermission(context: Context): Boolean {
        return context.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(),
                Process.myUid()) == PERMISSION_GRANTED
    }

    fun getDefaultPath(): String {
        return application.getExternalFilesDir("crash")?.absolutePath ?: ""
    }
}