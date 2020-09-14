package com.salton123.qa.kit.timecounter;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @desc: Activity跳转耗时检测首页
 */

public class TimeCounterFragment extends QBaseFragment {

    @Override
    public int getLayout() {
        return R.layout.common_item_recyclerview;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_time_counter));
        RecyclerView mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(settingItemAdapter);
        settingItemAdapter
                .append(new SettingItem(R.string.dk_item_time_counter_switch, TimeCounterManager.get().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_time_goto_list));

        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_item_time_counter_switch) {
                    if (on) {
                        TimeCounterManager.get().start();
                    } else {
                        TimeCounterManager.get().stop();
                    }
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_time_goto_list) {
                    showContent(TimeCounterListFragment.class);
                }
            }
        });
    }


}