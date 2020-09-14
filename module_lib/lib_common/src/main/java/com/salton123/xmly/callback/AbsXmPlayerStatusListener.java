package com.salton123.xmly.callback;

import com.salton123.log.XLog;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

/**
 * User: newSalton@outlook.com
 * Date: 2018/6/13 下午2:38
 * ModifyTime: 下午2:38
 * Description:
 */
public abstract class AbsXmPlayerStatusListener implements IXmPlayerStatusListener {
    private String TAG;

    public AbsXmPlayerStatusListener(String from) {
        TAG = from;
    }

    @Override
    public void onPlayStart() {
        XLog.d(TAG, "[onPlayStart]");
    }

    @Override
    public void onPlayPause() {
        XLog.d(TAG, "[onPlayPause]");
    }

    @Override
    public void onPlayStop() {
        XLog.d(TAG, "[onPlayStop]");
    }

    @Override
    public void onSoundPlayComplete() {
        XLog.d(TAG, "[onSoundPlayComplete]");
    }

    @Override
    public void onSoundPrepared() {
        XLog.d(TAG, "[onSoundPrepared]");
    }

    @Override
    public void onSoundSwitch(PlayableModel preview, PlayableModel current) {
        XLog.d(TAG, "[onSoundSwitch]");
    }

    @Override
    public void onBufferingStart() {
        XLog.d(TAG, "[onBufferingStart]");
    }

    @Override
    public void onBufferingStop() {
        XLog.d(TAG, "[onBufferingStop]");
    }

    @Override
    public void onBufferProgress(int i) {
        XLog.d(TAG, "[onBufferProgress] i=" + i);
    }

    @Override
    public void onPlayProgress(int i, int i1) {
        XLog.d(TAG, "[onPlayProgress] i=" + i + ",i1= " + i1);
    }

    @Override
    public boolean onError(XmPlayerException e) {
        XLog.e(TAG, "[onError] ex=" + e.getMessage());
        return false;
    }
}
