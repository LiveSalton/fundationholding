package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.sdcard.SDCardHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class SDcardFragment extends MobileFragement {

    public static SDcardFragment newInstance() {
        Bundle args = new Bundle();
        SDcardFragment fragment = new SDcardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(SDCardHelper.mobGetSdCard());
    }


}
