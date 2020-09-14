package com.salton123.qa.ui.realtime.datasource;

import com.salton123.qa.kit.common.PerformanceDataManager;
import com.salton123.qa.ui.realtime.widget.LineChart;

public class CpuDataSource implements IDataSource {
    @Override
    public LineChart.LineData createData() {
        float rate = PerformanceDataManager.getInstance().getLastCpuRate();
        return LineChart.LineData.obtain(rate, Math.round(rate) + "%");
    }
}
