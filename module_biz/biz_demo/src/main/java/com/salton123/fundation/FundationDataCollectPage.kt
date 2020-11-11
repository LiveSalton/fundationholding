package com.salton123.fundation

import com.salton123.fundation.db.FundDBAssembleLine
import com.salton123.fundation.poller.chicang.HoldingStocksPoller
import com.salton123.fundation.poller.daima.FCodePoller
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity
import com.salton123.utils.RxCompat
import kotlinx.android.synthetic.main.page_data_collect_fundation.*

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/4 18:05
 * ModifyTime: 18:05
 * Description:
 */

class FundationDataCollectPage : BaseActivity() {
    override fun getLayout(): Int = R.layout.page_data_collect_fundation
    private var mHoldingStocksPoller: HoldingStocksPoller? = HoldingStocksPoller()
    private var mFCodePoller: FCodePoller? = FCodePoller()
    override fun initViewAndData() {

        RxCompat.runOnFlowable { FundDBAssembleLine.getInstance().fundDao().deleteAllFundStocks() }
        btnStart.setOnClickListener {
            mHoldingStocksPoller?.start()
        }
        btnStartDaima.setOnClickListener {
            mFCodePoller?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHoldingStocksPoller?.stop()
        mFCodePoller?.stop()
    }
}