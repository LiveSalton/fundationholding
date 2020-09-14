package com.salton123.xmly;

import com.salton123.log.XLog;
import com.ximalaya.ting.android.sdkdownloader.http.RequestParams;
import com.ximalaya.ting.android.sdkdownloader.http.app.RequestTracker;
import com.ximalaya.ting.android.sdkdownloader.http.request.UriRequest;

/**
 * User: newSalton@outlook.com
 * Date: 2018/5/23 下午5:21
 * ModifyTime: 下午5:21
 * Description:
 */
public class XmlyRequestTracker implements RequestTracker {
    private static final String TAG = "XmlyRequestTracker";

    @Override
    public void onWaiting(RequestParams params) {
        XLog.i(TAG, "TingApplication : onWaiting " + params);
    }

    @Override
    public void onStart(RequestParams params) {
        XLog.i(TAG, "TingApplication : onStart " + params);
    }

    @Override
    public void onRequestCreated(UriRequest request) {
        XLog.i(TAG, "TingApplication : onRequestCreated " + request);
    }

    @Override
    public void onSuccess(UriRequest request, Object result) {
        XLog.i(TAG, "TingApplication : onSuccess " + request + "   result = " + result);
    }

    @Override
    public void onRemoved(UriRequest request) {
        XLog.i(TAG, "TingApplication : onRemoved " + request);
    }

    @Override
    public void onCancelled(UriRequest request) {
        XLog.i(TAG, "TingApplication : onCanclelled " + request);
    }

    @Override
    public void onError(UriRequest request, Throwable ex, boolean isCallbackError) {
        XLog.i(TAG, "TingApplication : onRequestError " + request + "   ex = " + ex + "   isCallbackError = " + isCallbackError);
    }

    @Override
    public void onFinished(UriRequest request) {
        XLog.i(TAG, "TingApplication : onFinished " + request);
    }
}
