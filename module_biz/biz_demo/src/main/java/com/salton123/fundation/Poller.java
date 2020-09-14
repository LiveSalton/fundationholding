package com.salton123.fundation;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/26 11:09
 * ModifyTime: 11:09
 * Description:轮询器
 */
public abstract class Poller {
    private long mInterval;
    private HandlerThread mThread;
    private Handler mHandler;
    private static final int POLLER_MESSAGE_WHAT = 0x102;

    public Poller(long interval) {
        this.mInterval = interval;
        if (mThread == null) {
            createThread();
        }
    }

    private void createThread(){
        mThread = new HandlerThread("Poller-Thread");
        mThread.setDaemon(true);
        mThread.start();
        mHandler = new Handler(mThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case POLLER_MESSAGE_WHAT:
                        pollAction();
                        if (mHandler != null) {
                            mHandler.sendEmptyMessageAtTime(POLLER_MESSAGE_WHAT, mInterval);
                        }
                        break;
                }
            }
        };
    }

    public void start() {
        // 开启定时获取
        if (mThread == null) {
            createThread();
        }
        mHandler.sendEmptyMessage(POLLER_MESSAGE_WHAT);
    }

    public void stop() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mThread != null) {
            mThread.quitSafely();
            mThread = null;
        }
    }

    public abstract void pollAction();

}
