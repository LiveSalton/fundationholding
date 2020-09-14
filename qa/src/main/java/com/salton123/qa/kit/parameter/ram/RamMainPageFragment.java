package com.salton123.qa.kit.parameter.ram;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.kit.common.PerformanceDataManager;
import com.salton123.qa.kit.common.PerformanceFragment;
import com.salton123.qa.kit.parameter.AbsParameterFragment;
import com.salton123.qa.ui.realtime.datasource.DataSourceFactory;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import java.util.Collection;
import java.util.List;

public class RamMainPageFragment extends AbsParameterFragment {
    private static final String TAG = "RamMainPageFragment";

    @Override
    protected Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_ram_detection_switch, false));
        list.add(new SettingItem(R.string.dk_item_cache_log, R.drawable.dk_more_icon));
        return list;
    }

    @Override
    protected SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener() {
        return new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on) {
                    startMonitor();
                } else {
                    stopMonitor();
                }
//                PerformanceInfoConfig.setMemoryOpen(config(), on);
            }

        };
    }

    @Override
    protected SettingItemAdapter.OnSettingItemClickListener getItemClickListener() {
        return new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_cache_log) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleKey.PERFORMANCE_TYPE, PerformanceFragment.RAM);
                    showContent(PerformanceFragment.class, bundle);

                }
            }
        };
    }

    protected void startMonitor() {
        PerformanceDataManager.getInstance().startMonitorMemoryInfo();
        openChartPage(R.string.dk_ram_detection_title, DataSourceFactory.TYPE_MEMORY);
    }

    protected void stopMonitor() {
        PerformanceDataManager.getInstance().stopMonitorMemoryInfo();
        closeChartPage();
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        PerformanceDataManager.getInstance().init(getContext().getApplicationContext());
        setTitleText(getString(R.string.dk_ram_detection_title));
    }


}
