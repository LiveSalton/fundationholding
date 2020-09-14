package com.salton123.qa.kit.network;

import android.os.Handler;
import android.os.Looper;

import com.salton123.qa.kit.network.bean.NetworkRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @desc: 提供网络抓包功能开启、关闭、数据统计功能的manager
 */
public class NetworkManager {
    private static final int MAX_SIZE = 100;

    private long mStartTime;
    private OnNetworkInfoUpdateListener mOnNetworkInfoUpdateListener;

    private int mPostCount;
    private int mGetCount;
    private int mTotalCount;

    public NetworkManager() {
    }

    public NetworkRecord getRecord(int requestId) {
        for (NetworkRecord record :
                mRecords) {
            if (record.mRequestId == requestId) {
                return record;
            }
        }
        return null;
    }

    private static class Holder {
        private static NetworkManager INSTANCE = new NetworkManager();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean mIsActive = new AtomicBoolean(false);

    /**
     * 这个数据结构要求有序（方便移除最旧的数据），线程安全（网络请求是在子线程内执行，会在子线程内对数据进行查询插入删除操作），方便查找
     * （需要根据requestId，找到对应的record），目前没找到同时满足三个条件的数据结构，暂时先保证前两者，因为限制了大小为MAX_SIZE，查找
     * 的数据量不会很大，直接foreach
     */
    private List<NetworkRecord> mRecords = Collections.synchronizedList(new ArrayList<NetworkRecord>());

    public static NetworkManager get() {
        return NetworkManager.Holder.INSTANCE;
    }

    public void addRecord(int requestId, NetworkRecord record) {
        if (mRecords.size() > MAX_SIZE) {
            mRecords.remove(0);
        }
        if (record.isPostRecord()) {
            mPostCount++;
        } else if (record.isGetRecord()) {
            mGetCount++;
        }
        mTotalCount++;
        mRecords.add(record);
        updateRecord(record, true);
    }

    public void updateRecord(final NetworkRecord record, final boolean add) {
        if (mOnNetworkInfoUpdateListener != null) {
            /**
             * post to main thread
             */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mOnNetworkInfoUpdateListener != null) {
                        mOnNetworkInfoUpdateListener.onNetworkInfoUpdate(record, add);
                    }
                }
            });
        }
    }

    public void startMonitor() {
        if (mIsActive.get()) {
            return;
        }
        mIsActive.set(true);
        mStartTime = System.currentTimeMillis();
    }

    public void stopMonitor() {
        if (!mIsActive.get()) {
            return;
        }
        mIsActive.set(false);
        mStartTime = 0;
    }

    public static boolean isActive() {
        return get().mIsActive.get();
    }

    public List<NetworkRecord> getRecords() {
        return mRecords;
    }

    public void setOnNetworkInfoUpdateListener(OnNetworkInfoUpdateListener onNetworkInfoUpdateListener) {
        mOnNetworkInfoUpdateListener = onNetworkInfoUpdateListener;
    }

    public long getRunningTime() {
        if (mStartTime == 0) {
            return mStartTime;
        }
        long time = System.currentTimeMillis() - mStartTime;
        return time;
    }

    public long getTotalRequestSize() {
        long totalSize = 0;
        for (NetworkRecord record : mRecords) {
            totalSize += record.requestLength;
        }
        return totalSize;
    }

    public long getTotalSize() {
        long totalSize = 0;
        for (NetworkRecord record : mRecords) {
            totalSize += record.requestLength;
            totalSize += record.responseLength;
        }
        return totalSize;
    }

    public long getTotalResponseSize() {
        long totalSize = 0;
        for (NetworkRecord record : mRecords) {
            totalSize += record.responseLength;
        }
        return totalSize;
    }

    public int getPostCount() {
        return mPostCount;
    }

    public int getGetCount() {
        return mGetCount;
    }

    public int getTotalCount() {
        return mTotalCount;
    }
}
