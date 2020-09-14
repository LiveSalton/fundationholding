package com.salton123.qa.kit.custom;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.salton123.feature.EventBusFeature;
import com.salton123.qa.config.PerformanceInfoConfig;
import com.salton123.qa.event.UpdateFloatPageEvent;
import com.salton123.qa.kit.common.PerformanceDataManager;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.realtime.RealTimeChartPage;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MonitorDataUploadFragment extends QBaseFragment {
    private static final String TAG = "MonitorDataUploadFragment";
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private TextView mCommitButton;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_monitor_data_upload_page;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        addFeature(new EventBusFeature(this));
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        PerformanceDataManager.getInstance().init(getContext().getApplicationContext());
        initView();
        initCommitButton();
    }

    private void initView() {
        setTitleText(getString(R.string.dk_category_performance));
        mCommitButton = f(R.id.commit);
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_frameinfo_fps, PerformanceInfoConfig.isFPSOpen(getContext())));
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_frameinfo_cpu, PerformanceInfoConfig.isCPUOpen(getContext())));
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_frameinfo_ram, PerformanceInfoConfig.isMemoryOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_net_monitor,
                PerformanceInfoConfig.isTrafficOpen(getContext())));
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_platform_monitor_view_stat_data, R.drawable.dk_more_icon));

        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_frameinfo_fps) {
                    PerformanceInfoConfig.setFPSOpen(getContext(), on);
                } else if (data.desc == R.string.dk_frameinfo_cpu) {
                    PerformanceInfoConfig.setCPUOpen(getContext(), on);
                } else if (data.desc == R.string.dk_frameinfo_ram) {
                    PerformanceInfoConfig.setMemoryOpen(getContext(), on);
                } else if (data.desc == R.string.dk_kit_net_monitor) {
                    PerformanceInfoConfig.setTrafficOpen(getContext(), on);
                }
                setCommitButtonState();
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_platform_monitor_view_stat_data) {
                    showContent(PageDataFragment.class);
                }
            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommitButton.getText().equals(getString(R.string.dk_platform_monitor_data_button_stop))) {
                    mCommitButton.setText(R.string.dk_platform_monitor_data_button);
                    PerformanceDataManager.getInstance().stopUploadMonitorData();
                    FloatPageManager.getInstance().removeAll(RealTimePerformDataFloatPage.class);
                } else {
                    mCommitButton.setText(R.string.dk_platform_monitor_data_button_stop);
                    PerformanceDataManager.getInstance().startUploadMonitorData();
                    PageIntent pageIntent = new PageIntent(RealTimePerformDataFloatPage.class);
                    pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                    FloatPageManager.getInstance().add(pageIntent);
                }
            }
        });
    }

    private boolean checkCommitButtonEnable() {
        if (PerformanceInfoConfig.isCPUOpen(getContext()) ||
                PerformanceInfoConfig.isFPSOpen(getContext()) ||
                PerformanceInfoConfig.isMemoryOpen(getContext()) ||
                PerformanceInfoConfig.isTrafficOpen(getContext())) {
            return true;
        } else {
            return false;
        }
    }

    private void setCommitButtonState() {
        if (checkCommitButtonEnable()) {
            mCommitButton.setEnabled(true);
        } else {
            mCommitButton.setEnabled(false);
        }
    }

    private void initCommitButton() {
        setCommitButtonState();
        if (checkCommitButtonEnable() && PerformanceDataManager.getInstance().isUploading()) {
            mCommitButton.setText(R.string.dk_platform_monitor_data_button_stop);
        } else {
            mCommitButton.setText(R.string.dk_platform_monitor_data_button);
        }
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
