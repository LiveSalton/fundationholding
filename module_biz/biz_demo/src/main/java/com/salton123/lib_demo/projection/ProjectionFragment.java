package com.salton123.lib_demo.projection;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/30 16:56
 * ModifyTime: 16:56
 * Description:
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ProjectionFragment extends Fragment {
    public static final int REQUEST_CODE = 0x100;
    private OnAcitivityCallback mCallback;

    public void setOnAcitivityCallback(OnAcitivityCallback callback) {
        this.mCallback = callback;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            this.mCallback.onResult(resultCode, data);
        }
    }

    public void requestRecordScreen(MediaProjectionManager manager, OnAcitivityCallback callback) {
        Intent intent = manager.createScreenCaptureIntent();
        startActivityForResult(intent, ProjectionFragment.REQUEST_CODE);
        setOnAcitivityCallback(callback);
    }
}
