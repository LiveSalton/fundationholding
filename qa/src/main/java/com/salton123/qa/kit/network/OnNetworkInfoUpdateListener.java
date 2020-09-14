package com.salton123.qa.kit.network;

import com.salton123.qa.kit.network.bean.NetworkRecord;

public interface OnNetworkInfoUpdateListener {
    /**
     * 网络请求更新时候的回调
     *
     * @param record
     * @param add    true表示添加，false表示更新
     */
    void onNetworkInfoUpdate(NetworkRecord record, boolean add);
}
