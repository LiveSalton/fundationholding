package com.salton123.qa.ui.realtime;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.salton123.qa.kit.common.PerformanceDataManager;
import com.salton123.qa.kit.network.NetworkManager;
import com.salton123.qa.ui.base.BaseFloatPage;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.salton123.qa.ui.base.TouchProxy;
import com.salton123.qa.ui.realtime.datasource.DataSourceFactory;
import com.salton123.qa.ui.realtime.datasource.IDataSource;
import com.salton123.qa.ui.realtime.widget.LineChart;
import com.salton123.utils.UIUtils;
import com.zhenai.qa.R;

/**
 * @desc: cpu、内存、流量监控的实时图，需要自定义IDataSource接口作为数据源。
 */
public class RealTimeChartPage extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    public static final String TAG = "RealTimeChartPage";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TITLE = "title";
    public static final String KEY_INTERVAL = "interval";
    public static final int DEFAULT_REFRESH_INTERVAL = 1000;

    private LineChart mLineChart;
    private TouchProxy mTouchProxy = new TouchProxy(this);
    private WindowManager mWindowManager;
    private ImageView mClose;
    private String title;
    private int type;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_layout_real_time, view, false);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        init();
    }

    public void init() {
        title = getBundle().getString(KEY_TITLE);
        type = getBundle().getInt(KEY_TYPE);
        int interval = getBundle().getInt(KEY_INTERVAL, DEFAULT_REFRESH_INTERVAL);

        IDataSource dataSource = DataSourceFactory.createDataSource(type);
        mLineChart = findViewById(R.id.lineChart);
        mLineChart.setTitle(title);
        mLineChart.setInterval(interval);
        mLineChart.setDataSource(dataSource);
        mLineChart.startMove();
        getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTouchProxy.onTouchEvent(v, event);
            }
        });
        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeChartPage();
                FloatPageManager.getInstance().remove(RealTimeChartPage.TAG);
                notifyPageClose();
            }
        });
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.x = UIUtils.dp2px(getContext(), 30);
        params.y = UIUtils.dp2px(getContext(), 30);
    }

    /**
     * 打开实时折线图浮窗
     *
     * @param title    左上角标题
     * @param type     类型，需要在{@link DataSourceFactory} 实现数据源接口
     * @param interval 刷新间隔,单位毫秒
     */
    public static void openChartPage(String title, int type, int interval) {
        if (updateChartPage(title, type, interval)) {
            return;
        }
        closeChartPage();
        PageIntent pageIntent = new PageIntent(RealTimeChartPage.class);
        pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        pageIntent.tag = RealTimeChartPage.TAG;
        Bundle bundle = new Bundle();
        bundle.putString(RealTimeChartPage.KEY_TITLE, title);
        bundle.putInt(RealTimeChartPage.KEY_TYPE, type);
        bundle.putInt(RealTimeChartPage.KEY_INTERVAL, interval);
        pageIntent.bundle = bundle;
        FloatPageManager.getInstance().add(pageIntent);
    }

    /**
     * 当前page已经打开的情况下，只做更新，防止闪烁
     *
     * @param title
     * @param type
     * @param interval
     * @return
     */
    private static boolean updateChartPage(String title, int type, int interval) {
        RealTimeChartPage chartPage =
                (RealTimeChartPage) FloatPageManager.getInstance().getFloatPage(RealTimeChartPage.TAG);
        if (chartPage == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(RealTimeChartPage.KEY_TITLE, title);
        bundle.putInt(RealTimeChartPage.KEY_TYPE, type);
        bundle.putInt(RealTimeChartPage.KEY_INTERVAL, interval);
        chartPage.setBundle(bundle);
        chartPage.init();
        return true;
    }

    public static void closeChartPage() {
        FloatPageManager.getInstance().remove(RealTimeChartPage.TAG);
    }

    @Override
    public void onEnterForeground() {
        super.onEnterForeground();
        mLineChart.startMove();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onEnterBackground() {
        super.onEnterBackground();
        mLineChart.stopMove();
        getRootView().setVisibility(View.GONE);
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

    /**
     * 关闭浮窗时，不同入口需要执行不同逻辑
     */
    private void notifyPageClose() {
        switch (type) {
            case DataSourceFactory.TYPE_NETWORK:
                NetworkManager.get().stopMonitor();
                break;
            case DataSourceFactory.TYPE_CPU:
                PerformanceDataManager.getInstance().stopMonitorCPUInfo();
                break;
            case DataSourceFactory.TYPE_MEMORY:
                PerformanceDataManager.getInstance().stopMonitorMemoryInfo();
                break;
            case DataSourceFactory.TYPE_FRAME:
                PerformanceDataManager.getInstance().stopMonitorFrameInfo();
                break;
            default:
                break;
        }
    }
}
