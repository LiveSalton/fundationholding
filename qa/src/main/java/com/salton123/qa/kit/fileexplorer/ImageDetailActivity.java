package com.salton123.qa.kit.fileexplorer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.utils.ImageUtil;
import com.zhenai.qa.R;

import java.io.File;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;

public class ImageDetailActivity extends QBaseActivity {
    private ImageView mImageView;
    private File mFile;

    private void readImage(File file) {
        if (file == null) {
            return;
        }
        ImageReadTask task = new ImageReadTask(this);
        task.execute(file);
    }

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_image_detail;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        mImageView = f(R.id.image);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
        readImage(mFile);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageView.setImageBitmap(null);
    }

    private static class ImageReadTask extends AsyncTask<File, Void, Bitmap> {
        private WeakReference<ImageDetailActivity> mReference;

        public ImageReadTask(ImageDetailActivity fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        protected Bitmap doInBackground(File... files) {
            return ImageUtil.decodeSampledBitmapFromFilePath(files[0].getPath(), 1080, 1920);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mReference.get() != null) {
                mReference.get().mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
