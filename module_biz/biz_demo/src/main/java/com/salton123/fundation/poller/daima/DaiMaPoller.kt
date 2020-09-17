package com.salton123.fundation.poller.daima

import com.google.gson.Gson
import com.salton123.fundation.db.FundAppDatabase
import com.salton123.fundation.poller.Poller
import com.salton123.log.XLog
import com.salton123.soulove.common.net.RxAdapter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/10 10:38
 * ModifyTime: 10:38
 * Description:
 */
class DaiMaPoller : Poller(100) {
    companion object {
        val TAG = "DaiMaPoller"
        val MAX_QUEUE_SIZE = 1000
        val INFO_MESSAGE_WHAT = 0x101
        val MAX_REQUEST_SIZE = MAX_QUEUE_SIZE / 10
    }

    private val mQueue: LinkedBlockingQueue<String> = LinkedBlockingQueue(MAX_QUEUE_SIZE)
    private var mQueueLatch: CountDownLatch = CountDownLatch(MAX_REQUEST_SIZE)

    init {
        for (i in 0 until MAX_QUEUE_SIZE - 1) {
            mQueue.add("$i")
        }
    }

    override fun pollAction() {
        if (mQueueLatch.count == 0L) {
            mQueueLatch = CountDownLatch(MAX_REQUEST_SIZE)
        }
        //请求
        for (i in 0..MAX_REQUEST_SIZE) {
            val index = mQueue.poll()
            index?.let {
                request(index, mQueueLatch)
            }
        }
        mQueueLatch.await()
    }

    val okHttpClient = OkHttpClient()
    val url = "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNRank?appType=ttjj&Sort=desc&product=EFund&gToken" +
            "=ceaf-d3be9c7b62c0bd53aa18ca2d23fed99c&version=6.3" +
            ".1&DataConstraintType=0&onFundCache=3&ESTABDATE=&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C" +
            "%7Ciemi_tluafed_me&FundType=0&BUY=true&pageIndex=10086&pageSize=100&SortColumn=SYL_Y&MobileKey" +
            "=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&plat=Android&ISABNORMAL=true"

    fun request(pageIndex: String, latch: CountDownLatch) {
        val requestUrl = url.replace("10086", pageIndex)
        XLog.i(TAG, requestUrl)
        val request: Request = Request.Builder()
                .url(requestUrl)
                .get() //默认就是GET请求，可以不写
                .build()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                latch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                latch.countDown()
                val data = response.body()?.string()
                val resp = Gson().fromJson(data, DaiMaResp::class.java)
                //save data
                if (resp.fundData.isNotEmpty()) {
                    Observable.create { emitter: ObservableEmitter<Long?> ->
                        try {
                            FundAppDatabase.getInstance().fundDao().insert(resp.fundData)
                            emitter.onNext(0)
                        } catch (e: Exception) {
                            emitter.onError(e)
                        }
                        emitter.onComplete()
                    }.compose(RxAdapter.schedulersTransformer()).subscribe()
                }
            }
        })
    }
}