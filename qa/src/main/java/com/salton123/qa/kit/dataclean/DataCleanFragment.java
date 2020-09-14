package com.salton123.qa.kit.dataclean;

import android.os.Bundle;
import android.view.View;

import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.dialog.DialogInfo;
import com.salton123.qa.ui.dialog.SimpleDialogListener;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.salton123.utils.DataCleanUtil;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglikun on 2018/11/17.
 */

public class DataCleanFragment extends QBaseFragment {
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
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.dk_kit_data_clean));
        mSettingList = f(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSettingList.setLayoutManager(layoutManager);
        List<SettingItem> settingItems = new ArrayList<>();
        SettingItem settingItem = new SettingItem(R.string.dk_kit_data_clean, R.drawable.dk_more_icon);
        settingItem.rightDesc = DataCleanUtil.getApplicationDataSizeStr(getContext());
        settingItems.add(settingItem);
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.setData(settingItems);
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, final SettingItem data) {
                if (data.desc == R.string.dk_kit_data_clean) {
                    DialogInfo dialogInfo = new DialogInfo();
                    dialogInfo.title = getString(R.string.dk_hint);
                    dialogInfo.desc = getString(R.string.dk_app_data_clean);
                    dialogInfo.listener = new SimpleDialogListener() {
                        @Override
                        public boolean onPositive() {
                            DataCleanUtil.cleanApplicationData(getContext());
                            data.rightDesc = DataCleanUtil.getApplicationDataSizeStr(getContext());
                            mSettingItemAdapter.notifyDataSetChanged();
                            return true;
                        }

                        @Override
                        public boolean onNegative() {
                            return true;
                        }
                    };
                    showDialog(dialogInfo);
                }
            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mSettingList.addItemDecoration(decoration);
    }


}