package com.salton123.qa.kit.blockmonitor;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.kit.blockmonitor.core.BlockMonitorManager;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @desc: 卡顿检测首页
 */

public class BlockMonitorFragment extends QBaseFragment {
    private static final String TAG = "BlockMonitorIndexFragment";
    public static final String KEY_JUMP_TO_LIST = "KEY_JUMP_TO_LIST";

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
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.dk_kit_block_monitor));
        RecyclerView mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(settingItemAdapter);
        settingItemAdapter
                .append(new SettingItem(R.string.dk_item_block_switch, BlockMonitorManager.getInstance().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_goto_list));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_mock));

        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_item_block_switch) {
                    if (on) {
                        BlockMonitorManager.getInstance().start(getContext());
                    } else {
                        BlockMonitorManager.getInstance().stop();
                    }
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_block_goto_list) {
                    showContent(BlockListFragment.class);
                } else if (data.desc == R.string.dk_item_block_mock) {
                    mockBlock();
                }
            }
        });

        if (getArguments() != null) {
            boolean jump = getArguments().getBoolean(KEY_JUMP_TO_LIST, false);
            if (jump) {
                showContent(BlockListFragment.class);
            }
        }
    }

    private void mockBlock() {
        try {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        } catch (Exception e) {

        }
    }


}