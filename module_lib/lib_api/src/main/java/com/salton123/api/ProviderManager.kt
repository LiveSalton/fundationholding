package com.salton123.api

import com.alibaba.android.arouter.launcher.ARouter

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/8 16:52
 * ModifyTime: 16:52
 * Description:
 */
object ProviderManager {
    fun <T> of(service: Class<out T>): T {
        return ARouter.getInstance().navigation(service)
    }

    fun user(): IUserProvider {
        return of(IUserProvider::class.java)
    }

    fun record(): IScreenRecordProvider {
        return of(IScreenRecordProvider::class.java)
    }

    fun app(): IApplicationProvider {
        return of(IApplicationProvider::class.java)
    }
}