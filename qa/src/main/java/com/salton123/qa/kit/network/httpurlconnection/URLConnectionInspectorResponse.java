/*
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.salton123.qa.kit.network.httpurlconnection;

import com.salton123.qa.kit.network.core.NetworkInterpreter;
import com.salton123.qa.kit.network.utils.StreamUtil;

import java.io.IOException;
import java.net.HttpURLConnection;

public class URLConnectionInspectorResponse
        extends URLConnectionInspectorHeaders
        implements NetworkInterpreter.InspectorResponse {
    private final int mRequestId;
    private final String mUrl;
    private final int mStatusCode;

    public URLConnectionInspectorResponse(int requestId, HttpURLConnection conn, int statusCode) throws IOException {
        super(StreamUtil.convertHeaders(conn.getHeaderFields()));
        mRequestId = requestId;
        mUrl = conn.getURL().toString();
        mStatusCode = statusCode;
    }

    @Override
    public int requestId() {
        return mRequestId;
    }

    @Override
    public String url() {
        return mUrl;
    }

    @Override
    public int statusCode() {
        return mStatusCode;
    }

}
