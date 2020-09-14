package com.salton123.model.manager.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.SparseArray;

import com.salton123.model.core.IBaseCore;

/**
 * User: newSalton@outlook.com
 * Date: 2018/3/7 19:08
 * ModifyTime: 19:08
 * Description:
 */
public interface IFragmentLifeCycle extends IBaseCore {
    void init(FragmentManager fragmentManager);

    void unInit(FragmentManager fragmentManager);

    SparseArray<Fragment> getComponents();

    class Factory {
        public static IFragmentLifeCycle get() {
            return FragmentLifeCycleManager.INSTANCE();
        }
    }
}
