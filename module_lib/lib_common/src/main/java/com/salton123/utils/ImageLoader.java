package com.salton123.utils;

import android.widget.ImageView;

import com.salton123.GlideApp;
import com.salton123.soulove.common.BuildConfig;
import com.salton123.log.XLog;
import com.salton123.soulove.common.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019-08-04 12:46
 * ModifyTime: 12:46
 * Description:
 */
public class ImageLoader {

    public static void loadCircleCrop(ImageView imageView, String url) {
        if (BuildConfig.APP_DEVELOP) {
            XLog.d(ImageLoader.class, "[loadCenterInsideRoundedCorners] url:" + url);
        }

        GlideApp.with(imageView)
                .load(url)
                .centerInside()
                .placeholder(R.drawable.salton_load_pic)
                .circleCrop()
                .into(imageView);
    }

    public static void loadCenterCrop(ImageView imageView, String url) {
        if (BuildConfig.APP_DEVELOP) {
            XLog.d(ImageLoader.class, "[loadCenterCrop]" + ",url:" + url);
        }
        GlideApp.with(imageView)
                .load(url)
                .fitCenter()
                .into(imageView);
    }
}
