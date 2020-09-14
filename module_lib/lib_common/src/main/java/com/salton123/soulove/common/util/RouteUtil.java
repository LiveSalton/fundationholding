package com.salton123.soulove.common.util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.salton123.soulove.common.bean.NavigateBean;
import com.salton123.soulove.common.event.ActivityEvent;
import com.salton123.soulove.common.event.EventCode;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Author: Thomas.<br/>
 * Date: 2019/10/24 15:25<br/>
 * GitHub: https://github.com/TanZhiL<br/>
 * CSDN: https://blog.csdn.net/weixin_42703445<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:页面跳转
 */
public class RouteUtil {
    /**
     * 页面跳转
     *
     * @param path
     */
    public static void navigateTo(String path) {
        Object navigation = ARouter.getInstance().build(path).navigation();
        if (null != navigation) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.NAVIGATE,
                    new NavigateBean((ISupportFragment) navigation)));
        }
    }

    public static void navigateTo(String path, int launchMode) {
        Object navigation = ARouter.getInstance().build(path).navigation();
        NavigateBean navigateBean = new NavigateBean((ISupportFragment) navigation);
        navigateBean.setLaunchMode(launchMode);
        if (null != navigation) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.NAVIGATE,
                    new NavigateBean((ISupportFragment) navigation)));
        }
    }

    public static void navigateTo(String path, int launchMode, ExtraTransaction extraTransaction) {
        Object navigation = ARouter.getInstance().build(path).navigation();
        NavigateBean navigateBean = new NavigateBean((ISupportFragment) navigation);
        navigateBean.setLaunchMode(launchMode);
        navigateBean.setExtraTransaction(extraTransaction);
        if (null != navigation) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.NAVIGATE,
                    new NavigateBean((ISupportFragment) navigation)));
        }
    }

    public static void navigateTo(String path, ExtraTransaction extraTransaction) {
        Object navigation = ARouter.getInstance().build(path).navigation();
        NavigateBean navigateBean = new NavigateBean((ISupportFragment) navigation);
        navigateBean.setExtraTransaction(extraTransaction);
        if (null != navigation) {
            EventBus.getDefault().post(new ActivityEvent(EventCode.Main.NAVIGATE,
                    new NavigateBean((ISupportFragment) navigation)));
        }
    }
}
