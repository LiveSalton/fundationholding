package com.salton123.infopanel;

import android.app.Activity;
import android.app.Application;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.SidePattern;
import com.salton123.infopanel.entity.Info;
import com.salton123.qa.QualityAssistant;
import com.salton123.qa.kit.ToolKitsDialog;
import com.salton123.util.ScreenUtils;
import com.zhenai.qa.R;

import java.lang.ref.WeakReference;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/26 12:39
 * ModifyTime: 12:39
 * Description:
 */
public enum InfoDispatcher {
    INSTANCE;
    private WeakReference<IInfoView> stackView;
    private InfoPoller mInfoPoller;
    public boolean isInstalled = false;
    private WeakReference<Activity> mCurrentAty;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks
            = new ActivityLifecycleCallbacksAdapter() {
        @Override
        public void onActivityResumed(Activity activity) {
            mCurrentAty = new WeakReference<>(activity);
            boolean isIntercept = activity instanceof IInterceptView;
            if (isInstalled && !isIntercept) {
                addView(activity);
            }
        }
    };

    public void install() {
        isInstalled = true;
        mInfoPoller = new InfoPoller() {
            @Override
            public void postInfo(final Info info) {
                super.postInfo(info);
                if (stackView != null && stackView.get() != null) {
                    stackView.get().updateInfo(info);
                }
            }
        };
        mInfoPoller.start();
        if (mCurrentAty != null && mCurrentAty.get() != null) {
            addView(mCurrentAty.get());
        }
        QualityAssistant.application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public void uninstall() {
        isInstalled = false;
        if (mInfoPoller != null) {
            mInfoPoller.stop();
        }
        if (mCurrentAty != null && mCurrentAty.get() != null) {
            removeView(mCurrentAty.get());
        }
        QualityAssistant.application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    private void removeView(Activity activity) {
        View root = activity.findViewById(android.R.id.content);
        if (root instanceof FrameLayout) {
            View infoView = root.findViewById(R.id.salton_id_info_panel);
            if (infoView != null) {
                EasyFloat.hide(activity);
                stackView.clear();
                return;
            }
        }
    }

    private void addView(Activity activity) {
        View root = activity.findViewById(android.R.id.content);
        if (root instanceof FrameLayout) {
            View infoView = root.findViewById(R.id.salton_id_info_panel);
            if (infoView != null) {
                return;
            }
            EasyFloat
                    .with(activity)
                    .setSidePattern(SidePattern.DEFAULT)
                    .setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, ScreenUtils.dp2px(50))
                    .setLayout(R.layout.view_item_simple_info, view -> {
                        UsageInfoView usageInfoView = view.findViewById(R.id.salton_id_info_panel);
                        usageInfoView.setOnClickListener(v -> new ToolKitsDialog(activity).show());
                        stackView = new WeakReference<>(usageInfoView);
                    })
                    .show();
        }
    }

}
