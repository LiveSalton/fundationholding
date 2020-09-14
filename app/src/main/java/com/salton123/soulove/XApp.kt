package com.salton123.soulove

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.toast.ToastUtils
import com.salton123.api.IApplicationProvider
import com.salton123.app.BaseApplication
import com.salton123.fundation.FundAppDatabase
import com.salton123.qa.QualityAssistant.install
import com.salton123.qa.QualityContext
import com.salton123.soulove.common.AppHelper
import com.salton123.soulove.common.BuildConfig
import com.salton123.soulove.common.widget.TRefreshHeader
import com.salton123.soulove.sdk.ThirdHelper
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.ximalaya.ting.android.opensdk.util.BaseUtil

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/23 15:40
 * ModifyTime: 15:40
 * Description:
 */
class XApp : BaseApplication() {
    companion object {
        init {
            //设置全局默认配置（优先级最低，会被其他设置覆盖）
            SmartRefreshLayout.setDefaultRefreshInitializer { _: Context?, layout: RefreshLayout? ->
                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
                layout?.setHeaderMaxDragRate(1.5f)
            }
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, _: RefreshLayout? ->
                TRefreshHeader(context)
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, _: RefreshLayout? ->
                val classicsFooter = ClassicsFooter(context)
                classicsFooter.setTextSizeTitle(12f)
                classicsFooter.setDrawableSize(16f)
                classicsFooter.setFinishDuration(0)
                classicsFooter
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        if (BaseUtil.isMainProcess(this)) {
            ThirdHelper.getInstance(this)
                    .initQualityAssistant()
                    .initFragmentation(BuildConfig.APP_DEVELOP)
                    .initRouter()
                    .initUtils()
                    .initBugly()
                    .initCrashView()
            AppHelper.getInstance(this)
                    .initXmly()
            initQualityAssistant()
            ARouter.getInstance().navigation(IApplicationProvider::class.java)
                    .getOnInitActions().forEach {
                        it.doAction()
                    }
        }
    }

    fun initQualityAssistant() {
        install(QualityContext(this))
    }
}