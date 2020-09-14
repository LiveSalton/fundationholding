package com.salton123.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.salton123.log.XLog;


/**
 * 可安全分发任务的Handler
 *
 * @author zhongyongsheng
 */
public class SafeDispatchHandler extends Handler {
    public SafeDispatchHandler(Looper looper) {
        super(looper);
    }

    public SafeDispatchHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public SafeDispatchHandler() {
        super();
    }

    public SafeDispatchHandler(Callback callback) {
        super(callback);
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            XLog.e(this,e.getMessage());
        }
    }
}
