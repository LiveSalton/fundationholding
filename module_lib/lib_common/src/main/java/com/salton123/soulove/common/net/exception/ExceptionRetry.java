package com.salton123.soulove.common.net.exception;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Thomas.
 * <br/>Date: 2019/8/1 8:01
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:所有异常都会经过此处,可拦截需要重试的内部异常,如Token超时等
 */
public class ExceptionRetry implements Function<Observable<Throwable>, Observable<?>> {

    @Override
    public Observable<?> apply(Observable<Throwable> observable) throws Exception {
        return observable.compose(upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()))
                .flatMap((Function<Throwable, Observable<?>>) throwable -> {
                    Log.d("ExceptionRetry", throwable.getLocalizedMessage());
                    return Observable.error(throwable);
                });
    }

}
