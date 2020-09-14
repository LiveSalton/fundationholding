package com.salton123.api

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/2 20:41
 * ModifyTime: 20:41
 * Description:
 */
interface IApplicationProvider : IProvider {
    fun addOnInitAction(action: InitAction)
    fun getOnInitActions(): MutableList<InitAction>
}