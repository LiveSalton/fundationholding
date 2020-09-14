package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.screen.ScreenHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class ScreenFragment extends MobileFragement {

    public static ScreenFragment newInstance() {
        Bundle args = new Bundle();
        ScreenFragment fragment = new ScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(ScreenHelper.mobGetMobScreen(getActivity().getWindow()));
    }


}
