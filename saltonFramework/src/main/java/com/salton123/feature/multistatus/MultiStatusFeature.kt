package com.salton123.feature.multistatus

import android.view.View
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.salton123.arch.view.IBaseView
import com.salton123.arch.view.IMultiStatusView
import com.salton123.feature.IFeature

/**
 * User: newSalton@outlook.com
 * Date: 2019/12/11 17:18
 * ModifyTime: 17:18
 * Description:
 */
class MultiStatusFeature(var mAttchView: IBaseView, var resId: Int) : IFeature, IMultiStatusView {

    //状态页管理
    var mBaseLoadService: LoadService<*>? = null
    override fun onBindLogic() {}
    override fun onBindUI() {
        val builder = LoadSir.Builder()
                .addCallback(mAttchView.initStatus)
                .addCallback(mAttchView.emptyStatus)
                .addCallback(mAttchView.errorStatus)
                .addCallback(mAttchView.loadingStatus)
                .setDefaultCallback(mAttchView.initStatus.javaClass)
        val attachView = mAttchView.f<View>(resId)
        mBaseLoadService = builder.build().register(attachView, mAttchView)
    }

    override fun onUnBind() {
        mBaseLoadService = null
    }

    override fun showInitLoadView() {
        if (mBaseLoadService != null) {
            mBaseLoadService!!.showCallback(mAttchView.initStatus.javaClass)
        }
    }

    override fun showNoDataView() {
        if (mBaseLoadService != null) {
            mBaseLoadService!!.showCallback(mAttchView.emptyStatus.javaClass)
        }
    }

    override fun showTransLoadingView() {
        if (mBaseLoadService != null) {
            mBaseLoadService!!.showCallback(mAttchView.loadingStatus.javaClass)
        }
    }

    override fun showNetWorkErrView() {
        if (mBaseLoadService != null) {
            mBaseLoadService!!.showCallback(mAttchView.errorStatus.javaClass)
        }
    }

    override fun showSuccessView() {
        if (mBaseLoadService != null) {
            mBaseLoadService!!.showSuccess()
        }
    }
}