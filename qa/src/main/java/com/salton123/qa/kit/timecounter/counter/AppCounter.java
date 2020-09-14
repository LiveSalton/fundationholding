package com.salton123.qa.kit.timecounter.counter;

import com.salton123.qa.kit.timecounter.bean.CounterInfo;

/**
 * @desc: App启动耗时
 */
public class AppCounter {

    private long mStartTime;
    private CounterInfo mCounterInfo = new CounterInfo();

    public void start() {
        mStartTime = System.currentTimeMillis();
    }

    public void end() {
        mCounterInfo.title = "App Setup Cost";
        mCounterInfo.totalCost = System.currentTimeMillis() - mStartTime;
        ;
        mCounterInfo.type = CounterInfo.TYPE_APP;
        mCounterInfo.time = System.currentTimeMillis();
    }

    public CounterInfo getAppSetupInfo() {
        return mCounterInfo;
    }
}
