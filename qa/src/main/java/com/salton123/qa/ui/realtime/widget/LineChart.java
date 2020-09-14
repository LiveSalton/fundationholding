package com.salton123.qa.ui.realtime.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.salton123.qa.ui.realtime.datasource.IDataSource;
import com.zhenai.qa.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;

/**
 * @desc: 实时折线图
 */
public class LineChart extends FrameLayout {
    private TextView mTitle;
    private CardiogramView mLine;

    public LineChart(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LineChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.dk_view_line_chart, this);
        mTitle = findViewById(R.id.tv_title);
        mLine = findViewById(R.id.line_chart_view);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void startMove() {
        mLine.startMove();
    }

    public void stopMove() {
        mLine.stopMove();
    }

    public void setInterval(int interval) {
        mLine.setInterval(interval);
    }

    public void setDataSource(@NonNull IDataSource dataSource) {
        mLine.setDataSource(dataSource);
    }

    public static class LineData {
        public float value;
        public String label;
        private static Pools.SimplePool<LineData> mPool = new Pools.SimplePool<>(50);

        /**
         * @param value 影响折线幅度的值，必须大于minValue小于maxValue
         * @param label item从右边进入时显示的值，为null的时候不显示
         */
        public LineData(float value, String label) {
            this.value = value;
            this.label = label;
        }

        public static LineData obtain(float value, String label) {
            LineData lineData = mPool.acquire();
            if (lineData == null) {
                return new LineData(value, label);
            }
            lineData.value = value;
            lineData.label = label;
            return lineData;
        }

        public void release() {
            mPool.release(this);
        }


    }
}
