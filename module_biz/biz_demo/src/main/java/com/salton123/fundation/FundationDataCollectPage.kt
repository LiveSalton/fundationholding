package com.salton123.fundation

import com.alibaba.android.arouter.facade.annotation.Route
import com.salton123.fundation.db.FundAppDatabase
import com.salton123.fundation.poller.chicang.HoldingStocksPoller
import com.salton123.fundation.poller.daima.FCodePoller
import com.salton123.log.XLog
import com.salton123.soulove.common.Constants
import com.salton123.soulove.common.net.RxAdapter
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.android.synthetic.main.page_data_collect_fundation.*

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/4 18:05
 * ModifyTime: 18:05
 * Description:
 */
@Route(path = Constants.Router.Fundation.DATA_COLLECT)
class FundationDataCollectPage : BaseActivity() {
    override fun getLayout(): Int = R.layout.page_data_collect_fundation
    private var mChiCangPoller: HoldingStocksPoller? = HoldingStocksPoller()
    private var mDaiMaPoller: FCodePoller? = FCodePoller()
    override fun initViewAndData() {
        Observable.create { emitter: ObservableEmitter<Long?> ->
            try {
                FundAppDatabase.getInstance().fundDao().deleteAllFundStocks()
                XLog.i("FundationPage", "drop FundStock")
                emitter.onNext(0)
            } catch (e: Exception) {
                emitter.onError(e)
            }
            emitter.onComplete()
        }.compose(RxAdapter.schedulersTransformer()).subscribe(
                {}, {}, {}
        )
        btnStart.setOnClickListener {
            mChiCangPoller?.start()
        }
        btnStartDaima.setOnClickListener {
            mDaiMaPoller?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mChiCangPoller?.stop()
        mDaiMaPoller?.stop()
    }
}