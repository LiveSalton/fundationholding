package com.salton123.fundation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.salton123.fundation.upgrade.FundInfoUpgradeViewModel
import com.salton123.log.XLog
import com.salton123.soulove.common.Constants
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.page_fund_info_upgrade.*

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/11/11 10:43
 * ModifyTime: 10:43
 * Description:
 */
@Route(path = Constants.Router.Fundation.DATA_COLLECT)
class FundInfoUpgradePage : BaseActivity() {
    lateinit var mViewModel: FundInfoUpgradeViewModel

    override fun getLayout(): Int = R.layout.page_fund_info_upgrade

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(FundInfoUpgradeViewModel::class.java)
        mViewModel.apply {
            mFCodeRet.observe(this@FundInfoUpgradePage, Observer {
                if (it.first <= it.second && it.third) {
                    val msg = "已经更新完毕，总共有${it.second}只基金"
                    XLog.i(TAG, msg)
                    tvStart.text = msg
                } else {
                    tvStart.text = "已经更新${it.second}只基金"
                }
            })
            mHoldingStocksRet.observe(this@FundInfoUpgradePage, Observer {
                if (it.first <= it.second) {
                    val msg = "已经更新完毕，总共更新${it.second}只基金持仓信息"
                    XLog.i(TAG, msg)
                    tvUpdateHoldings.text = msg
                } else {
                    tvUpdateHoldings.text = "已经更新${it.second}只基金持仓信息"
                }
            })
        }
    }

    override fun initViewAndData() {
    }

    override fun initListener() {
        super.initListener()
        setListener(tvStart,tvUpdateHoldings)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            tvStart -> {
                mViewModel.updateFundCode()
            }
            tvUpdateHoldings -> {
                mViewModel.updateHoldingStocks()
            }
        }
    }

    companion object {
        val TAG = "FundInfoUpgradePage"
    }
}