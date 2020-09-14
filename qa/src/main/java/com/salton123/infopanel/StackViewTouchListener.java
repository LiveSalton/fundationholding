package com.salton123.infopanel;

import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/16 20:55
 * ModifyTime: 20:55
 * Description:
 */
public class StackViewTouchListener implements View.OnTouchListener {
    private View stackView;
    private float dX, dY = 0f;
    private float downX, downY = 0f;
    private boolean isClickState;
    private int clickLimitValue;

    StackViewTouchListener(View stackView, int clickLimitValue) {
        this.stackView = stackView;
        this.clickLimitValue = clickLimitValue;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float X = event.getRawX();
        float Y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClickState = true;
                downX = X;
                downY = Y;
                dX = stackView.getX() - event.getRawX();
                dY = stackView.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(X - downX) < clickLimitValue && Math.abs(Y - downY) < clickLimitValue && isClickState) {
                    isClickState = true;
                } else {
                    isClickState = false;
                    float desX = event.getRawX() + dX;
                    float desY = event.getRawY() + dY;
                    int maxWidth = getScreenWidth() - stackView.getWidth();
                    int maxHeight = getScreenHeight() - stackView.getHeight();
                    if (desX > maxWidth) {
                        desX = maxWidth;
                    }
                    if (desY > maxHeight) {
                        desY = maxHeight;
                    }

                    if (desX < 0) {
                        desX = 0;
                    }
                    if (desY < 0) {
                        desY = 0;
                    }

                    stackView.setX(desX);
                    stackView.setY(desY);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (X - downX < clickLimitValue && isClickState) {
                    stackView.performClick();
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public static int getScreenWidth() {
        int screenWith = -1;
        try {
            screenWith = Resources.getSystem().getDisplayMetrics().widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenWith;
    }

    public static int getScreenHeight() {
        int screenHeight = -1;
        try {
            screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }
}