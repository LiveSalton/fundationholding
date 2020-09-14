package com.salton123.model.manager.lifecycle;

import android.app.Application;

import com.salton123.model.core.IBaseCore;

/**
 * User: newSalton@outlook.com
 * Date: 2018/3/7 19:04
 * ModifyTime: 19:04
 * Description:代理模式实现生命周期管理处理
 */
public interface IActivityLifeCycle extends IBaseCore {
    void init(Application sApplication);

    void unInit();

    class Factory {
        public static IActivityLifeCycle get() {
            return ActivityLifeCycleManager.INSTANCE;
        }
    }
}
