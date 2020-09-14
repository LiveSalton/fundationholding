package com.salton123.feature;

import android.app.Activity;

import com.gyf.immersionbar.ImmersionBar;
import com.salton123.arch.view.IImmersionView;

import androidx.fragment.app.Fragment;

/**
 * User: newSalton@outlook.com
 * Date: 2018/12/25 4:51 PM
 * ModifyTime: 4:51 PM
 * Description:
 */
public class ImmersionFeature implements IFeature, IImmersionView {
    private ImmersionBar mImmersionBar;
    private Object mHost;

    public ImmersionFeature(Object host) {
        if (host instanceof Activity ||
                host instanceof Fragment) {
            this.mHost = host;
        } else {
            throw new IllegalArgumentException("host must instanceof activity or fragment");
        }
    }

    @Override
    public void onBindLogic() {
        mImmersionBar = getImmersionBar();
        mImmersionBar.init();
    }

    @Override
    public void onBindUI() {

    }

    public ImmersionBar getImmersionBar() {
        if (mImmersionBar == null) {
            if (mHost instanceof Activity) {
                mImmersionBar = ImmersionBar.with((Activity) mHost);
            } else if (mHost instanceof Fragment) {
                mImmersionBar = ImmersionBar.with((Fragment) mHost);
            }
        }
        return mImmersionBar;
    }

    @Override
    public void onUnBind() {
    }

    public void dardFont() {
        getImmersionBar().statusBarDarkFont(true).init();
    }

    public void lightFont() {
        getImmersionBar().statusBarDarkFont(false).init();
    }
}
