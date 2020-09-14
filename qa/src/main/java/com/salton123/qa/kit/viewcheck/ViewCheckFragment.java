package com.salton123.qa.kit.viewcheck;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.config.ViewCheckConfig;
import com.salton123.qa.constant.PageTag;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCheckFragment extends QBaseFragment {
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

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
        setTitleText(getString(R.string.dk_kit_view_check));
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_view_check) {
                    if (on) {
                        PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
                        intent.tag = PageTag.PAGE_VIEW_CHECK;
                        FloatPageManager.getInstance().add(intent);
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckInfoFloatPage.class));
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckDrawFloatPage.class));
                        getActivity().finish();
                    } else {
                        FloatPageManager.getInstance().removeAll(ViewCheckDrawFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckInfoFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckFloatPage.class);
                    }
                    ViewCheckConfig.setViewCheckOpen(getContext(), on);
                }
            }
        });
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_kit_view_check, ViewCheckConfig.isViewCheckOpen(getContext())));
        mSettingList.setAdapter(mSettingItemAdapter);
    }


}