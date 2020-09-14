package com.mobile.mobileinfo.adapter;

import com.mobile.mobileinfo.fragment.AppFragment;
import com.mobile.mobileinfo.fragment.AppListFragment;
import com.mobile.mobileinfo.fragment.AudioFragment;
import com.mobile.mobileinfo.fragment.BandFragment;
import com.mobile.mobileinfo.fragment.BatteryFragment;
import com.mobile.mobileinfo.fragment.BluetoothFragment;
import com.mobile.mobileinfo.fragment.BuildFragment;
import com.mobile.mobileinfo.fragment.CameraFragment;
import com.mobile.mobileinfo.fragment.CpuFragment;
import com.mobile.mobileinfo.fragment.DebugFragment;
import com.mobile.mobileinfo.fragment.EmulatorFragment;
import com.mobile.mobileinfo.fragment.HookFragment;
import com.mobile.mobileinfo.fragment.HostFragment;
import com.mobile.mobileinfo.fragment.IDFragment;
import com.mobile.mobileinfo.fragment.LocalFragment;
import com.mobile.mobileinfo.fragment.MemoryFragment;
import com.mobile.mobileinfo.fragment.MoreOpenFragment;
import com.mobile.mobileinfo.fragment.NetFragment;
import com.mobile.mobileinfo.fragment.NetWorkFragment;
import com.mobile.mobileinfo.fragment.SDcardFragment;
import com.mobile.mobileinfo.fragment.ScreenFragment;
import com.mobile.mobileinfo.fragment.SettingFragment;
import com.mobile.mobileinfo.fragment.SignalFragment;
import com.mobile.mobileinfo.fragment.SimCardFragment;
import com.mobile.mobileinfo.fragment.UAFragment;
import com.mobile.mobileinfo.fragment.XposedFragment;
import com.salton123.qa.kit.sysinfo.SysInfoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MobPageAdapter extends FragmentStatePagerAdapter {
    private List<Pair<String, Fragment>> mData = new ArrayList<>();

    private void addParam() {
        mData.add(new Pair<>("基础信息", SysInfoFragment.newInstance()));
        mData.add(new Pair<>("包信息", AppFragment.newInstance()));
        mData.add(new Pair<>("Build信息", BuildFragment.newInstance()));
        mData.add(new Pair<>("调试信息", DebugFragment.newInstance()));
        mData.add(new Pair<>("屏幕信息", ScreenFragment.newInstance()));
        mData.add(new Pair<>("CPU信息", CpuFragment.newInstance()));
        mData.add(new Pair<>("Net信息", NetFragment.newInstance()));
        mData.add(new Pair<>("网络信息", NetWorkFragment.newInstance()));
        mData.add(new Pair<>("信号信息", SignalFragment.newInstance()));
        mData.add(new Pair<>("音量信息", AudioFragment.newInstance()));
        mData.add(new Pair<>("版本信息", BandFragment.newInstance()));
        mData.add(new Pair<>("电池信息", BatteryFragment.newInstance()));
        mData.add(new Pair<>("蓝牙信息", BluetoothFragment.newInstance()));
        mData.add(new Pair<>("相机信息", CameraFragment.newInstance()));
        mData.add(new Pair<>("模拟器信息", EmulatorFragment.newInstance()));
        mData.add(new Pair<>("Hook信息", HookFragment.newInstance()));
        mData.add(new Pair<>("Local信息", LocalFragment.newInstance()));
        mData.add(new Pair<>("内存信息", MemoryFragment.newInstance()));
        mData.add(new Pair<>("多开信息", MoreOpenFragment.newInstance()));
//        mData.add(new Pair<>("Root信息", RootFragment.newInstance()));
        mData.add(new Pair<>("内存卡信息", SDcardFragment.newInstance()));
        mData.add(new Pair<>("Setting信息", SettingFragment.newInstance()));
        mData.add(new Pair<>("SimCard信息", SimCardFragment.newInstance()));
        mData.add(new Pair<>("ID信息", IDFragment.newInstance()));
        mData.add(new Pair<>("UA信息", UAFragment.newInstance()));
        mData.add(new Pair<>("Host信息", HostFragment.newInstance()));
        mData.add(new Pair<>("Xposed信息", XposedFragment.newInstance()));
        mData.add(new Pair<>("应用列表", AppListFragment.newInstance()));
    }

    public MobPageAdapter(FragmentManager fm) {
        super(fm);
        addParam();
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position).second;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).first;
    }
}
