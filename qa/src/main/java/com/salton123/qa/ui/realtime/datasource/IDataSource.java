package com.salton123.qa.ui.realtime.datasource;

import com.salton123.qa.ui.realtime.widget.LineChart;

/**
 * @desc: 折线图绘制的数据源接口
 */
public interface IDataSource {
    /**
     * 返回在折线图上显示的最新数据
     *
     * @return
     */
    LineChart.LineData createData();
}
