package com.salton123.fundation.poller.daima

import com.google.gson.Gson
import com.salton123.fundation.db.FundDBAssembleLine
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
 * Date: 2020/9/11 9:14
 * ModifyTime: 9:14
 * Description:
 */
class FCodePoller : QueuePoller<String>(50) {
    private var mIndex: Int = 0
    private val TAG = "FCodePoller"
    val okHttpClient = OkHttpClient()
    val url = "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNRank?appType=ttjj&Sort=desc&product=EFund&gToken" +
            "=ceaf-d3be9c7b62c0bd53aa18ca2d23fed99c&version=6.3" +
            ".1&DataConstraintType=0&onFundCache=3&ESTABDATE=&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C" +
            "%7Ciemi_tluafed_me&FundType=0&BUY=true&pageIndex=10086&pageSize=30&SortColumn=SYL_Y&MobileKey" +
            "=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&plat=Android&ISABNORMAL=true"

    override fun requestData(size: Int): MutableList<String> {
        val data = mutableListOf<String>()
        for (i in 0 until size) {
            data.add("${mIndex + i}")
        }
        mIndex += size
        return data
    }

    private var fCodeCount = 0
    override fun sendRequest(data: String, latch: CountDownLatch) {
        val requestUrl = url.replace("10086", data)
        XLog.i(TAG, "pageIndex:$data")
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
                            FundDBAssembleLine.getInstance().fundDao().insert(resp.fundData)
                            fCodeCount += resp.fundData.size
                            XLog.i(TAG, "fCodeCount:$fCodeCount")
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

    override fun canRequest(): Boolean = mIndex <= 1000
}