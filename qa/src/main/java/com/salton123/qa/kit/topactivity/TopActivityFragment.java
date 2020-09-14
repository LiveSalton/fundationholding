package com.salton123.qa.kit.topactivity;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.config.TopActivityConfig;
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
 * 项目名:    Android
 * 包名       com.salton123.qa.kit.topactivity
 * 文件名:    TopActivityFragment
 * 创建时间:  2019-04-29 on 12:16
 * 描述:
 *
 * @author 阿钟
 */

public class TopActivityFragment extends QBaseFragment {
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
        setTitleText(getString(R.string.dk_kit_top_activity));
        RecyclerView topActivityList = f(R.id.recyclerView);
        topActivityList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter topActivityAdapter = new SettingItemAdapter(getContext());
        topActivityAdapter.append(new SettingItem(R.string.dk_kit_top_activity,
                TopActivityConfig.isTopActivityOpen(getContext())));
        topActivityAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_top_activity) {
                    if (on) {
                        PageIntent intent = new PageIntent(TopActivityFloatPage.class);
                        FloatPageManager.getInstance().add(intent);
                        getActivity().finish();
                    } else {
                        FloatPageManager.getInstance().removeAll(TopActivityFloatPage.class);
                    }
                    TopActivityConfig.setTopActivityOpen(getContext(), on);
                }
            }
        });
        topActivityList.setAdapter(topActivityAdapter);
    }


}
