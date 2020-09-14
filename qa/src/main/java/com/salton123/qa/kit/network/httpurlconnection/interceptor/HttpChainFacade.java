package com.salton123.qa.kit.network.httpurlconnection.interceptor;

import java.io.IOException;
import java.util.List;

/**
 * @date: 2019/3/14
 * @desc: 对几个处理链的包装
 */
public class HttpChainFacade {
    private final HttpRequestChain mHttpRequestChain;
    private final HttpResponseChain mHttpResponseChain;
    private final HttpRequestStreamChain mHttpRequestStreamChain;
    private final HttpResponseStreamChain mHttpResponseStreamChain;

    public HttpChainFacade(List<DKInterceptor> interceptors) {
        mHttpRequestChain = new HttpRequestChain(interceptors);
        mHttpResponseChain = new HttpResponseChain(interceptors);
        mHttpRequestStreamChain = new HttpRequestStreamChain(interceptors);
        mHttpResponseStreamChain = new HttpResponseStreamChain(interceptors);
    }

    public void process(HttpRequest request) throws IOException {
        mHttpRequestChain.process(request);
    }

    public void process(HttpResponse response) throws IOException {
        mHttpResponseChain.process(response);
    }

    public void processStream(HttpRequest request) throws IOException {
        mHttpRequestStreamChain.process(request);
    }

    public void processStream(HttpResponse response) throws IOException {
        mHttpResponseStreamChain.process(response);
    }

}
