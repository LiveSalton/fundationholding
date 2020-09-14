package com.salton123.qa

import android.app.Application
import com.salton123.qa.kit.IKit

/**
 * User: newSalton@outlook.com
 * Date: 2018/10/22 2:49 PM
 * ModifyTime: 2:49 PM
 * Description:
 */
open class QualityContext(var app: Application) {

    init {
        if (app == null) {
            throw RuntimeException("please install Application")
        }
    }

    open fun isShowCrashPanel(): Boolean {
        return true
    }

    open fun isLeakCanaryEnable(): Boolean {
        return true
    }

    open fun isBlockCanaryEnable(): Boolean {
        return false
    }

    open fun isShowInfoPanel(): Boolean {
        return true
    }

    open fun addSelfKits(): List<IKit> {
        return ArrayList()
    }
}