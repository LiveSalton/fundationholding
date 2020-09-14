package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.simcard.SimCardHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class SimCardFragment extends MobileFragement {

    public static SimCardFragment newInstance() {
        Bundle args = new Bundle();
        SimCardFragment fragment = new SimCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(SimCardHelper.mobileSimInfo());
    }


}
