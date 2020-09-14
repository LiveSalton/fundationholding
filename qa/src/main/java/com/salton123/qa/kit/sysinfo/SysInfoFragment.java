package com.salton123.qa.kit.sysinfo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;

import com.mobile.mobilehardware.root.RootHelper;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.salton123.utils.DeviceUtils;
import com.salton123.utils.ExecutorUtil;
import com.salton123.utils.PermissionUtil;
import com.salton123.utils.UIUtils;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 手机app信息
 * Created by zhangweida on 2018/6/25.
 */

public class SysInfoFragment extends QBaseFragment {
    private RecyclerView mInfoList;
    private SysInfoItemAdapter mInfoItemAdapter;
    LinearLayoutManager layoutManager;

    public static SysInfoFragment newInstance() {
        Bundle args = new Bundle();
        SysInfoFragment fragment = new SysInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_sys_info;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        super.initVariable(savedInstanceState);
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        mInfoList = f(R.id.info_list);
        layoutManager = new LinearLayoutManager(getContext());
        mInfoList.setLayoutManager(layoutManager);
        mInfoItemAdapter = new SysInfoItemAdapter(getContext());
        mInfoList.setAdapter(mInfoItemAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mInfoList.addItemDecoration(decoration);
        List<SysInfoItem> sysInfoItems = new ArrayList<>();
        addAppData(sysInfoItems);
        addDeviceData(sysInfoItems);
        if (getContext().getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
            addPermissionData(sysInfoItems);
        } else {
            addPermissionDataUnreliable();
        }
        mInfoItemAdapter.setData(sysInfoItems);
    }

    @Override
    public boolean enableTitleBar() {
        return false;
    }

    @Override
    public void initListener() {
        super.initListener();
        setTitleText(getString(R.string.dk_kit_sysinfo));
    }

    private void addAppData(List<SysInfoItem> sysInfoItems) {
        PackageInfo pi = DeviceUtils.getPackageInfo(getContext());
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_app_info)));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_name), pi.packageName));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_version_name), pi.versionName));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_version_code),
                String.valueOf(pi.versionCode)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_min_sdk),
                    String.valueOf(getContext().getApplicationInfo().minSdkVersion)));
        }
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_target_sdk),
                String.valueOf(getContext().getApplicationInfo().targetSdkVersion)));
    }

    private void addDeviceData(List<SysInfoItem> sysInfoItems) {
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_device_info)));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_brand_and_model),
                Build.MANUFACTURER + " " + Build.MODEL));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_android_version),
                Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_ext_storage_free),
                DeviceUtils.getSDCardSpace(getContext())));
        sysInfoItems
                .add(new SysInfoItem(getString(R.string.dk_sysinfo_rom_free), DeviceUtils.getRomSpace(getContext())));
        sysInfoItems.add(new SysInfoItem("ROOT", String.valueOf(RootHelper.mobileRoot())));
        sysInfoItems.add(new SysInfoItem("DENSITY", String.valueOf(UIUtils.getDensity(getContext()))));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_display_size),
                UIUtils.getWidthPixels(getContext()) + "x" + UIUtils.getRealHeightPixels(getContext())));
    }

    /**
     * 不可靠的检测权限方式
     */
    private void addPermissionDataUnreliable() {
        ExecutorUtil.execute(new Runnable() {
            @Override
            public void run() {
                final List<SysInfoItem> list = new ArrayList<>();
                list.add(new TitleItem(getString(R.string.dk_sysinfo_permission_info_unreliable)));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_location),
                        PermissionUtil.checkLocationUnreliable(getContext()) ? "YES" : "NO"));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_sdcard),
                        PermissionUtil.checkStorageUnreliable() ? "YES" : "NO"));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_camera),
                        PermissionUtil.checkCameraUnreliable() ? "YES" : "NO"));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_record),
                        PermissionUtil.checkRecordUnreliable() ? "YES" : "NO"));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_read_phone),
                        PermissionUtil.checkReadPhoneUnreliable(getContext()) ? "YES" : "NO"));
                list.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_contact),
                        PermissionUtil.checkReadContactUnreliable(getContext()) ? "YES" : "NO"));
                getView().post(new Runnable() {
                    @Override
                    public void run() {
                        if (SysInfoFragment.this.isDetached()) {
                            return;
                        }
                        mInfoItemAdapter.append(list);
                    }
                });
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void addPermissionData(List<SysInfoItem> sysInfoItems) {
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_permission_info)));
        String[] p1 = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_location), checkPermission(p1)));
        String[] p2 = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_sdcard), checkPermission(p2)));
        String[] p3 = {
                Manifest.permission.CAMERA
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_camera), checkPermission(p3)));
        String[] p4 = {
                Manifest.permission.RECORD_AUDIO
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_record), checkPermission(p4)));
        String[] p5 = {
                Manifest.permission.READ_PHONE_STATE
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_read_phone), checkPermission(p5)));
        String[] p6 = {
                Manifest.permission.READ_CONTACTS
        };
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_contact), checkPermission(p6)));
    }

    private String checkPermission(String... perms) {
        Permission permissions[] = SoulPermission.getInstance().checkPermissions(perms);
        if (permissions.length == perms.length) {
            return "YES";
        }
        return "NO";
    }

}
