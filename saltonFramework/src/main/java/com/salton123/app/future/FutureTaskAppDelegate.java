package com.salton123.app.future;

import android.app.Application;
import androidx.annotation.NonNull;
import android.util.Log;

import com.salton123.util.CommonUtils;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * User: newSalton@outlook.com
 * Date: 2019/12/27 15:05
 * ModifyTime: 15:05
 * Description:
 */
public class FutureTaskAppDelegate implements IFutureTaskPriority {
    //只有高优先级的任务需要和主线程保持同步，其他线程的任务异步处理，加快初始化过程。
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    public static Application sInstance;

    public static <T extends Application> T getInstance() {
        return (T) sInstance;
    }

    public FutureTaskAppDelegate(@NonNull Application application) {
        this.sInstance = application;
    }

    public void onCreate() {
        try {
            if (CommonUtils.isMainProcess()) {  //主进程
                runOnMainProcessMainThread();
                FutureTaskLoader.INSTANCE.init(this, mCountDownLatch);
                mCountDownLatch.await(1500, TimeUnit.MILLISECONDS);
            } else {
                runOnAllProcessMainThread();
            }
            String rootDir = MMKV.initialize(sInstance);
            System.out.println("mmkv root: " + rootDir);
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
