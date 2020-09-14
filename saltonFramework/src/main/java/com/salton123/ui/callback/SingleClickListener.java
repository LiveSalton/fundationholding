package com.salton123.ui.callback;

import android.os.SystemClock;
import android.view.View;

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/16 9:47
 * ModifyTime: 9:47
 * Description:
 */
public abstract class SingleClickListener implements View.OnClickListener {
    private int mInterval = 500;
    private long lastClickTime = 0;

    public SingleClickListener(int interval) {
        this.mInterval = interval;
    }

    public SingleClickListener() {

    }

    @Override
    public void onClick(View v) {
        long currentTime = SystemClock.elapsedRealtime();
        long delayTime = currentTime - lastClickTime;
        if (delayTime > mInterval) {
            onSingleClick(v);
            lastClickTime = currentTime;
        } else {
            onDoubleClick(v);
        }
    }

    public void onDoubleClick(View v) {

    }

    public abstract void onSingleClick(View v);
}
