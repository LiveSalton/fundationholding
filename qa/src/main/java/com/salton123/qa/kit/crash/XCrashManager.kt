package com.salton123.qa.kit.crash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.salton123.log.XLog
import com.salton123.qa.QualityAssistant
import com.salton123.qa.QualityContext
import com.salton123.qa.constant.BundleKey
import com.salton123.qa.kit.fileexplorer.TextViewerActivity
import xcrash.XCrash
import java.io.File

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/28 14:40
 * ModifyTime: 14:40
 * Description:
 */
object XCrashManager {
    val TAG = "XCrashManager"
    fun init(qualityContext: QualityContext) {
        val params = XCrash.InitParameters()
        params.enableAnrCrashHandler()
        params.enableJavaCrashHandler()
        params.enableNativeCrashHandler()
        params.setAppVersion(qualityContext.getAppVersion())
        params.setLogDir(getCrashPath())
        params.setAnrCallback { logPath, emergency ->
            Toast.makeText(qualityContext.app, "检测到Anr:$logPath", Toast.LENGTH_LONG).show()
            XLog.e(TAG, "检测到Anr:$logPath\n $emergency")
            openActivity(qualityContext.app, logPath)
        }
        params.setJavaCallback { logPath, emergency ->
            Toast.makeText(qualityContext.app, "检测到Crash:$logPath", Toast.LENGTH_LONG).show()
            XLog.e(TAG, "检测到Crash:$logPath\n $emergency")
            openActivity(qualityContext.app, logPath)
        }
        params.setNativeCallback { logPath, emergency ->
            Toast.makeText(qualityContext.app, "检测到Native Crash:$logPath", Toast.LENGTH_LONG).show()
            XLog.e(TAG, "检测到Native Crash:$logPath\n $emergency")
            openActivity(qualityContext.app, logPath)
        }
        XCrash.init(qualityContext.app, params)
    }

    fun openActivity(context: Context, logPath: String) {
        val bundle = Bundle()
        bundle.putSerializable(BundleKey.FILE_KEY, File(logPath))
        val crashIntent = Intent(context, TextViewerActivity::class.java)
        crashIntent.putExtras(bundle)
        crashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(crashIntent)
    }

    fun getCrashPath(): String {
        return QualityAssistant.application.getExternalFilesDir("crash")?.absolutePath ?: ""
    }
}