package com.salton123.fundation.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salton123.fundation.FundAppDatabase
import com.salton123.fundation.bean.CodeStocksInnerJoinInfo
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

    fun searchFundHoldingStocks(stocksKeyWord: String) {
        FundAppDatabase.getInstance().fundDao()
                .searchFundHoldingStocks(stocksKeyWord)
                .compose(RxUtils.schedulersTransformer())
                .subscribe({
                    mCodeStacksRet.value = it
                }, {
                    XLog.e(TAG, it.toString())
                })
    }
}