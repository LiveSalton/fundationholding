package com.salton123.qa.ui.realtime.datasource;

import com.salton123.qa.kit.network.NetworkManager;
import com.salton123.qa.kit.network.utils.ByteUtil;
import com.salton123.qa.ui.realtime.widget.LineChart;

/**
 * @desc: 抓包数据源
 */
public class NetworkDataSource implements IDataSource {
    private long latestTotalLength = -1;

    @Override
    public LineChart.LineData createData() {
        long diff = 0;
        long totalSize = NetworkManager.get().getTotalSize();
        if (latestTotalLength >= 0) {
            diff = totalSize - latestTotalLength;
            if (diff < 0) {
                diff = 0;
            }
        }
        latestTotalLength = totalSize;
        if (diff == 0) {
            return LineChart.LineData.obtain((float) Math.ceil(diff / 1024f), null);
        } else {
            return LineChart.LineData.obtain((float) Math.ceil(diff / 1024f), ByteUtil.getPrintSize(diff));
        }
    }
}
