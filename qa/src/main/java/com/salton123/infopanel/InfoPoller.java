package com.salton123.infopanel;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.salton123.infopanel.entity.Info;
import com.salton123.utils.CommonUtils;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/26 12:02
 * ModifyTime: 12:02
 * Description:
 */
public class InfoPoller extends Poller {
    private static final String TAG = "InfoPoller";
    private Info mInfo = new Info();
    private static final int INFO_MESSAGE_WHAT = 0x101;
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INFO_MESSAGE_WHAT:
                    postInfo(mInfo);
                    break;
            }
        }
    };

    @Override
    void pollAction() {
        mInfo.processMemory = DecimalFormatUtil.setScale(CommonUtils.getMyProcessMemory(), 1) + "MB";
        // mInfo.topActivity = CommonUtils.getTopActivity();
        // mInfo.freeMemory = String.valueOf(PerformanceUtils.getFreeMemory());
        // mInfo.totalMemory = String.valueOf(PerformanceUtils.getTotalMemory());
        mInfo.cpuUsage = DecimalFormatUtil.setScale(CommonUtils.getCpuUsage(), 1) + "%";
        // mInfo.topActivity = CommonUtils.getTopActivity();
        Message message = Message.obtain();
        message.what = INFO_MESSAGE_WHAT;
        message.obj = mInfo;
        mMainHandler.sendMessage(message);
    }

    public void postInfo(Info info) {
    }
}
