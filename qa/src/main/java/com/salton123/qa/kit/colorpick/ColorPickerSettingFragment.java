package com.salton123.qa.kit.colorpick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;

import com.salton123.qa.constant.PageTag;
import com.salton123.qa.constant.RequestCode;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.salton123.qa.ui.base.QBaseFragment;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;

/**
 * Created by wanglikun on 2018/9/15.
 */

public class ColorPickerSettingFragment extends QBaseFragment {

    private boolean requestCaptureScreen() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        MediaProjectionManager mediaProjectionManager =
                (MediaProjectionManager) getContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager == null) {
            return false;
        }
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), RequestCode.CAPTURE_SCREEN);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.CAPTURE_SCREEN && resultCode == Activity.RESULT_OK) {
            showColorPicker(data);
            finish();
        } else {
            shortToast("start color pick fail");
            finish();
        }
    }

    private void showColorPicker(Intent data) {
        PageIntent pageIntent = new PageIntent(ColorPickerInfoFloatPage.class);
        pageIntent.tag = PageTag.PAGE_COLOR_PICKER_INFO;
        pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        pageIntent = new PageIntent(ColorPickerFloatPage.class);
        pageIntent.bundle = data.getExtras();
        pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);
    }

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_color_picker_setting;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        requestCaptureScreen();
    }
}
