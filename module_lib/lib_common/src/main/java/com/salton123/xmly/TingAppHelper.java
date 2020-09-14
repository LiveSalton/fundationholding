package com.salton123.xmly;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;

import com.blankj.utilcode.util.ActivityUtils;
import com.salton123.C;
import com.salton123.app.BaseApplication;
import com.salton123.log.XLog;
import com.salton123.soulove.common.BuildConfig;
import com.salton123.soulove.common.Constants;
import com.salton123.soulove.common.bean.PlayHistoryBean;
import com.salton123.soulove.common.db.TingAppDatabase;
import com.salton123.utils.RxUtilCompat;
import com.salton123.xmly.callback.AbsXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.constants.ConstantsOpenSdk;
import com.ximalaya.ting.android.opensdk.datatrasfer.AccessTokenManager;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.NotificationColorUtils;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.player.service.XmMediaPlayerFactory;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerConfig;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;
import com.ximalaya.ting.android.player.XMediaPlayerConstants;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;

import java.io.File;
import java.lang.reflect.Method;

import io.reactivex.functions.Consumer;

/**
 * User: newSalton@outlook.com
 * Date: 2019-09-15 22:14
 * ModifyTime: 22:14
 * Description:
 */
@SuppressLint("CheckResult")
public enum TingAppHelper {
    INSTANCE;
    private Application mApplication;
    private String DEFAULT_SAVE_PATH = C.BASE_PATH + File.separator + "ting";

    public void init(Application application) {
        this.mApplication = application;
        TingAppDatabase.init(mApplication);
        if (BuildConfig.APP_DEVELOP) {
            ConstantsOpenSdk.isDebug = true;
            XMediaPlayerConstants.isDebug = true;
            XMediaPlayerConstants.isDebugPlayer = true;
            XmMediaPlayerFactory.setPlayerMode(false);
        }

        AccessTokenManager.getInstanse().init(mApplication);
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        String mAppSecret = "4c9171bc78b238c544b3fbbf489dffdd";
        mXimalaya.setAppkey("171bc185d4acf6ae9d0ecc8dec7c54fd");
        mXimalaya.setPackid("com.salton123.mamamiya");
        mXimalaya.init(application, mAppSecret);
        if (BaseUtil.isMainProcess(application)) {
            AccessTokenManager.getInstanse().init(application);
            if (AccessTokenManager.getInstanse().hasLogin()) {
                CommonRequest.getInstanse().setITokenStateChange(null);
            }
        }
        try {
            Method method = XmPlayerConfig.getInstance(mApplication).getClass().getDeclaredMethod("setUseSystemPlayer", boolean.class);
            method.setAccessible(true);
            method.invoke(XmPlayerConfig.getInstance(mApplication), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!BaseUtil.isPlayerProcess(application)) {
                boolean hasPermission = mApplication.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(),
                        Process.myUid()) == PackageManager.PERMISSION_GRANTED;

                XmDownloadManager.Builder builder = XmDownloadManager.Builder(application)
                        .maxDownloadThread(3)            // 最大的下载个数 默认为1 最大为3
                        .maxSpaceSize(Long.MAX_VALUE)    // 设置下载文件占用磁盘空间最大值，单位字节。不设置没有限制
                        .connectionTimeOut(15000)        // 下载时连接超时的时间 ,单位毫秒 默认 30000
                        .readTimeOut(15000)                // 下载时读取的超时时间 ,单位毫秒 默认 30000
                        .fifo(false)                    // 等待队列的是否优先执行先加入的任务. false表示后添加的先执行(不会改变当前正在下载的音频的状态) 默认为true
                        .maxRetryCount(3)                // 出错时重试的次数 默认2次
                        .progressCallBackMaxTimeSpan(1000)//  进度条progress 更新的频率 默认是800
                        .requestTracker(new XmlyRequestTracker());    // 日志 可以打印下载信息
                if (hasPermission) {
                    builder.savePath(DEFAULT_SAVE_PATH);    // 保存的地址 会检查这个地址是否有效
                }
                builder.create();
            }

        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(this, e.getMessage());
        }


        // if (BaseUtil.isPlayerProcess(application)) {

        NotificationColorUtils.isTargerSDKVersion24More = true;

        // 此代码表示播放时会去监测下是否已经下载(setDownloadPlayPathCallback 方法已经废弃 请使用如下方法)
        TingAppHelper.INSTANCE.getPlayerManager().setCommonBusinessHandle(XmDownloadManager.getInstance());

        XmNotificationCreater instanse = XmNotificationCreater.getInstanse(application);
        try {
            Notification notification = instanse.initNotification(mApplication, Class.forName(ActivityUtils.getLauncherActivity()));
            TingAppHelper.INSTANCE.getPlayerManager().init(Constants.Third.XIMALAYA_NOTIFICATION, notification);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        instanse.setNextPendingIntent(null);
        instanse.setPrePendingIntent(null);

        String actionName = "com.salton123.soulove.android.Action_Close";
        Intent intent = new Intent(actionName);
        intent.setClass(application, TingPlayerReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(application, 0, intent, 0);
        instanse.setClosePendingIntent(broadcast);

        String pauseActionName = "com.salton123.soulove.android.Action_PAUSE_START";
        Intent intent1 = new Intent(pauseActionName);
        intent1.setClass(application, TingPlayerReceiver.class);
        PendingIntent broadcast1 = PendingIntent.getBroadcast(application, 0, intent1, 0);
        instanse.setStartOrPausePendingIntent(broadcast1);
        getPlayerManager().addPlayerStatusListener(mAbsXmPlayerStatusListener);
    }

    public XmPlayerManager getPlayerManager() {
        return XmPlayerManager.getInstance(BaseApplication.sInstance);
    }

    /**
     * 仅仅处理数据层
     */
    private AbsXmPlayerStatusListener mAbsXmPlayerStatusListener = new AbsXmPlayerStatusListener("TingAppHelper") {

        @Override
        public void onBufferProgress(int i) {
            super.onBufferProgress(i);
            updatePlayRecord();
        }

        @Override
        public void onPlayPause() {
            super.onPlayPause();
            NotificationManager notificationManager = (NotificationManager) mApplication.getSystemService(Context.NOTIFICATION_SERVICE);
            if (null != notificationManager) {
                notificationManager.cancel(Constants.Third.XIMALAYA_NOTIFICATION);
            }
        }

        public void updatePlayRecord() {
            try {
                PlayableModel currSound = TingAppHelper.INSTANCE.getPlayerManager().getCurrSound();
                if (null == currSound) {
                    return;
                }
                if (currSound.getKind().equals(PlayableModel.KIND_SCHEDULE)) {
                    Schedule schedule = (Schedule) currSound;
                    TingAppDatabase.getInstance().playHistoryDao().insert(new PlayHistoryBean(currSound.getDataId(), schedule.getRadioId(), currSound.getKind(),
                            System.currentTimeMillis(), schedule))
                            .compose(RxUtilCompat.schedulersMybeIO())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    XLog.i(this, "insert play history , id : " + aLong);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                            });
                }
                if (currSound.getKind().equals(PlayableModel.KIND_TRACK)) {
                    Track track = (Track) currSound;
                    int currPos = TingAppHelper.INSTANCE.getPlayerManager().getPlayCurrPositon();
                    int duration = TingAppHelper.INSTANCE.getPlayerManager().getDuration();
                    TingAppDatabase.getInstance().playHistoryDao().insert(new PlayHistoryBean(currSound.getDataId(), track.getAlbum().getAlbumId(),
                            currSound.getKind(), 100 * currPos / duration,
                            System.currentTimeMillis(), track))
                            .compose(RxUtilCompat.schedulersMybeIO())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    XLog.i(this, "insert play history , id : " + aLong);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                            });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
