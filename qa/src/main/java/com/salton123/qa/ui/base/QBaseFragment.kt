package com.salton123.qa.ui.base

import android.os.Bundle
import android.view.View
import com.salton123.qa.ui.dialog.CommonDialogProvider
import com.salton123.qa.ui.dialog.DialogInfo
import com.salton123.qa.ui.dialog.DialogProvider
import com.salton123.qa.ui.dialog.UniversalDialogFragment
import com.salton123.ui.base.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import com.zhenai.qa.R

abstract class QBaseFragment : BaseFragment() {

    @JvmOverloads
    fun showContent(fragmentClass: Class<out QBaseFragment>, bundle: Bundle? = null) {
        val activity = activity as QBaseActivity?
        activity?.showContent(fragmentClass, bundle)
    }

    fun finish() {
        val activity = activity as QBaseActivity?
        activity?.doBack(this)
    }

    fun showDialog(dialogInfo: DialogInfo): DialogProvider<*> {
        val provider = CommonDialogProvider(dialogInfo, dialogInfo.listener)
        showDialog(provider)
        return provider
    }

    fun showDialog(provider: DialogProvider<*>) {
        val dialog = UniversalDialogFragment()
        provider.host = dialog
        dialog.setProvider(provider)
        provider.show(childFragmentManager)
    }

    override fun initViewAndData() {
    }

    fun dismissDialog(provider: DialogProvider<*>) {
        provider.dismiss()
    }

    fun setTitleText(string: String) {
        if (enableTitleBar()) {
            (f(R.id.commonTitleBar) as CommonTitleBar).centerTextView.visibility = View.VISIBLE
            (f(R.id.commonTitleBar) as CommonTitleBar).centerTextView.text = string
        }
    }

    override fun enableTitleBar(): Boolean {
        return true
    }
}