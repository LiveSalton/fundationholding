package com.mobile.mobileinfo.fragment;

import android.os.Bundle;

import com.mobile.mobilehardware.camera.CameraHelper;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.fragment.base.MobileFragement;

import org.json.JSONException;

import java.util.List;

public class CameraFragment extends MobileFragement {

    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public List<Param> addListView() {
        try {
            return getListParam(CameraHelper.getCameraInfo().getJSONArray("cameraInfo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
