package com.salton123.qa.kit.timecounter.instrumentation;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.salton123.qa.kit.timecounter.TimeCounterManager;
import com.salton123.utils.Reflector;

/**
 * @author: linjizong
 * @date: 2019/6/3
 * @desc:
 */
class ProxyHandlerCallback implements Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;
    public static final int PAUSE_ACTIVITY = 101;
    public static final int EXECUTE_TRANSACTION = 159;
    public static final String LAUNCH_ITEM_CLASS = "android.app.servertransaction.ResumeActivityItem";
    public static final String PAUSE_ITEM_CLASS = "android.app.servertransaction.PauseActivityItem";

    public final Handler.Callback mOldCallback;
    public final Handler mHandler;

    ProxyHandlerCallback(Handler.Callback oldCallback, Handler handler) {
        mOldCallback = oldCallback;
        mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        int msgType = preDispatch(msg);
        if (mOldCallback != null && mOldCallback.handleMessage(msg)) {
            postDispatch(msgType);
            return true;
        }
        mHandler.handleMessage(msg);
        postDispatch(msgType);
        return true;
    }

    private int preDispatch(Message msg) {
        switch (msg.what) {
            case LAUNCH_ACTIVITY:
                TimeCounterManager.get().onActivityLaunch();
                break;
            case PAUSE_ACTIVITY:
                TimeCounterManager.get().onActivityPause();
                break;
            case EXECUTE_TRANSACTION:
                return handlerActivity(msg);
        }
        return msg.what;
    }

    private int handlerActivity(Message msg) {
        Object obj = msg.obj;

        Object activityCallback = Reflector.QuietReflector.with(obj).method("getLifecycleStateRequest").call();
        if (activityCallback != null) {
            String transactionName = activityCallback.getClass().getCanonicalName();
            if (TextUtils.equals(transactionName, LAUNCH_ITEM_CLASS)) {
                TimeCounterManager.get().onActivityLaunch();
                return LAUNCH_ACTIVITY;
            } else if (TextUtils.equals(transactionName, PAUSE_ITEM_CLASS)) {
                TimeCounterManager.get().onActivityPause();
                return PAUSE_ACTIVITY;
            }
        }
        return msg.what;
    }

    private void postDispatch(int msgType) {
        switch (msgType) {
            case LAUNCH_ACTIVITY:
                TimeCounterManager.get().onActivityLaunched();
                break;
            case PAUSE_ACTIVITY:
                TimeCounterManager.get().onActivityPaused();
                break;
        }
    }
}
