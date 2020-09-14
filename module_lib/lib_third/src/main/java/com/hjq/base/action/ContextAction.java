package com.hjq.base.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/09/15
 *    desc   : Context 意图处理（扩展非 Context 类的方法，禁止 Context 类实现此接口）
 */
public interface ContextAction {

    /**
     * 获取 Context
     */
    Context getContext();

    /**
     * 获取资源对象（仅供子类调用）
     */
    default Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 根据 id 获取一个文本
     */
    default String getString(@StringRes int id) {
        return getContext().getString(id);
    }

    default String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 根据 id 获取一个 Drawable
     */
    default Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    /**
     * 根据 id 获取一个颜色
     */
    @ColorInt
    default int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    /**
     * 启动一个 Activity（简化版）
     */
    default void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    /**
     * 启动一个 Activity
     */
    default void startActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            // 如果当前的上下文不是 Activity，调用 startActivity 必须加入新任务栈的标记
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }

    /**
     * 获取系统服务
     */
    default <S> S getSystemService(@NonNull Class<S> serviceClass) {
        return ContextCompat.getSystemService(getContext(), serviceClass);
    }
}