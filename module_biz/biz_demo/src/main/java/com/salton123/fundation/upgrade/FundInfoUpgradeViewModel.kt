package com.salton123.fundation.upgrade

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.salton123.fundation.db.FundDBAssembleLine
import com.salton123.fundation.poller.chicang.FundResp
import com.salton123.fundation.poller.chicang.FundStock
import com.salton123.fundation.poller.chicang.HoldingStocksPoller
import com.salton123.fundation.poller.daima.DaiMaResp
import com.salton123.log.XLog
import com.salton123.soulove.common.net.RxAdapter
import com.salton123.utils.RxCompat
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.CountDownLatch

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/11/11 11:36
 * ModifyTime: 11:36
 * Description:
 */
@SuppressLint("CheckResult")
class FundInfoUpgradeViewModel : ViewModel(), IUpgradeDelegate {
    val okHttpClient = OkHttpClient()
    val fundCodeUrl =
            "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNRank?appType=ttjj&Sort=desc&product=EFund&gToken" +
                    "=ceaf-d3be9c7b62c0bd53aa18ca2d23fed99c&version=6.3" +
                    ".1&DataConstraintType=0&onFundCache=3&ESTABDATE=&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C" +
                    "%7Ciemi_tluafed_me&FundType=0&BUY=true&pageIndex=10086&pageSize=30&SortColumn=SYL_Y&MobileKey" +
                    "=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&plat=Android&ISABNORMAL=true"

    val holdingStockUrl =
            "https://fundmobapi.eastmoney.com/FundMNewApi/FundMNInverstPosition?product=EFund&appVersion=6.3" +
                    ".1&serverVersion=6.3.1&FCODE=009314&deviceid=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me" +
                    "&version=6.3.1&userId=uid&DATE=2020-06-30&cToken=kd8kheua6cr6-jdaked8eck8cn6re8dc&MobileKey=6110b75b31a95a4f4b568396d6c9e85d%7C%7Ciemi_tluafed_me&appType=ttjj&OSVersion=10&plat=Android&uToken=utoken&passportid=1234567890"


    val TAG = "FundInfoUpgradeViewModel"
    private var fCodeCount = 0
    private var fTotalCount = 1000
    override fun requestFundCode(index: Int, latch: CountDownLatch) {
        val requestUrl = fundCodeUrl.replace("10086", index.toString())
        XLog.i(TAG, "$index,$requestUrl")
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
                val data = response.body()?.string()
                val resp = Gson().fromJson(data, DaiMaResp::class.java)
                //save data
                fTotalCount = resp.totalCount
                RxCompat.runOnFlowable {
                    FundDBAssembleLine.getInstance().fundDao().insert(resp.fundData)
                }
                if (resp.fundData.isNotEmpty()) {
                    fCodeCount += resp.fundData.size
                    mFCodeRet.postValue(Triple(fTotalCount, fCodeCount, false))
                } else {
                    mFCodeRet.postValue(Triple(fTotalCount, fCodeCount, true))
                }
                latch.countDown()
            }
        })
    }

    val mFCodeRet: MutableLiveData<Triple<Int, Int, Boolean>> = MutableLiveData()
    private val mHandler = UpgradeHanlder(this)
    fun updateFundCode() {
        fCodeCount = 0
        for (i in 0..1000) {
            //耗时操作完成，发送消息给UI线程
            val msg: Message = Message.obtain()
            msg.what = UpgradeHanlder.MSG_WHAT_REQUEST_FUND_CODE
            msg.obj = i
            mHandler.sendMessage(msg)
            XLog.i(TAG, "send message:$i")
        }
    }

    fun updateHoldingStocks() {
//        FundDBAssembleLine.getInstance().fundDao().allData.subscribe { data ->
//            mUpdateHoldingStocksCount = 0
//            mTotalHoldingStocksCount = data.size
//            data.forEach {
//                val msg: Message = Message.obtain()
//                msg.what = UpgradeHanlder.MSG_WHAT_REQUEST_HOLDING_STOCK
//                msg.obj = it.fcode
//                mHandler.sendMessage(msg)
//            }
//        }
        HoldingStocksPoller().start()
    }

    val mHoldingStocksRet: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    @Volatile
    var mUpdateHoldingStocksCount = 0
    @Volatile
    var mTotalHoldingStocksCount = 0

    var allFundStocks: MutableList<FundStock> = mutableListOf()
    override fun reqHoldingStocks(fCode: String, latch: CountDownLatch) {
        val requestUrl = holdingStockUrl.replace("009314", fCode)
        val request: Request = Request.Builder()
                .url(requestUrl)
                .get() //默认就是GET请求，可以不写
                .build()

        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                latch.countDown()
                mHoldingStocksRet.postValue(Pair(mTotalHoldingStocksCount, ++mUpdateHoldingStocksCount))
            }

            override fun onResponse(call: Call, response: Response) {
                latch.countDown()
                ++mUpdateHoldingStocksCount
                val respData = response.body()?.string()
                val fundResp = Gson().fromJson(respData, FundResp::class.java)
                fundResp.fundData.fundStocks.forEach { it.code = fCode }
                allFundStocks.addAll(fundResp.fundData.fundStocks)
                XLog.i(TAG, "$fCode,$mUpdateHoldingStocksCount,$requestUrl")
                if (mTotalHoldingStocksCount == mUpdateHoldingStocksCount) {
                    Observable.create { emitter: ObservableEmitter<Long?> ->
                        try {
//                            var tFundStocks: MutableList<FundStock> = mutableListOf()
//                            tFundStocks.addAll(allFundStocks)
                            FundDBAssembleLine.getInstance().fundDao().insertAllFundStocks(allFundStocks.filterNotNull())
                            emitter.onNext(0)
                        } catch (e: Exception) {
                            emitter.onError(e)
                        }
                        XLog.i(TAG, "save data to db")
                        emitter.onComplete()
                    }.compose(RxAdapter.schedulersTransformer()).subscribe {
                        mHoldingStocksRet.postValue(Pair(mTotalHoldingStocksCount, mUpdateHoldingStocksCount))
                    }
                } else {
                    mHoldingStocksRet.postValue(Pair(mTotalHoldingStocksCount, mUpdateHoldingStocksCount))
                }
            }
        })
    }

    internal class UpgradeHanlder(fundPage: IUpgradeDelegate) : Handler() {
        private var mPageRef = WeakReference<IUpgradeDelegate>(fundPage)

        companion object {
            val MSG_WHAT_REQUEST_FUND_CODE = 0x100
            val MSG_WHAT_REQUEST_HOLDING_STOCK = 0x101
        }

        private var mQueueLatch: CountDownLatch? = null
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_WHAT_REQUEST_FUND_CODE -> {
                    mQueueLatch = CountDownLatch(1)
                    mPageRef.get()?.requestFundCode(msg.obj as Int, mQueueLatch!!)
//                    mQueueLatch?.await()
                }
                MSG_WHAT_REQUEST_HOLDING_STOCK -> {
                    mQueueLatch = CountDownLatch(1)
                    mPageRef.get()?.reqHoldingStocks(msg.obj as String, mQueueLatch!!)
//                    mQueueLatch?.await()
                }
            }
        }
    }
}