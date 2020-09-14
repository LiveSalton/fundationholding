package com.salton123.lib_demo

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.hjq.toast.ToastUtils
import com.salton123.arch.view.TitleBarStyle
import com.salton123.lib_demo.projection.ProjectionCode
import com.salton123.lib_demo.projection.ProjectionManager
import com.salton123.soulove.common.Constants
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.aty_projection_main.*

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/1 15:51
 * ModifyTime: 15:51
 * Description:
 */
@Route(path = Constants.Router.Projection.MAIN)
class ProjectionMainAty : BaseActivity() {
    private val requestCode = 0x101

    override fun getRightStyle(): Int {
        return TitleBarStyle.ICON
    }

    override fun getRightIcon(): Int {
        return R.drawable.ic_common_sort
    }

    override fun onRightClick(v: View?) {
        var path = Constants.Router.Projection.MAIN
        ARouter.getInstance()
                .build(path)
                .navigation(activity())
    }

    override fun initViewAndData() {
        btnRequest.setOnClickListener {
            ProjectionManager.reqeustProjection(this, requestCode)
        }
        btnStop.setOnClickListener {
            ProjectionManager.toProjection(false)
        }
    }

    override fun getLayout(): Int = R.layout.aty_projection_main

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ProjectionCode.CODE_GET_PROJECTION_SUCCEED && requestCode == this.requestCode) {
            ToastUtils.show("录屏请求成功")
            ActivityUtils.startHomeActivity()
            ProjectionManager.toProjection(true)
        }
    }
}