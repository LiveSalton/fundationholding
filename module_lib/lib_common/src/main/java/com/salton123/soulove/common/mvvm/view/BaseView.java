package com.salton123.soulove.common.mvvm.view;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.soulove.common.R;
import com.salton123.soulove.common.mvvm.view.status.BlankStatus;
import com.salton123.soulove.common.mvvm.view.status.EmptyStatus;
import com.salton123.soulove.common.mvvm.view.status.ErrorStatus;
import com.salton123.soulove.common.mvvm.view.status.LoadingStatus;
import com.kingja.loadsir.callback.Callback;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.List;

/**
 * Author: Thomas.<br/>
 * Date: 2019/10/24 15:25<br/>
 * GitHub: https://github.com/TanZhiL<br/>
 * CSDN: https://blog.csdn.net/weixin_42703445<br/>
 * Email: 1071931588@qq.com<br/>
 * Description: Activity和Fragment公用方法
 */
public interface BaseView {

    enum  SimpleBarStyle {
        /**
         * 返回图标(默认)
         */
         LEFT_BACK ,
        /**
         * 返回图标+文字
         */
        LEFT_BACK_TEXT ,
        /**
         * 附加图标
         */
        LEFT_ICON ,
        /**
         * 标题(默认)
         */
         CENTER_TITLE ,
        /**
         * 自定义布局
         */
         CENTER_CUSTOME ,
        /**
         * 文字
         */
         RIGHT_TEXT ,
        /**
         * 图标(默认)
         */
         RIGHT_ICON ,
        /**
         * 自定义布局
         */
         RIGHT_CUSTOME ,
    }
    /**
     * 提供状态布局
     *
     * @return
     */
    default Callback getInitStatus() {
        return new BlankStatus();
    }

    default Callback getLoadingStatus() {
        return new LoadingStatus();
    }

    default Callback getErrorStatus() {
        return new ErrorStatus();
    }

    default Callback getEmptyStatus() { return new EmptyStatus();
    }
    /**
     * 提供额外状态布局
     *
     * @return
     */
    default @Nullable
    List<Callback> getExtraStatus() {
        return null;
    }
    /**
     * 初始化通用标题栏
     */
    default void initSimpleBar(CommonTitleBar mSimpleTitleBar) {
        // 中间
        if (onBindBarCenterStyle() == SimpleBarStyle.CENTER_TITLE) {
            String[] strings = onBindBarTitleText();
            if (strings != null && strings.length > 0) {
                if (null != strings[0] && strings[0].trim().length() > 0) {
                    TextView title = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_title);
                    title.setVisibility(View.VISIBLE);
                    title.setText(strings[0]);
                }
                if (strings.length > 1 && null != strings[1] && strings[1].trim().length() > 0) {
                    TextView subtitle = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_subtitle);
                    subtitle.setVisibility(View.VISIBLE);
                    subtitle.setText(strings[1]);
                }
            }
        } else if (onBindBarCenterStyle() == BaseFragment.SimpleBarStyle.CENTER_CUSTOME && onBindBarCenterCustome() != null) {
            ViewGroup group = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.fl_custome);
            group.setVisibility(View.VISIBLE);
            group.addView(onBindBarCenterCustome(), new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        //左边
        if (onBindBarLeftStyle() == BaseFragment.SimpleBarStyle.LEFT_BACK) {

            ImageView backView = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.iv_back);
            if (onBindBarBackIcon() != null) {
                backView.setImageResource(onBindBarBackIcon());
            }
            backView.setVisibility(View.VISIBLE);
            backView.setOnClickListener(v -> onSimpleBackClick());
        } else if (onBindBarLeftStyle() == BaseFragment.SimpleBarStyle.LEFT_BACK_TEXT) {
            View backIcon = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.iv_back);
            backIcon.setVisibility(View.VISIBLE);
            backIcon.setOnClickListener(v -> onSimpleBackClick());
            View backTv = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.tv_back);
            backTv.setVisibility(View.VISIBLE);
            backTv.setOnClickListener(v -> onSimpleBackClick());
        } else if (onBindBarLeftStyle() == BaseFragment.SimpleBarStyle.LEFT_ICON && onBindBarLeftIcon() != null) {
            ImageView icon = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.iv_left);
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(onBindBarLeftIcon());
            icon.setOnClickListener(this::onLeftIconClick);
        }
        //右边
        switch (onBindBarRightStyle()) {
            case RIGHT_TEXT:
                String[] strings = onBindBarRightText();
                if (strings == null || strings.length == 0) {
                    break;
                }
                if (null != strings[0] && strings[0].trim().length() > 0) {
                    TextView tv1 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.tv1_right);
                    tv1.setVisibility(View.VISIBLE);
                    tv1.setText(strings[0]);
                    tv1.setOnClickListener(this::onRight1Click);
                }
                if (strings.length > 1 && null != strings[1] && strings[1].trim().length() > 0) {
                    TextView tv2 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.tv2_right);
                    tv2.setVisibility(View.VISIBLE);
                    tv2.setText(strings[1]);
                    tv2.setOnClickListener(this::onRight2Click);
                }
                break;
            case RIGHT_ICON:
                Integer[] ints = onBindBarRightIcon();
                if (ints == null || ints.length == 0) {
                    break;
                }
                if (null != ints[0]) {
                    ImageView iv1 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.iv1_right);
                    iv1.setVisibility(View.VISIBLE);
                    iv1.setImageResource(ints[0]);
                    iv1.setOnClickListener(this::onRight1Click);
                }
                if (ints.length > 1 && null != ints[1]) {
                    ImageView iv2 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.iv2_right);
                    iv2.setVisibility(View.VISIBLE);
                    iv2.setImageResource(ints[1]);
                    iv2.setOnClickListener(this::onRight2Click);
                }
                break;
            case RIGHT_CUSTOME:
                if (onBindBarRightCustome() != null) {
                    ViewGroup group = mSimpleTitleBar.getRightCustomView().findViewById(R.id.fl_custome);
                    group.setVisibility(View.VISIBLE);
                    group.addView(onBindBarRightCustome(), new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
                break;
        }

    }



    /**
     * 是否开启通用标题栏,默认true
     *
     * @return
     */
    default boolean enableSimplebar() {
        return true;
    }

    /**
     * 初始化右边标题栏类型
     *
     * @return
     */
    default SimpleBarStyle onBindBarRightStyle() {
        return SimpleBarStyle.RIGHT_ICON;
    }

    /**
     * 初始化左边标题栏类型
     *
     * @return
     */
    default SimpleBarStyle onBindBarLeftStyle() {
        return SimpleBarStyle.LEFT_BACK;
    }

    /**
     * 初始化中间标题栏类型
     *
     * @return
     */
    default SimpleBarStyle onBindBarCenterStyle() {
        return SimpleBarStyle.CENTER_TITLE;
    }

    /**
     * 初始化标题栏右边文本
     *
     * @return
     */
    default String[] onBindBarRightText() {
        return null;
    }

    /**
     * 初始化标题文本
     *
     * @return
     */
    default String[] onBindBarTitleText() {
        return null;
    }

    /**
     * 初始化标题栏右边图标
     *
     * @return
     */
    default @DrawableRes
    Integer[] onBindBarRightIcon() {
        return null;
    }

    /**
     * 初始化标题栏左边附加图标
     *
     * @return
     */
    default @DrawableRes
    Integer onBindBarLeftIcon() {
        return null;
    }

    /**
     * 初始化标题栏左边返回按钮图标
     *
     * @return
     */
    default @DrawableRes
    Integer onBindBarBackIcon() {
        return null;
    }

    /**
     * 点击标题栏返回按钮事件
     */
     void onSimpleBackClick();
    /**
     * 初始化标题栏右侧自定义布局
     *
     * @return
     */
    default View onBindBarRightCustome() {
        return null;
    }

    /**
     * 初始化标题栏中间自定义布局
     *
     * @return
     */
    default View onBindBarCenterCustome() {
        return null;
    }

    /**
     * 点击标题栏靠右第一个事件
     *
     * @return
     */
    default void onRight1Click(View v) {

    }

    /**
     * 点击标题栏靠右第二个事件
     *
     * @return
     */
    default void onRight2Click(View v) {

    }

    /**
     * 点击标题栏靠左附加事件
     *
     * @return
     */
    default void onLeftIconClick(View v) {

    }
}
