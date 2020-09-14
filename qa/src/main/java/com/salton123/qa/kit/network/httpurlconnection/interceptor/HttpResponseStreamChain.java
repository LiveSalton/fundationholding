package com.salton123.qa.kit.network.httpurlconnection.interceptor;

import java.io.IOException;
import java.util.List;

/**
 * @date: 2019/3/11
 * @desc: 响应body处理链
 */
public class HttpResponseStreamChain extends InterceptorChain<HttpResponse, DKInterceptor> {

    public HttpResponseStreamChain(List<DKInterceptor> interceptors) {
        super(interceptors);
    }

    public HttpResponseStreamChain(List<DKInterceptor> interceptors, int index) {
        super(interceptors, index);
    }

    @Override
    protected void processNext(HttpResponse response, List<DKInterceptor> interceptors,
                               int index) throws IOException {
        DKInterceptor interceptor = interceptors.get(index);
        if (interceptor != null) {
            index++;
            interceptor.intercept(this, response);
        }
    }

}
