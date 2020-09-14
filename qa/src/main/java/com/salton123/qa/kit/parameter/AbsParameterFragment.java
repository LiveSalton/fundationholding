package com.salton123.qa.kit.parameter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.salton123.feature.EventBusFeature;
import com.salton123.qa.event.UpdateFloatPageEvent;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.realtime.RealTimeChartPage;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.zhenai.qa.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbsParameterFragment extends QBaseFragment {

    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private static final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE = 2;

    @Override
    public int getLayout() {
        return R.layout.common_item_recyclerview;
    }

    protected abstract Collection<SettingItem> getSettingItems(List<SettingItem> list);

    protected abstract SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener();

    protected abstract SettingItemAdapter.OnSettingItemClickListener getItemClickListener();

    protected void openChartPage(@StringRes int title, int type) {
        RealTimeChartPage.openChartPage(getString(title), type, RealTimeChartPage.DEFAULT_REFRESH_INTERVAL);
    }

    protected void closeChartPage() {
        RealTimeChartPage.closeChartPage();
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        addFeature(new EventBusFeature(this));
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        mSettingList = f(R.id.recyclerView);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(getSettingItems(new ArrayList<SettingItem>()));

        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on && !ownPermissionCheck()) {
                    if (view instanceof CheckBox) {
                        ((CheckBox) view).setChecked(false);
                    }
                    requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    return;
                }
                SettingItemAdapter.OnSettingItemSwitchListener itemSwitchListener = getItemSwitchListener();
                if (itemSwitchListener != null) {
                    itemSwitchListener.onSettingItemSwitch(view, data, on);
                }
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (!ownPermissionCheck()) {
                    requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    return;
                }
                SettingItemAdapter.OnSettingItemClickListener itemClickListener = getItemClickListener();
                if (itemClickListener != null) {
                    itemClickListener.onSettingItemClick(view, data);
                }

            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    longToast(getString(R.string.dk_error_tips_permissions_less));
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean ownPermissionCheck() {
        int permission = ActivityCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
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
