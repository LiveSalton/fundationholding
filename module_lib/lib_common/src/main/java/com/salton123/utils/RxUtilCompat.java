package com.salton123.utils;

import android.annotation.SuppressLint;

import com.salton123.log.XLog;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * User: newSalton@outlook.com
 * Date: 2019/8/6 15:42
 * ModifyTime: 15:42
 * Description:
 */
public class RxUtilCompat {

    public static final String TAG = "RxUtilCompat";

    @SuppressLint("CheckResult")
    public static <T> void justToIO(T item) {
        Observable.just(item).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {

            }
        }, errorConsumer());
    }

    public static void create(final ToCallback toCallback) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                toCallback.onCall();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, errorConsumer());
    }

    public static <T> ObservableTransformer<T, T> schedulersIO() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> schedulersMybeIO() {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> schedulersMybe() {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static Consumer<? super Throwable> errorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                XLog.e(TAG, throwable.getLocalizedMessage());
            }
        };
    }
}

