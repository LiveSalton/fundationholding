package com.salton123.arch.view

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.Callback.OnReloadListener
import com.salton123.feature.multistatus.EmptyStatus
import com.salton123.feature.multistatus.ErrorStatus
import com.salton123.feature.multistatus.InitStatus
import com.salton123.feature.multistatus.LoadingStatus
import com.salton123.ui.base.IComponentLife

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 11:06
 * Time: 11:06
 * Description:
 */
@SuppressLint("ObsoleteSdkInt")

interface IBaseView : IImmersionView, IMultiStatusView, IComponentLife, ITitleBar, OnReloadListener, LifecycleOwner {
    val initStatus: Callback
        get() = InitStatus()

    val loadingStatus: Callback
        get() = LoadingStatus()

    val errorStatus: Callback
        get() = ErrorStatus()

    val emptyStatus: Callback
        get() = EmptyStatus()
}
