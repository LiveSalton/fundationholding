package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.useragent.UserAgentHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class UAFragment extends MobileFragement {

    public static UAFragment newInstance() {
        Bundle args = new Bundle();
        UAFragment fragment = new UAFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam("userAgent", UserAgentHelper.getDefaultUserAgent());
    }


}
