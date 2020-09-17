package com.salton123.fundation.poller.chicang

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.salton123.fundation.db.FundAppDatabase
import com.salton123.fundation.poller.QueuePoller
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

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/11 10:07
 * ModifyTime: 10:07
 * Description:
 */
@SuppressLint("CheckResult")
class HoldingStocksPoller : QueuePoller<String>(100) {
    private var mIndex: Int = 0
    private var totalCount: Long = 0
    private val TAG = "FCodePoller"
    val okHttpClient = OkHttpClient()
    val url =
            "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNInverstPosition?product=EFund&appVersion=6.3" +
                    ".1&serverVersion=6.3.1&FCODE=009314&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me" +
                    "&version=6.3.1&userId=uid&DATE=2020-06-30&cToken=kd8kheua6cr6-jdaked8eck8cn6re8dc&MobileKey=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&appType=ttjj&OSVersion=10&plat=Android&uToken=utoken&passportid=1234567890"

    init {
        FundAppDatabase.getInstance().fundDao().count.subscribe(
                { totalCount = it }, {
            XLog.i(TAG, "error:$it")
        }, {

        })
    }

    private var sendCount = 0
    override fun sendRequest(data: String, latch: CountDownLatch) {
        sendCount++
        val requestUrl = url.replace("009314", data)
//        XLog.i(TAG, requestUrl)
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
                val respData = response.body()?.string()
                val fundResp = Gson().fromJson(respData, FundResp::class.java)
                fundResp.fundData.fundStocks.forEach { it.code = data }
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
        XLog.i(TAG, "sendCount:$sendCount,totalCount:$totalCount,mIndex:$mIndex")
    }

    override fun canRequest(): Boolean = mIndex <= totalCount + maxQueueSize + 1

    override fun requestData(size: Int): MutableList<String> {
        val data = mutableListOf<String>()
        var queryData = FundAppDatabase.getInstance().fundDao()
                .getData(mIndex, size).blockingFirst().map {
                    it.fcode
                }
        data.addAll(queryData)
        mIndex += size
        return data
    }
}