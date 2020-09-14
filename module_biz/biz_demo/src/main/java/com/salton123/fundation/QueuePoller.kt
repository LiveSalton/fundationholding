package com.salton123.fundation

import com.salton123.log.XLog
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/10 17:47
 * ModifyTime: 17:47
 * Description:
 */
abstract class QueuePoller<T>(interval: Long) : Poller(interval) {
    var maxQueueSize = 500
    var maxRequestSize = 100

    companion object {
        const val TAG = "FCodePoller"
    }

    private val mQueue: LinkedBlockingQueue<T> = LinkedBlockingQueue(maxQueueSize)
    private var mQueueLatch: CountDownLatch = CountDownLatch(maxRequestSize)

    override fun pollAction() {
        if (canRequest()) {
            //数据入列
            if (mQueue.size < maxQueueSize) { //队列未满
                val size = maxQueueSize - mQueue.size - 1
                val reqData = requestData(size)
                if (reqData.size < maxQueueSize) {
                    mQueue.addAll(reqData)
                } else {
                    XLog.i(TAG, "size:$size")
                }
                XLog.i(TAG, "size:$size,reqData.size:${reqData.size}")
            }

            for (i in 0..mQueue.size) {
                //发起请求
                mQueue.poll()?.let {
                    if (canRequest()) {
                        sendRequest(it, mQueueLatch)
                    }
                }
            }
            mQueueLatch.await()
        } else {
            XLog.i(TAG, "stopRequest")
            stop()
        }
    }

    //请求数据
    abstract fun requestData(size: Int): MutableList<T>

    abstract fun sendRequest(data: T, latch: CountDownLatch)

    abstract fun canRequest(): Boolean
}