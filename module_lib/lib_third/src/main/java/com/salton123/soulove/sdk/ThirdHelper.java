package com.salton123.soulove.sdk;

import android.annotation.SuppressLint;
import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastWhiteStyle;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import me.yokeyword.fragmentation.Fragmentation;

public class ThirdHelper {

    private static final String TAG = "ThirdHelper";

    private static Application mApplication;
    private static volatile ThirdHelper instance;

    private ThirdHelper() {
    }

    public static ThirdHelper getInstance(Application application) {
        if (instance == null) {
            synchronized (ThirdHelper.class) {
                if (instance == null) {
                    mApplication = application;
                    instance = new ThirdHelper();
                }
            }
        }
        return instance;
    }

    public ThirdHelper initQualityAssistant() {
        // QualityAssistant.install(new QualityContext(mApplication) {
        //     @Override
        //     public boolean isLeakCanaryEnable() {
        //         return false;
        //     }
        // });
        return this;
    }

    public ThirdHelper initBugly() {
        Beta.largeIconId = R.drawable.third_launcher_ting;
        Beta.smallIconId = R.drawable.third_launcher_ting;
        Beta.upgradeDialogLayoutId = R.layout.third_dialog_update;

        Beta.canNotifyUserRestart = true;
        //生产环境
        // Bugly.init(mApplication, ConstantsThird.Bugly.SPEECH_ID,, false);
        //开发设备
        Bugly.setIsDevelopmentDevice(mApplication, true);
        Bugly.init(mApplication, ConstantsThird.Third.BUGLY_ID, true);
        return this;
    }

    public ThirdHelper initRouter() {

        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
        return this;
    }

    public ThirdHelper initUtils() {
        Utils.init(mApplication);
        ToastUtils.init(mApplication, new ToastWhiteStyle(mApplication));
        // ToastUtils.setView(R.layout.third_layout_toast);
        // ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        KeyboardUtils.clickBlankArea2HideSoftInput();
        initQualityAssistant();
        return this;
    }

    @SuppressLint("RestrictedApi")
    public ThirdHelper initCrashView() {
        return this;
    }

    public ThirdHelper initFragmentation(boolean isDebug) {
        // 建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(isDebug)
                .install();
        return this;
    }
}
