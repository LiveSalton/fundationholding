package com.salton123.qa.kit.network.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.salton123.feature.EventBusFeature;
import com.salton123.qa.event.UpdateFloatPageEvent;
import com.salton123.qa.kit.network.NetworkManager;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.realtime.RealTimeChartPage;
import com.salton123.qa.ui.realtime.datasource.DataSourceFactory;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NetWorkMonitorFragment extends QBaseFragment {
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_net_monitor;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        addFeature(new EventBusFeature(this));
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_net_monitor));
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(mSettingItemAdapter);
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_net_monitor_detection_switch, NetworkManager.isActive()));
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_net_monitor_detection_switch) {
                    if (on) {
                        String title = getString(R.string.dk_kit_network_monitor);
                        int type = DataSourceFactory.TYPE_NETWORK;
                        NetworkManager.get().startMonitor();
                        RealTimeChartPage.openChartPage(title, type, RealTimeChartPage.DEFAULT_REFRESH_INTERVAL);
                    } else {
                        NetworkManager.get().stopMonitor();
                        RealTimeChartPage.closeChartPage();
                    }
                }
            }
        });
        f(R.id.btn_net_summary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent(NetWorkMainPagerFragment.class);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFloatPageClose(UpdateFloatPageEvent event) {
        if (!TextUtils.equals(RealTimeChartPage.TAG, event.getTag())) {
            return;
        }
        if (mSettingList == null || mSettingList.isComputingLayout()) {
            return;
        }
        if (mSettingItemAdapter == null) {
            return;
        }
        if (!mSettingItemAdapter.getData().get(0).isChecked) {
            return;
        }
        mSettingItemAdapter.getData().get(0).isChecked = false;
        mSettingItemAdapter.notifyItemChanged(0);
    }
}
