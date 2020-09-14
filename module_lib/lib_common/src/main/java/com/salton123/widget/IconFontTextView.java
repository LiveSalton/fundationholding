package com.salton123.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.salton123.soulove.common.R;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/17 9:42 PM
 * ModifyTime: 9:42 PM
 * Description:
 */
public class IconFontTextView extends AppCompatTextView {

    private Paint paint;
    private int strokeWidth;
    private int strokeColor;
    private int fillColor;

    public IconFontTextView(Context context) {
        this(context, null);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(getIconfont(context));
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconFontTextView, defStyleAttr, 0);
        strokeColor = a.getColor(R.styleable.IconFontTextView_if_strokeColor, 0);
        strokeWidth = a.getDimensionPixelSize(R.styleable.IconFontTextView_if_strokeWidth, 0);
        fillColor = a.getColor(R.styleable.IconFontTextView_if_fillColor, 0);
        a.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setAlpha(1);
        } else {
            setAlpha(0.2F);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int center = getWidth() / 2;
        if (strokeColor != 0) {

            if (strokeWidth != 0) {
                this.paint.setStyle(Paint.Style.STROKE);
                this.paint.setStrokeWidth(strokeWidth);
            }

            this.paint.setColor(strokeColor);

            if (strokeWidth != -1) {
                canvas.drawCircle(center, center, getWidth() / 2 - strokeWidth / 2, this.paint);
            } else {
                canvas.drawCircle(center, center, getWidth() / 2, this.paint);
            }
        }

        if (fillColor != 0) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(fillColor);
            canvas.drawCircle(center, center, getWidth() / 2, this.paint);
        }

        super.onDraw(canvas);
    }

    public static Typeface mIconfont;

    public static Typeface getIconfont(Context context) {
        if (mIconfont == null) {
            mIconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        }
        return mIconfont;
    }
}
