package com.salton123.soulove.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.salton123.soulove.common.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Author: Thomas.
 * <br/>Date: 2019/8/23 13:47
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:
 */
public class TRefreshHeader extends LinearLayout implements RefreshHeader {
    private static final String TAG = "TRefreshHeader";
    SimpleColorFilter filter;
    private LottieAnimationView mAnimationView;

    public TRefreshHeader(Context context) {
        super(context);
        filter = new SimpleColorFilter(context.getResources().getColor(R.color.colorHint));
        initView(context);
    }

    public TRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LottieAnimationView);
        filter = new SimpleColorFilter(
                typedArray.getColor(R.styleable.LottieAnimationView_lottie_colorFilter,
                        context.getResources().getColor(R.color.colorAccent)));
        typedArray.recycle();
        initView(context);
    }

    public TRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_widget_refersheader, this);
        mAnimationView = view.findViewById(R.id.animation_view);

        KeyPath keyPath = new KeyPath("**");
        LottieValueCallback<ColorFilter> callback = new LottieValueCallback<ColorFilter>(filter);
        mAnimationView.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback);

    }


    @NonNull
    @Override
    public View getView() {

        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        mAnimationView.setProgress(percent);
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mAnimationView.playAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mAnimationView.cancelAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
