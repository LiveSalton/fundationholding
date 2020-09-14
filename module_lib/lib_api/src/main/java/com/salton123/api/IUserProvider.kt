package com.salton123.api

import com.alibaba.android.arouter.facade.template.IProvider
import com.salton123.bmob.bean.User

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/8 18:13
 * ModifyTime: 18:13
 * Description:
 */
interface IUserProvider : IProvider {
    fun login(account: String, password: String, callback: (ret: Boolean, err: Throwable?) -> Unit)
    fun register(account: String, password: String, callback: (ret: Boolean, err: Throwable?) -> Unit)
    fun getLoginUser(): User?
    fun isLogin(): Boolean
    fun autoLogin()
    fun getLoginUserId(): String
    fun logout()
    fun active()
}