package com.hjq.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.cardview.widget.CardView;

import com.salton123.soulove.sdk.R;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/7/23 21:12
 * ModifyTime: 21:12
 * Description:
 */
public class RationCardView extends CardView {

    /**
     * 宽高比
     */
    private float mSizeRatio;

    public RationCardView(Context context) {
        this(context, null);
    }

    public RationCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RationCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout);
        mSizeRatio = array.getFloat(R.styleable.RatioFrameLayout_sizeRatio, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mSizeRatio != 0) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前宽度是写死的，但是高度不写死
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSpecSize / mSizeRatio), MeasureSpec.EXACTLY);
            } else if (heightSpecMode == MeasureSpec.EXACTLY && widthSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前高度是写死的，但是宽度不写死
                widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (heightSpecSize * mSizeRatio), MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateRatio(float ratio) {
        this.mSizeRatio = ratio;
        invalidate();
    }
}
