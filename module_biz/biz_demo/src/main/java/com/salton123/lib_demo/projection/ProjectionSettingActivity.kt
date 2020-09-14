package com.salton123.lib_demo.projection

import com.alibaba.android.arouter.facade.annotation.Route
import com.salton123.soulove.common.Constants
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/1 17:39
 * ModifyTime: 17:39
 * Description:
 */
@Route(path = Constants.Router.Projection.SETTING)
class ProjectionSettingActivity : BaseActivity() {
    override fun initViewAndData() {
    }

    override fun getLayout(): Int = R.layout.aty_projection_setting
}