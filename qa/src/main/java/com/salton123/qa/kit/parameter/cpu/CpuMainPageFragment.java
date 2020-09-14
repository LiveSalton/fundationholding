package com.salton123.qa.kit.parameter.cpu;

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

import androidx.annotation.Nullable;

public class CpuMainPageFragment extends AbsParameterFragment {
    private static final String TAG = "CpuMainPageFragment";

    @Override
    protected Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_cpu_detection_switch, false));
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
//                PerformanceInfoConfig.setCPUOpen(config(), on);
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
                    bundle.putInt(BundleKey.PERFORMANCE_TYPE, PerformanceFragment.CPU);
                    showContent(PerformanceFragment.class, bundle);
                }
            }
        };
    }

    private void startMonitor() {
        PerformanceDataManager.getInstance().startMonitorCPUInfo();
        openChartPage(R.string.dk_frameinfo_cpu, DataSourceFactory.TYPE_CPU);
    }

    private void stopMonitor() {
        PerformanceDataManager.getInstance().stopMonitorCPUInfo();
        closeChartPage();
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        PerformanceDataManager.getInstance().init(getContext().getApplicationContext());
        setTitleText(getString(R.string.dk_frameinfo_cpu));
    }


}
