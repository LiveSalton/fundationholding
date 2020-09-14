package com.salton123.qa.kit.alignruler;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.config.AlignRulerConfig;
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

public class AlignRulerSettingFragment extends QBaseFragment {
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
        setTitleText(getString(R.string.dk_kit_align_ruler));
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter
                .append(new SettingItem(R.string.dk_kit_align_ruler, AlignRulerConfig.isAlignRulerOpen(getContext())));
        mSettingList.setAdapter(mSettingItemAdapter);
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_align_ruler) {
                    if (on) {
                        PageIntent pageIntent = new PageIntent(AlignRulerMarkerFloatPage.class);
                        pageIntent.tag = PageTag.PAGE_ALIGN_RULER_MARKER;
                        FloatPageManager.getInstance().add(pageIntent);
                        FloatPageManager.getInstance().add(new PageIntent(AlignRulerLineFloatPage.class));
                    } else {
                        FloatPageManager.getInstance().removeAll(AlignRulerMarkerFloatPage.class);
                        FloatPageManager.getInstance().removeAll(AlignRulerLineFloatPage.class);
                    }
                    AlignRulerConfig.setAlignRulerOpen(getContext(), on);
                }
            }
        });
    }


}
