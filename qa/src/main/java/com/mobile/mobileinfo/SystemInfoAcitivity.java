package com.mobile.mobileinfo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mobile.mobileinfo.adapter.MobPageAdapter;
import com.qw.soul.permission.bean.Permissions;
import com.salton123.feature.PermissionFeature;
import com.salton123.infopanel.IInterceptView;
import com.salton123.ui.base.BaseActivity;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SystemInfoAcitivity extends BaseActivity implements IInterceptView {

    private ImageView ivBack;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MobPageAdapter mobPageAdapter;

    @Override
    public int getLayout() {
        return R.layout.acitivity_system_info;
    }

    @Override
    public boolean isOpenMultiStatus() {
        return false;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        addFeature(new PermissionFeature() {
            @Override
            public String[] getPermissionArr() {
                return Permissions.build(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.BLUETOOTH).getPermissionsString();
            }

            @Override
            public void onRequestFinish(boolean isGranted) {
                super.onRequestFinish(isGranted);
                if (!isGranted) {
                    Toast.makeText(activity(),
                            "请授予全部权限",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void initViewAndData() {
        mTabLayout = f(R.id.mTabLayout);
        mViewPager = f(R.id.mViewPager);
        ivBack = f(R.id.ivBack);
        mobPageAdapter = new MobPageAdapter(getSupportFragmentManager());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mobPageAdapter);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == ivBack) {
            finish();
        }
    }

    @Override
    public boolean enableTitleBar() {
        return false;
    }
}
