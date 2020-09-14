package com.salton123.feature

import android.os.Looper

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/10 10:04
 * ModifyTime: 10:04
 * Description:
 */
abstract class IdleHandlerFeature : IFeature {

    override fun onBindLogic() {
    }

    override fun onBindUI() {
        Looper.myQueue().addIdleHandler { doOnQueueIdle() }
    }

    override fun onUnBind() {
    }

    abstract fun doOnQueueIdle(): Boolean
}
