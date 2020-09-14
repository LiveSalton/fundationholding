package com.salton123.qa.kit.timecounter.counter;

import android.app.Activity;

import com.salton123.log.XLog;
import com.salton123.qa.constant.PageTag;
import com.salton123.qa.kit.timecounter.TimeCounterFloatPage;
import com.salton123.qa.kit.timecounter.bean.CounterInfo;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计一个打开Activity操作的耗时分三个阶段，以A打开B为例，第一个阶段是A的pause操作，主要是onPause方法的耗时，第二个阶段是B的Launch操作，主要是
 * onCreate和onResume方法的耗时，第三个阶段是B的Window的渲染操作，调用DecorView的post方法进行统计
 * 这里忽略了和AMS、WMS进行通讯的耗时，主要是因为这部分耗时无法通过非侵入式方式统计，而且用户也无法对这部分耗时做优化（除非重写Activity调用AMS的逻辑），
 * 总耗时=pause+launch+render+other
 */
public class ActivityCounter {

    private static final String TAG = "ActivityCounter";
    private long mStartTime;
    private long mPauseCostTime;
    private long mLaunchStartTime;
    private long mLaunchCostTime;
    private long mRenderStartTime;
    private long mRenderCostTime;
    private long mTotalCostTime;
    private long mOtherCostTime;

    private String mPreviousActivity;
    private String mCurrentActivity;
    private List<CounterInfo> mCounterInfos = new ArrayList<>();

    public void pause() {
        mStartTime = System.currentTimeMillis();
        mPauseCostTime = 0;
        mRenderCostTime = 0;
        mOtherCostTime = 0;
        mLaunchCostTime = 0;
        mLaunchStartTime = 0;
        mTotalCostTime = 0;
        mPreviousActivity = null;
        Activity activity = ActivityUtils.getCurrentResumedActivity();
        if (activity != null) {
            mPreviousActivity = activity.getClass().getSimpleName();
        }
    }

    public void paused() {
        mPauseCostTime = System.currentTimeMillis() - mStartTime;
        XLog.d(TAG, "pause cost：" + mPauseCostTime);
    }

    public void launch() {
        // 可能不走pause，直接打开新页面，比如从后台点击通知栏
        if (mStartTime == 0) {
            mStartTime = System.currentTimeMillis();
            mPauseCostTime = 0;
            mRenderCostTime = 0;
            mOtherCostTime = 0;
            mLaunchCostTime = 0;
            mLaunchStartTime = 0;
            mTotalCostTime = 0;
        }
        mLaunchStartTime = System.currentTimeMillis();
        mLaunchCostTime = 0;
    }

    public void launchEnd() {
        mLaunchCostTime = System.currentTimeMillis() - mLaunchStartTime;
        XLog.d(TAG, "create cost：" + mLaunchCostTime);
        render();
    }

    public void render() {
        mRenderStartTime = System.currentTimeMillis();
        final Activity activity = ActivityUtils.getCurrentResumedActivity();
        if (activity != null && activity.getWindow() != null) {
            mCurrentActivity = activity.getClass().getSimpleName();
            activity.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    activity.getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            renderEnd();
                        }
                    });
                }
            });
        } else {
            renderEnd();
        }
    }

    /**
     * 用户退到后台，点击通知栏打开新页面，这时候需要清空下上次pause记录的时间
     */
    public void enterBackground() {
        mStartTime = 0;
    }

    public void renderEnd() {
        mRenderCostTime = System.currentTimeMillis() - mRenderStartTime;
        XLog.d(TAG, "render cost：" + mRenderCostTime);
        mTotalCostTime = System.currentTimeMillis() - mStartTime;
        XLog.d(TAG, "total cost：" + mTotalCostTime);
        mOtherCostTime = mTotalCostTime - mRenderCostTime - mPauseCostTime - mLaunchCostTime;
        print();
    }

    public void print() {
        TimeCounterFloatPage page =
                (TimeCounterFloatPage) FloatPageManager.getInstance().getFloatPage(PageTag.PAGE_TIME_COUNTER);

        CounterInfo counterInfo = new CounterInfo();
        counterInfo.time = System.currentTimeMillis();
        counterInfo.type = CounterInfo.TYPE_ACTIVITY;
        counterInfo.title = mPreviousActivity + " -> " + mCurrentActivity;
        counterInfo.launchCost = mLaunchCostTime;
        counterInfo.pauseCost = mPauseCostTime;
        counterInfo.renderCost = mRenderCostTime;
        counterInfo.totalCost = mTotalCostTime;
        counterInfo.otherCost = mOtherCostTime;
        mCounterInfos.add(counterInfo);
        if (page != null) {
            page.showInfo(counterInfo);
        }
    }

    public List<CounterInfo> getHistory() {
        return mCounterInfos;
    }
}
