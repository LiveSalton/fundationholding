package com.salton123.qa.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.salton123.infopanel.IInterceptView
import com.salton123.log.XLog
import com.salton123.qa.ui.dialog.DialogProvider
import com.salton123.qa.ui.dialog.UniversalDialogFragment
import com.salton123.ui.base.BaseActivity
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import com.zhenai.qa.R
import java.util.ArrayDeque

abstract class QBaseActivity : BaseActivity(), IInterceptView {

    private val mFragments = ArrayDeque<Fragment>()

    fun showContent(target: Class<out Fragment>, bundle: Bundle?) {
        try {
            val fragment = target.newInstance()
            bundle?.let { fragment.arguments = bundle }
            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.add(android.R.id.content, fragment)
            mFragments.push(fragment)
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        } catch (e: Exception) {
            XLog.e(TAG, e.toString())
        }
    }

    override fun isOpenMultiStatus(): Boolean {
        return false
    }

    override fun onBackPressedSupport() {
        if (!mFragments.isEmpty()) {
            mFragments.removeFirst()
            super.onBackPressed()
            if (mFragments.isEmpty()) {
                finish()
            }
        } else {
            super.onBackPressedSupport()
        }
    }

    fun doBack(fragment: QBaseFragment) {
        if (mFragments.contains(fragment)) {
            mFragments.remove(fragment)
            val fm = supportFragmentManager
            fm.popBackStack()
            if (mFragments.isEmpty()) {
                finish()
            }
        }
    }

    override fun initViewAndData() {

    }

    fun setTitleText(titleText: String?) {
        (f(R.id.commonTitleBar) as CommonTitleBar).centerTextView.visibility = View.VISIBLE
        (f(R.id.commonTitleBar) as CommonTitleBar).centerTextView.text = titleText
    }

    fun showDialog(provider: DialogProvider<*>) {
        val dialog = UniversalDialogFragment()
        provider.host = dialog
        dialog.setProvider(provider)
        provider.show(supportFragmentManager)
    }

    companion object {
        private val TAG = "QBaseActivity"
    }
}
