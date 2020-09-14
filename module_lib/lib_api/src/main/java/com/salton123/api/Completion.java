package com.salton123.api;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/24 15:20
 * ModifyTime: 15:20
 * Description:
 */
public interface Completion {
    void onSuccess();

    void onFailed(Throwable err);
}
