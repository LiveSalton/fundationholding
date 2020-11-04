package com.salton123.fundation.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salton123.fundation.db.FundAppDatabase
import com.salton123.fundation.bean.CodeStocksInnerJoinInfo
import com.salton123.fundation.bean.SearchHistoryInfo
import com.salton123.fundation.poller.chicang.FundStock
import com.salton123.fundation.poller.chicang.FundStockExt
import com.salton123.log.XLog
import com.salton123.util.RxUtils
import com.salton123.utils.RxUtilCompat

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/12 12:45
 * ModifyTime: 12:45
 * Description:
 */
@SuppressLint("CheckResult")
class FundationViewModel : ViewModel() {
    companion object {
        const val TAG = "FundationViewModel"
    }

    val mCodeStacksRet: MutableLiveData<MutableList<CodeStocksInnerJoinInfo>> = MutableLiveData()
    val mFundStocksRet: MutableLiveData<MutableList<FundStock>> = MutableLiveData()
    val mSearchHistoryRet: MutableLiveData<MutableList<SearchHistoryInfo>> = MutableLiveData()
    val mFundStockExtRet: MutableLiveData<MutableList<FundStockExt>> = MutableLiveData()

    fun searchFundHoldingStocks(stocksKeyWord: String) {
        insertSearchHistory(stocksKeyWord)
        FundAppDatabase.getInstance().fundDao()
                .searchFundHoldingStocks(stocksKeyWord)
                .compose(RxUtils.schedulersTransformer())
                .subscribe({
                    mCodeStacksRet.value = it
                }, {
                    XLog.e(TAG, it.toString())
                })
    }

    fun searchFundHoldings(keyword: String) {
        FundAppDatabase.getInstance().fundDao()
                .searchFundHoldings(keyword)
                .compose(RxUtils.schedulersTransformer())
                .subscribe({
                    mFundStocksRet.value = it
                }, {
                    XLog.e(TAG, it.toString())
                })
    }

    fun searchSearchHistories(limit: Int) {
        FundAppDatabase.getInstance().fundDao()
                .searchSearchHistoryInfos(limit)
                .compose(RxUtils.schedulersTransformer())
                .subscribe({
                    mSearchHistoryRet.value = it
                }, {
                    XLog.e(TAG, it.toString())
                })
    }

    fun getPopularFund() {
        FundAppDatabase.getInstance().fundDao()
                .popularFund
                .compose(RxUtils.schedulersTransformer())
                .subscribe({
                    mFundStockExtRet.value = it
                }, {
                    XLog.e(TAG, it.toString())
                })
    }


    private fun insertSearchHistory(keyword: String) {
        FundAppDatabase.getInstance().fundDao()
                .insertSearchHistory(SearchHistoryInfo(keyword,System.currentTimeMillis()))
                .compose(RxUtilCompat.schedulersMybeIO())
                .subscribe({
                    XLog.e(TAG, "insertSearchHistory saved")
                }, {
                    XLog.e(TAG, it.toString())
                })
    }
}