package com.salton123.ui.base

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.salton123.arch.view.IBaseView
import com.salton123.arch.view.TitleBarStyle
import com.salton123.feature.IdleHandlerFeature
import com.salton123.feature.ImmersionFeature
import com.salton123.feature.multistatus.InitStatus
import com.salton123.feature.multistatus.MultiStatusFeature
import com.salton123.saltonframework.R
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * User: newSalton@outlook.com
 * Date: 2019/12/12 15:59
 * ModifyTime: 15:59
 * Description:
 */
abstract class BaseDialogFragment : LifeDelegateDialogFragment(), IBaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (isOpenImmersionBar()) {
            dardFont()
        }
        super.onCreate(savedInstanceState)
        if (isOpenMultiStatus()) {
            mMultiStatusFeature = fetchMultiStatusFeature()
            mMultiStatusFeature?.apply {
                addFeature(this)
            }
        }
        addFeature(object : IdleHandlerFeature() {
            override fun doOnQueueIdle(): Boolean {
                if (mMultiStatusFeature?.mBaseLoadService?.currentCallback == InitStatus::class.java) {
                    showSuccessView()
                }
                return false
            }
        })
        applyDefaultTitleBar()
    }

    //<editor-fold desc="多状态特性">
    var mMultiStatusFeature: MultiStatusFeature? = null

    open fun isOpenMultiStatus(): Boolean {
        return false
    }

    open fun fetchMultiStatusFeature(): MultiStatusFeature? {
        if (mMultiStatusFeature == null) {
            mMultiStatusFeature = MultiStatusFeature(this, R.id.salton_id_content_layout)
        }
        return mMultiStatusFeature
    }

    override fun showInitLoadView() {
        mMultiStatusFeature?.showInitLoadView()
    }

    override fun showNoDataView() {
        mMultiStatusFeature?.showNoDataView()
    }

    override fun showTransLoadingView() {
        mMultiStatusFeature?.showTransLoadingView()
    }

    override fun showNetWorkErrView() {
        mMultiStatusFeature?.showNetWorkErrView()
    }

    override fun showSuccessView() {
        mMultiStatusFeature?.showSuccessView()
    }

    override fun onReload(v: View?) {
    }
    //</editor-fold>

    //<editor-fold desc="沉浸式特性">
    private var mImmersionFeature: ImmersionFeature? = null

    open fun fetchImmersionFeature(): ImmersionFeature? {
        if (mImmersionFeature == null) {
            mImmersionFeature = ImmersionFeature(self())
        }
        return mImmersionFeature
    }

    override fun getImmersionBar(): ImmersionBar? {
        return fetchImmersionFeature()?.getImmersionBar()
    }

    open fun isOpenImmersionBar(): Boolean {
        if (self() is Activity) {
            return true
        } else if (self() is Fragment) {
            return false
        }
        return false
    }

    override fun dardFont() {
        fetchImmersionFeature()?.dardFont()
    }

    override fun lightFont() {
        fetchImmersionFeature()?.lightFont()
    }
    //</editor-fold>

    //<editor-fold desc="标题特性">
    open fun applyDefaultTitleBar() {
        if (enableTitleBar()) {
            syncTitleBar(LayoutInflater.from(activity()).inflate(R.layout.salton_common_title_bar, null, false))
            initTitleBar((f(R.id.commonTitleBar) as CommonTitleBar))
        }
    }

    override fun enableTitleBar(): Boolean {
        return self() is BaseActivity
    }

    open fun initTitleBar(titleBar: CommonTitleBar) {
        if (TextUtils.isEmpty(titleText)) {
            titleBar.centerTextView?.visibility = View.GONE
        } else {
            titleBar.centerTextView?.visibility = View.VISIBLE
            titleBar.centerTextView?.text = titleText
        }
        when (leftStyle) {
            TitleBarStyle.ICON_TEXT -> {   //icon & text
                titleBar.leftTextView?.text = leftText
                titleBar.leftTextView?.visibility = View.VISIBLE
                leftIcon?.let {
                    titleBar.leftTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(it, 0, 0, 0)
                }
            }
            TitleBarStyle.ICON -> {    //icon
                titleBar.leftTextView?.text = ""
                titleBar.leftTextView?.visibility = View.VISIBLE
                leftIcon?.let {
                    titleBar.leftTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(it, 0, 0, 0)
                }
            }
            else -> {    //hidden
                titleBar.leftTextView?.visibility = View.GONE
            }
        }
        when (rightStyle) {
            TitleBarStyle.TEXT -> {   //icon & text
                titleBar.rightTextView?.text = rightText
                titleBar.rightTextView?.visibility = View.VISIBLE
                rightIcon?.let {
                    titleBar.rightTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(it, 0, 0, 0)
                }
            }
            TitleBarStyle.ICON -> {    //icon
                titleBar.rightTextView?.text = ""
                titleBar.rightTextView?.visibility = View.VISIBLE
                rightIcon?.let {
                    titleBar.rightTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(it, 0, 0, 0)
                }
            }
            else -> {    //hidden
                titleBar.rightTextView?.visibility = View.GONE
            }
        }
        titleBar.setListener { v, action, _ ->
            when (action) {
                CommonTitleBar.ACTION_LEFT_TEXT -> {
                    onLeftClick(v)
                }
                CommonTitleBar.ACTION_RIGHT_TEXT -> {
                    onRightClick(v)
                }
            }
        }
    }
    //</editor-fold>
}
