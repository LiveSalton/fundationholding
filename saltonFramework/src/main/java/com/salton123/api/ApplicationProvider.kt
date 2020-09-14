package com.salton123.api

import android.annotation.SuppressLint
import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.salton123.C

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/2 20:49
 * ModifyTime: 20:49
 * Description:
 */
@SuppressLint("CheckResult")
@Route(path = C.Provider.APP)
class ApplicationProvider : IApplicationProvider {
    private var mActions: MutableList<InitAction> = mutableListOf()
    override fun addOnInitAction(action: InitAction) {
        mActions.add(action)
    }

    override fun getOnInitActions(): MutableList<InitAction> {
        return mActions
    }

    override fun init(context: Context?) {
    }
}