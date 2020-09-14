package com.salton123.qa.kit.colorpick;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.qa.ui.base.BaseFloatPage;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.TouchProxy;
import com.salton123.utils.ColorUtil;
import com.salton123.utils.UIUtils;
import com.zhenai.qa.R;

import androidx.annotation.ColorInt;

/**
 * Created by wanglikun on 2018/12/3.
 */

public class ColorPickerInfoFloatPage extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    private WindowManager mWindowManager;
    private ImageView mColor;
    private TextView mColorHex;
    private ImageView mClose;
    private TouchProxy mTouchProxy = new TouchProxy(this);

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_color_picker_info, null);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.x = 0;
        params.y = UIUtils.getHeightPixels(getContext()) - UIUtils.dp2px(getContext(), 85);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        initView();
    }

    private void initView() {
        getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTouchProxy.onTouchEvent(v, event);
            }
        });
        mColor = findViewById(R.id.color);
        mColorHex = findViewById(R.id.color_hex);
        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatPageManager.getInstance().removeAll(ColorPickerFloatPage.class);
                FloatPageManager.getInstance().removeAll(ColorPickerInfoFloatPage.class);
            }
        });
    }

    public void showInfo(@ColorInt int colorInt, int x, int y) {
        mColor.setImageDrawable(new ColorDrawable(colorInt));
        mColorHex.setText(String.format(ColorPickConstants.TEXT_FOCUS_INFO, ColorUtil.parseColorInt(colorInt),
                x + ColorPickConstants.PIX_INTERVAL, y + ColorPickConstants.PIX_INTERVAL));
    }

    @Override
    public void onMove(int x, int y, int dx, int dy) {
        getLayoutParams().x += dx;
        getLayoutParams().y += dy;
        mWindowManager.updateViewLayout(getRootView(), getLayoutParams());
    }

    @Override
    public void onUp(int x, int y) {

    }

    @Override
    public void onDown(int x, int y) {

    }
}