package com.salton123.qa.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.constant.FragmentIndex;
import com.salton123.qa.kit.alignruler.AlignRulerSettingFragment;
import com.salton123.qa.kit.animationpreview.AnimationPreviewFragment;
import com.salton123.qa.kit.blockmonitor.BlockMonitorFragment;
import com.salton123.qa.kit.colorpick.ColorPickerSettingFragment;
import com.salton123.qa.kit.custom.MonitorDataUploadFragment;
import com.salton123.qa.kit.dataclean.DataCleanFragment;
import com.salton123.qa.kit.gpsmock.GpsMockFragment;
import com.salton123.qa.kit.logInfo.LogInfoFragment;
import com.salton123.qa.kit.network.ui.NetWorkMonitorFragment;
import com.salton123.qa.kit.parameter.cpu.CpuMainPageFragment;
import com.salton123.qa.kit.parameter.frameInfo.FrameInfoFragment;
import com.salton123.qa.kit.parameter.ram.RamMainPageFragment;
import com.salton123.qa.kit.sysinfo.SysInfoFragment;
import com.salton123.qa.kit.timecounter.TimeCounterFragment;
import com.salton123.qa.kit.topactivity.TopActivityFragment;
import com.salton123.qa.kit.viewcheck.ViewCheckFragment;
import com.salton123.qa.kit.weaknetwork.WeakNetworkFragment;
import com.salton123.qa.kit.webdoor.WebDoorDefaultFragment;
import com.salton123.qa.kit.webdoor.WebDoorFragment;
import com.salton123.qa.ui.base.QBaseActivity;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by wanglikun on 2018/10/26.
 */

public class UniversalActivity extends QBaseActivity {

    @Override
    public int getLayout() {
        return R.layout.salton_fm_container;
    }

    int index;
    Bundle bundle;

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        index = bundle.getInt(BundleKey.FRAGMENT_INDEX);
        if (index == 0) {
            finish();
            return;
        }
    }

    @Override
    public void initViewAndData() {
        Class<? extends Fragment> fragmentClass = null;
        switch (index) {
            case FragmentIndex.FRAGMENT_SYS_INFO:
                fragmentClass = SysInfoFragment.class;
                break;
            case FragmentIndex.FRAGMENT_LOG_INFO_SETTING:
                fragmentClass = LogInfoFragment.class;
                break;
            case FragmentIndex.FRAGMENT_COLOR_PICKER_SETTING:
                fragmentClass = ColorPickerSettingFragment.class;
                break;
            case FragmentIndex.FRAGMENT_FRAME_INFO:
                fragmentClass = FrameInfoFragment.class;
                break;
            case FragmentIndex.FRAGMENT_GPS_MOCK:
                fragmentClass = GpsMockFragment.class;
                break;
            case FragmentIndex.FRAGMENT_ALIGN_RULER_SETTING:
                fragmentClass = AlignRulerSettingFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEB_DOOR:
                fragmentClass = WebDoorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_DATA_CLEAN:
                fragmentClass = DataCleanFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEAK_NETWORK:
                fragmentClass = WeakNetworkFragment.class;
                break;
            case FragmentIndex.FRAGMENT_BLOCK_MONITOR:
                fragmentClass = BlockMonitorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_VIEW_CHECK:
                fragmentClass = ViewCheckFragment.class;
                break;
            case FragmentIndex.FRAGMENT_NETWORK_MONITOR:
                fragmentClass = NetWorkMonitorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_CPU:
                fragmentClass = CpuMainPageFragment.class;
                break;
            case FragmentIndex.FRAGMENT_RAM:
                fragmentClass = RamMainPageFragment.class;
                break;
            case FragmentIndex.FRAGMENT_TIME_COUNTER:
                fragmentClass = TimeCounterFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEB_DOOR_DEFAULT:
                fragmentClass = WebDoorDefaultFragment.class;
                break;
            case FragmentIndex.FRAGMENT_CUSTOM:
                fragmentClass = MonitorDataUploadFragment.class;
                break;
            case FragmentIndex.FRAGMENT_TOP_ACTIVITY:
                fragmentClass = TopActivityFragment.class;
                break;
            case FragmentIndex.FRAGMENT_ANIMATION_PREVIEW:
                fragmentClass = AnimationPreviewFragment.class;
                break;
            default:
                break;
        }
        if (fragmentClass == null) {
            finish();
            Toast.makeText(this, String.format("fragment index %s not found", index), Toast.LENGTH_SHORT).show();
            return;
        }
        showContent(fragmentClass, bundle);
    }
}
