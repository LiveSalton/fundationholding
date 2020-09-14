package com.salton123.qa.hook

import com.salton123.log.XLog
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/5/9 18:20
 * ModifyTime: 18:20
 * Description:监控线程创建，很好的定位到不合规的创建
 */
object ThreadHook {
    const val TAG = "ThreadHook"
    fun hook() {
        DexposedBridge.hookAllConstructors(Thread::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                super.beforeHookedMethod(param)
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                super.afterHookedMethod(param)
                (param.thisObject as Thread).stackTrace
                val thread = param.thisObject as Thread
                XLog.d(TAG, "Thread,size:${Thread.getAllStackTraces().size},name: " +
                        thread.name + " " + "class:" + thread.javaClass + " is created.")
            }
        })
        DexposedBridge.findAndHookMethod(Thread::class.java, "run", ThreadMethodHook())
    }

    internal class ThreadMethodHook : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            super.beforeHookedMethod(param)
            val t = param.thisObject as Thread
            XLog.d(TAG, "thread:$t, started..")
        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)
            val t = param.thisObject as Thread
            XLog.d(TAG, "thread:$t, exit..")
        }
    }
}