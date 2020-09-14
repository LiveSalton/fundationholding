package com.salton123.lib_demo.projection.task

import cc.suitalk.ipcinvoker.IPCAsyncInvokeTask
import cc.suitalk.ipcinvoker.IPCInvokeCallback
import cc.suitalk.ipcinvoker.IPCServiceManager
import cc.suitalk.ipcinvoker.type.IPCBoolean
import com.salton123.lib_demo.projection.ForegroundNotificationUtils
import com.salton123.lib_demo.projection.ProjectionManager

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/4 14:45
 * ModifyTime: 14:45
 * Description:
 */
class ForegroundNotificationTask : IPCAsyncInvokeTask<IPCBoolean, IPCBoolean> {
    override fun invoke(data: IPCBoolean?, callback: IPCInvokeCallback<IPCBoolean>?) {
        if (data?.value!!) {
            ForegroundNotificationUtils.startForegroundNotification(
                    IPCServiceManager.getImpl()
                            .get(ProjectionManager.PROJECTION_PROCESS_NAME))
        } else {
            ForegroundNotificationUtils.deleteForegroundNotification(
                    IPCServiceManager.getImpl()
                            .get(ProjectionManager.PROJECTION_PROCESS_NAME))
        }
        callback?.onCallback(data)
    }
}