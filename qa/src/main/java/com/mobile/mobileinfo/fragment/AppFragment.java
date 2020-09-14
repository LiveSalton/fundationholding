package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.app.PackageHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class AppFragment extends MobileFragement {

    public static AppFragment newInstance() {
        Bundle args = new Bundle();
        AppFragment fragment = new AppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(PackageHelper.getPackageInfo());
    }


}
