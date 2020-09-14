package com.salton123.xmly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hjq.toast.ToastUtils;

import com.salton123.log.XLog;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

/**
 * User: newSalton@outlook.com
 * Date: 2019-09-15 22:28
 * ModifyTime: 22:28
 * Description:
 */
public class TingPlayerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.i(this, "MyPlayerReceiver.onReceive " + intent);
        if (intent.getAction().equals("com.salton123.soulove.android.Action_Close")) {
            ToastUtils.show("通知栏点击了关闭");
            XmPlayerManager.release();
        } else if (intent.getAction().equals("com.salton123.soulove.android.Action_PAUSE_START")) {
            if (XmPlayerManager.getInstance(context).isPlaying()) {
                XmPlayerManager.getInstance(context).pause();
            } else {
                XmPlayerManager.getInstance(context).play();
            }
        }
    }
}
