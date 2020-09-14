package com.salton123.qa.kit.layoutborder;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.config.LayoutBorderConfig;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglikun on 2018/10/9.
 */

public class LayoutBorderSettingFragment extends QBaseFragment {
    private static final String TAG = "LayoutBorderSettingFragment";
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

    private void initView() {
        setTitleText(getString(R.string.dk_kit_layout_border));
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_kit_layout_border, LayoutBorderConfig.isLayoutBorderOpen()));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_layout_level, LayoutBorderConfig.isLayoutLevelOpen()));
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_layout_border) {
                    if (on) {
                        LayoutBorderManager.getInstance().start();
                    } else {
                        LayoutBorderManager.getInstance().stop();
                    }
                    LayoutBorderConfig.setLayoutBorderOpen(on);
                } else if (data.desc == R.string.dk_layout_level) {
                    if (on) {
                        PageIntent intent = new PageIntent(LayoutLevelFloatPage.class);
                        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                        FloatPageManager.getInstance().add(intent);
                    } else {
                        FloatPageManager.getInstance().removeAll(LayoutLevelFloatPage.class);
                    }
                    LayoutBorderConfig.setLayoutLevelOpen(on);
                }
            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
    }

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


}