package com.salton123.arch.view

import com.gyf.immersionbar.ImmersionBar

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/27 17:09
 * ModifyTime: 17:09
 * Description:
 */
interface IImmersionView {
    fun getImmersionBar(): ImmersionBar?

    fun dardFont()

    fun lightFont()
}