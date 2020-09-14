package com.salton123.app.future;

import java.util.concurrent.CountDownLatch;

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/9 17:29
 * ModifyTime: 17:29
 * Description: 在什么
 */
public enum FutureTaskLoader {
    INSTANCE;
    private int FUTURE_PRIORITY_HIGH = 10;
    private int FUTURE_PRIORITY_MEDIUM = 9;
    private int FUTURE_PRIORITY_LOW = 8;

    public void init(final IFutureTaskPriority priority, final CountDownLatch latch) {
        Thread futureHight = new Thread(new Runnable() {
            @Override
            public void run() {
                priority.highPriority();
                latch.countDown();
            }
        }, "futureHight");
        futureHight.setPriority(FUTURE_PRIORITY_HIGH);
        futureHight.start();

        Thread futureMedium = new Thread(new Runnable() {
            @Override
            public void run() {
                priority.mediumPriority();
            }
        }, "futureMedium");
        futureMedium.setPriority(FUTURE_PRIORITY_MEDIUM);
        futureMedium.start();

        Thread futureLow = new Thread(new Runnable() {
            @Override
            public void run() {
                priority.lowPriority();
            }
        }, "futureLow");
        futureLow.setPriority(FUTURE_PRIORITY_LOW);
        futureLow.start();
    }

}
