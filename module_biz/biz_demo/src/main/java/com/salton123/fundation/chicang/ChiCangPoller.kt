package com.salton123.fundation.chicang

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.gson.Gson
import com.salton123.fundation.FundAppDatabase
import com.salton123.fundation.Poller
import com.salton123.log.XLog
import com.salton123.soulove.common.net.RxAdapter
import com.salton123.utils.RxUtilCompat
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Consumer
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
 * Date: 2020/9/9 15:59
 * ModifyTime: 15:59
 * Description:
 */
@SuppressLint("CheckResult")
class ChiCangPoller : Poller(1000) {
    companion object {
        val MAX_QUEUE_SIZE = 1500

        val MAX_INDEX = 999999

        //        val MAX_INDEX = 10
        val INFO_MESSAGE_WHAT = 0x101
        val TAG = "FundPoller"
        val MAX_REQUEST_SIZE = 1000
    }

    private val mQueue: LinkedBlockingQueue<String> = LinkedBlockingQueue(
            MAX_QUEUE_SIZE)
    private var mIndex: Int = 0
    private val mMainHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                INFO_MESSAGE_WHAT -> {
                    postInfo(mIndex, MAX_INDEX)
                }
            }
        }
    }

    open fun postInfo(mIndex: Int, maxIndex: Int) {
    }

    private var mQueueLatch: CountDownLatch = CountDownLatch(
            MAX_REQUEST_SIZE)
    override fun pollAction() {
        if (mQueue.size < MAX_QUEUE_SIZE - 1 && mIndex <= MAX_INDEX) { //不足且未到最大
            val step = (MAX_QUEUE_SIZE - mQueue.size) + mIndex
            for (i in mIndex until step) {
                val data = String.format("%06d", i)
                XLog.i(TAG, "code:$data")
                mQueue.add(data)
            }
            mIndex = step
        }
        if (mQueueLatch.count == 0L) {
            mQueueLatch = CountDownLatch(
                    MAX_REQUEST_SIZE)
        }
        //请求
        for (i in 0..MAX_REQUEST_SIZE) {
            val code = mQueue.poll()
            code?.let {
                sendRequest(code, mQueueLatch)
            }
        }
    }

    val okHttpClient = OkHttpClient()
    val url =
            "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNInverstPosition?product=EFund&appVersion=6.3" +
                    ".1&serverVersion=6.3.1&FCODE=009314&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me" +
                    "&version=6.3.1&userId=uid&DATE=2020-06-30&cToken=kd8kheua6cr6-jdaked8eck8cn6re8dc&MobileKey=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&appType=ttjj&OSVersion=10&plat=Android&uToken=utoken&passportid=1234567890"

    private fun sendRequest(code: String = "009314", latch: CountDownLatch) {
        val requestUrl = url.replace("009314", code)
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
                val fundResp = Gson().fromJson(data, FundResp::class.java)
                fundResp.fundData.fundStocks.forEach { it.code = code }
                Observable.create { emitter: ObservableEmitter<Long?> ->
                    try {
                        FundAppDatabase.getInstance().fundDao().insertAllFundStocks(fundResp.fundData.fundStocks)
                        emitter.onNext(0)
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                    emitter.onComplete()
                }.compose(RxAdapter.schedulersTransformer()).subscribe()
            }
        })
    }
}