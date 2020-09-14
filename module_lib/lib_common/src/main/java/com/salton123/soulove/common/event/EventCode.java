package com.salton123.soulove.common.event;

/**
 * Author: Thomas.
 * <br/>Date: 2019/9/18 13:58
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:EventBus 标识
 */
public interface EventCode {
    interface Main {
        int NAVIGATE = 1001;
        int HIDE_GP = 1002;
        int SHOW_GP = 1003;
        int SHARE = 1007;
    }

    interface Listen {
        int DOWNLOAD_SORT = 3000;
        int DOWNLOAD_DELETE = 3001;
    }

    interface Common {
        int REGISTER_SUCCESS = 10001;
        int REGISTER_FAIL = 10002;
        int LOGIN_SUCCESS = 10003;
        int LOGIN_FAIL = 10004;
    }
}
