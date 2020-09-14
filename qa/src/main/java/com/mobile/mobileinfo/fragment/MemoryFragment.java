package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.memory.MemoryHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import java.util.List;

public class MemoryFragment extends MobileFragement {

    public static MemoryFragment newInstance() {
        Bundle args = new Bundle();
        MemoryFragment fragment = new MemoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        return getListParam(MemoryHelper.getMemoryInfo());
    }


}
