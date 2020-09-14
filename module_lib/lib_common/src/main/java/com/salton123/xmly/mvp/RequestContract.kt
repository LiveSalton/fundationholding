package com.salton123.xmly.mvp

import com.salton123.arch.view.IBaseView

/**
 * User: newSalton@outlook.com
 * Date: 2018/5/23 下午6:28
 * ModifyTime: 下午6:28
 * Description:
 */
interface RequestContract {
    interface IRequestView : IBaseView {
        fun onRequestError(code: Int, msg: String?)
        fun <T> onRequestSucceed(data: T?)
    }
}
