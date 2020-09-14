package com.salton123.qa.kit.network.httpurlconnection.interceptor;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 *
 */
public interface DKInterceptor<T, S> {
    /**
     * 拦截请求，处理header、method之类的信息
     *
     * @param chain
     * @param request
     * @throws IOException
     */
    void intercept(@NonNull HttpRequestChain chain, @NonNull T request) throws IOException;

    /**
     * 拦截响应，处理header、statusCode之类的信息
     *
     * @param chain
     * @param response
     * @throws IOException
     */
    void intercept(@NonNull HttpResponseChain chain, @NonNull S response) throws IOException;

    /**
     * 拦截请求流，用以对post body做处理
     *
     * @param chain
     * @param request
     * @throws IOException
     */
    void intercept(@NonNull HttpRequestStreamChain chain, @NonNull T request) throws IOException;

    /**
     * 拦截响应流，用以对返回值做处理
     *
     * @param chain
     * @param response
     * @throws IOException
     */
    void intercept(@NonNull HttpResponseStreamChain chain, @NonNull S response) throws IOException;

}
