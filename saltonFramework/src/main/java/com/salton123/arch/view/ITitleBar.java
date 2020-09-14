package com.salton123.arch.view;

import android.view.View;

import com.salton123.ui.base.BaseActivity;
import com.salton123.ui.base.BaseDialogFragment;
import com.salton123.ui.base.BaseFragment;

import androidx.annotation.DrawableRes;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 15:22
 * Time: 15:22
 * Description:
 */
public interface ITitleBar {

    default boolean enableTitleBar() {
        return true;
    }

    default int getRightStyle() {
        return TitleBarStyle.HIDDEN;
    }

    default String getRightText() {
        return null;
    }

    default @DrawableRes
    Integer getRightIcon() {
        return null;
    }

    default void onRightClick(View v) {
    }

    default int getLeftStyle() {
        return TitleBarStyle.ICON_TEXT;
    }

    default String getLeftText() {
        return "返回";
    }

    default @DrawableRes
    Integer getLeftIcon() {
        return null;
    }

    default void onLeftClick(View v) {
        if (this instanceof BaseActivity) {
            ((BaseActivity) this).onBackPressedSupport();
        } else if (this instanceof BaseFragment) {
            boolean comsume = ((BaseFragment) this).onBackPressedSupport();
            if (!comsume) {
                ((BaseFragment) this).activity().finish();
            }
        } else if (this instanceof BaseDialogFragment) {
            ((BaseDialogFragment) this).dismissAllowingStateLoss();
        }
    }

    default String getTitleText() {
        return null;
    }
}

