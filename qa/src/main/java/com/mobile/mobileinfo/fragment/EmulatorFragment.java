package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.emulator.EmulatorHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class EmulatorFragment extends MobileFragement {

    public static EmulatorFragment newInstance() {
        Bundle args = new Bundle();
        EmulatorFragment fragment = new EmulatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(EmulatorHelper.mobCheckEmulator());
    }


}
