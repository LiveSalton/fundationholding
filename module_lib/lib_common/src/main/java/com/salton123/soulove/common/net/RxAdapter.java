package com.salton123.soulove.common.net;

import com.hjq.toast.ToastUtils;
import com.salton123.soulove.common.net.dto.ResponseDTO;
import com.salton123.soulove.common.net.exception.CustException;
import com.salton123.soulove.common.net.exception.ExceptionConverter;
import com.salton123.soulove.common.net.exception.ExceptionRetry;
import com.salton123.soulove.common.net.exception.InterceptableException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Thomas.
 * <br/>Date: 2019/9/10 8:23
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:Rx适配器
 */
public class RxAdapter {

    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 异常处理方式
     * <br/>StreamHandler(将内部异常选择性抛出,可设置需要重试的异常)->
     * <br/>ExceptionRetry(所有异常都会经过此处,可拦截需要重试的内部异常,如Token超时等)->
     * <br/>ExceptionHandler(统一处理未被拦截内部异常和所有外部异常)
     */
    public static <T> ObservableTransformer<T, T> exceptionTransformer() {

        return observable -> observable
                .flatMap(new StreamHandler<>())
                .retryWhen(new ExceptionRetry())//拦截需要处理的异常
                .onErrorResumeNext(new ExceptionHandler<>());
    }

    /**
     * 将内部异常选择性抛出,可设置需要重试的异常
     */
    private static class StreamHandler<T> implements Function<T, ObservableSource<T>> {

        @Override
        public Observable<T> apply(T o) throws Exception {
            return handle(o);
        }

        private Observable<T> handle(T o) {
            if (o instanceof ResponseDTO) {
                ResponseDTO respDTO = (ResponseDTO) o;
                //选择性抛出内部异常
                if (!respDTO.code.equals(ExceptionConverter.APP_ERROR.SUCCESS)) {
                    Exception throwable = new CustException(respDTO.code, respDTO.msg);
                    //如果是token超时,则尝试重试
                    if (respDTO.code.equals(ExceptionConverter.APP_ERROR.TOKEN_OUTTIME)) {
                        throwable = new InterceptableException(InterceptableException.TOKEN_OUTTIME,
                                respDTO.msg);
                    }
                    return Observable.error(throwable);
                }
            }
            return Observable.just(o);
        }
    }

    /**
     * 统一处理未被拦截内部异常和所有外部异常
     */
    private static class ExceptionHandler<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return handle(t);
        }

        private Observable<T> handle(Throwable t) {
            //转换外部异常
            if (!(t instanceof CustException)) {
                t = ExceptionConverter.convert(t);
            }
            ToastUtils.show(t.getMessage());
            return Observable.error(t);
        }
    }
}