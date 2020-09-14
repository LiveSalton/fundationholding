package com.salton123.app.future;

import android.app.Application;
import android.util.Log;

import com.salton123.util.CommonUtils;
import com.salton123.log.XLog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * User: newSalton@outlook.com
 * Date: 2019-05-08 23:58
 * ModifyTime: 23:58
 * Description:每个进程都会执行Application的初始化操作，对此我们需要在主进程区别初始化
 */
public class FutureTaskApplication extends Application implements IFutureTaskPriority {
    //只有高优先级的任务需要和主线程保持同步，其他线程的任务异步处理，加快初始化过程。
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if (CommonUtils.isMainProcess()) {  //主进程
                runOnMainProcessMainThread();
                FutureTaskLoader.INSTANCE.init(this, mCountDownLatch);
                mCountDownLatch.await(1500, TimeUnit.MILLISECONDS);
            } else {
                runOnAllProcessMainThread();
            }
        } catch (Throwable ignore) {
            ignore.fillInStackTrace();
            Log.e("FutureTaskApplication", ignore.toString());
        }
    }

    private void runOnAllProcessMainThread() {

    }

    public void runOnMainProcessMainThread() {

    }

    @Override
    public void highPriority() {

    }

    @Override
    public void mediumPriority() {

    }

    @Override
    public void lowPriority() {

    }
}
