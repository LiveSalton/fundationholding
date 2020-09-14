package com.salton123.util;

import android.os.HandlerThread;
import android.os.Looper;


/**
 * 执行延迟或定时任务。
 * <p>
 * Created by qinbo on 14/6/10.
 */
public final class ScheduledTask {
    private volatile Looper mTaskLooper;
    private volatile SafeDispatchHandler mTaskHandler;
    HandlerThread thread;

    /**
     * 私有构造函数，防止误实例化
     */
    private ScheduledTask() {
        thread = new HandlerThread("ScheduledTask");
        thread.start();

        mTaskLooper = thread.getLooper();
        mTaskHandler = new SafeDispatchHandler(mTaskLooper);

    }

    /**
     * 内部类, 用到时才会加载对象
     */
    private static class SingletonHolder {
        public final static ScheduledTask instance = new ScheduledTask();
    }

    /**
     * 获取单实例
     */
    public static ScheduledTask getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 延迟执行任务，单位milliseconds
     */
    public boolean postDelayed(Runnable command, long delay) {
        mTaskHandler.removeCallbacks(command);
        return mTaskHandler.postDelayed(command, delay);
    }

    /**
     * 延迟执行任务，单位milliseconds
     */
    public boolean post(Runnable command) {
        mTaskHandler.removeCallbacks(command);
        return mTaskHandler.post(command);
    }

    /**
     * 指定时刻执行任务，uptimeMillis(using the SystemClock.uptimeMillis() time-base)
     */
    public boolean postAtTime(Runnable command, long uptimeMillis) {
        mTaskHandler.removeCallbacks(command);
        return mTaskHandler.postAtTime(command, uptimeMillis);
    }

    /**
     * 停止计时器
     */
    public void removeCallbacks(Runnable command) {
        mTaskHandler.removeCallbacks(command);
    }

    public boolean isInterrupted() {
        if (thread == null) {
            return false;
        }
        return thread.isInterrupted();
    }
}
