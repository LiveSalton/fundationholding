package com.salton123.qa.kit.fileexplorer;

import android.os.Bundle;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.qa.ui.widget.videoview.MyVideoView;
import com.zhenai.qa.R;

import java.io.File;

import androidx.annotation.Nullable;

public class VideoPlayActivity extends QBaseActivity {
    private MyVideoView mVideoView;
    private File mFile;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_video_play;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
        mVideoView = f(R.id.video_view);
        mVideoView.register(activity());
        mVideoView.setVideoPath(mFile.getPath());
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoView.onResume();
    }
}