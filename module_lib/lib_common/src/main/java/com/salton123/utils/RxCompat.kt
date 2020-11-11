package com.salton123.utils

import com.salton123.log.XLog
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/11/8 14:02
 * ModifyTime: 14:02
 * Description:
 */
object RxCompat {
    private const val TAG = "RxUtils"
    fun <T> runOnFlowable(callback: () -> T) {
        Flowable.create<T>({
            it.onNext(callback.invoke())
        }, BackpressureStrategy.MISSING)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun <T> runOnMainFlowable(callback: () -> T) {
        Flowable.create<T>({
            it.onNext(callback.invoke())
        }, BackpressureStrategy.MISSING)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> schedulersTransformerForFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun errorConsumer(): Consumer<in Throwable> {
        return Consumer { throwable -> XLog.e(TAG, throwable.toString()) }
    }

    private fun printStackTrace(errorReason: Array<StackTraceElement>): String {
        val stringBuilder = StringBuilder()
        for (item in errorReason) {
            stringBuilder.append(item.toString() + "\n")
        }
        return stringBuilder.toString()
    }

    fun dispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun <T> schedulersIO(): ObservableTransformer<T, T>? {
        return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
    }

    fun <T> schedulersMybeIO(): MaybeTransformer<T, T>? {
        return MaybeTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
    }

    fun <T> schedulersMybe(): MaybeTransformer<T, T>? {
        return MaybeTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}