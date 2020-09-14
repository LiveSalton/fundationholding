package com.salton123.soulove.common.mvvm.model;

import android.app.Application;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Author: Thomas.
 * <br/>Date: 2019/9/10 8:23
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:Model基类
 */
public abstract class BaseModel {
    protected Application mApplication;
    private CompositeDisposable mCompositeDisposable;

    public BaseModel(Application application) {
        mApplication = application;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onCleared() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
