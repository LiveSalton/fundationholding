package com.salton123.qa.kit.network.httpurlconnection;

import android.util.Log;
import android.util.Pair;

import com.salton123.qa.kit.network.NetworkManager;
import com.salton123.qa.kit.network.bean.NetworkRecord;
import com.salton123.qa.kit.network.core.DefaultResponseHandler;
import com.salton123.qa.kit.network.core.NetworkInterpreter;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.DKInterceptor;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpRequest;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpRequestChain;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpRequestStreamChain;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpResponse;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpResponseChain;
import com.salton123.qa.kit.network.httpurlconnection.interceptor.HttpResponseStreamChain;
import com.salton123.qa.kit.network.stream.HttpOutputStreamProxy;
import com.salton123.qa.kit.network.stream.OutputStreamProxy;
import com.salton123.qa.kit.network.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * @desc: 监听HttpUrlConnection数据的拦截器
 */
public class HttpMonitorInterceptor implements DKInterceptor<HttpRequest, HttpResponse> {
    private static final String TAG = "HeaderParseInterceptor";
    private final NetworkInterpreter mInterpreter;

    public HttpMonitorInterceptor() {
        mInterpreter = NetworkInterpreter.get();
    }

    @Override
    public void intercept(@NonNull HttpRequestChain chain, @NonNull HttpRequest request) throws IOException {
        int requestId = request.getId();
        if (NetworkManager.get().getRecord(requestId) != null) {
            chain.process(request);
            return;
        }
        HttpURLConnection connection = request.getURLConnection();
        ArrayList<Pair<String, String>> header;
        // connect参数不知道什么会被置为true，以防崩溃，这里直接try-catch住
        try {
            header = StreamUtil.convertHeaders(connection.getRequestProperties());
        } catch (Exception e) {
            Log.e(TAG, "get head exception", e);
            header = new ArrayList<>();
        }
        URLConnectionInspectorRequest inspectorRequest = new URLConnectionInspectorRequest(
                requestId,
                header,
                connection);
        mInterpreter.createRecord(requestId, inspectorRequest);
        chain.process(request);
    }

    @Override
    public void intercept(@NonNull HttpResponseChain chain, @NonNull HttpResponse response) throws IOException {
        int id = response.getId();
        NetworkRecord record = NetworkManager.get().getRecord(id);
        if (record == null) {
            chain.process(response);
            return;
        }
        URLConnectionInspectorResponse urlConnectionInspectorResponse = new URLConnectionInspectorResponse(
                response.getId(),
                response.getURLConnection(),
                response.getStatusCode());
        mInterpreter.fetchResponseInfo(record, urlConnectionInspectorResponse);
        chain.process(response);
    }

    @Override
    public void intercept(@NonNull HttpRequestStreamChain chain, @NonNull HttpRequest request) throws IOException {
        OutputStreamProxy outputStream =
                new HttpOutputStreamProxy(request.getOutputStream(), request.getId(), mInterpreter);
        request.setOutputStream(outputStream);
        chain.process(request);
    }

    @Override
    public void intercept(@NonNull HttpResponseStreamChain chain, @NonNull HttpResponse response) throws IOException {
        int id = response.getId();
        NetworkRecord record = NetworkManager.get().getRecord(id);
        if (record == null) {
            chain.process(response);
        }
        HttpURLConnection connection = response.getURLConnection();
        InputStream responseStream = mInterpreter.interpretResponseStream(
                connection.getHeaderField("Content-Type"),
                response.getInputStream(),
                new DefaultResponseHandler(mInterpreter, id, record));
        response.setInputStream(responseStream);
        chain.process(response);
    }
}
