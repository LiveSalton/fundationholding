package com.salton123.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;

public class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    /**
     * 查找View
     *
     * @param itemView 控件父类
     * @param resId    控件id
     * @return
     */
    public static <T extends View> T f(View itemView, @IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }

    public static void showHideView(View view, boolean hide) {
        if (view == null) {
            return;
        }
        view.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    public static View getViewByLayout(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, null, false);
    }
}
