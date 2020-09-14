package com.salton123.qa.kit.detech;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.salton123.log.XLog;

import de.robv.android.xposed.XC_MethodHook;

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/16 14:51
 * ModifyTime: 14:51
 * Description:
 */
public class BigImageDetechService {
    private static String TAG = "BigImageDetechService";

    public static void hookSetImageBitmap() {
        DexposedManager.getIntance().hookMethod(ImageView.class,
                "setImageBitmap",
                new DexposedManager.HookMethodCallback<ImageView>() {
                    @Override
                    public void hookMethodBefore(ImageView imageView, XC_MethodHook.MethodHookParam param) {

                    }

                    @Override
                    public void hookMothodAfter(final ImageView imageView, XC_MethodHook.MethodHookParam param) {
                        if (imageView.getDrawable() instanceof BitmapDrawable) {
                            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                            final Bitmap bitmap = drawable.getBitmap();
                            if (bitmap != null) {
                                int bitmapWidth = bitmap.getWidth();
                                int bitmapHeight = bitmap.getHeight();
                                int viewWidth = imageView.getWidth();
                                int viewHeight = imageView.getHeight();
                                if (viewHeight > 0 && viewWidth > 0) {
                                    if (bitmapWidth >= viewWidth << 2 && bitmapHeight >= viewHeight << 2) {
                                        //不合理
                                        StringBuffer msg = new StringBuffer();
                                        msg.append("图片大小不合理：")
                                                .append("位置" + imageView.getContext())
                                                .append(",bitmapWidth=").append(bitmapWidth)
                                                .append(",bitmapHeight=").append(bitmapHeight)
                                                .append(",viewWidth=").append(viewWidth)
                                                .append(",viewHeight=").append(viewHeight);
                                        XLog.e(TAG, msg.toString());
                                    }
                                } else {
                                    imageView.getViewTreeObserver()
                                            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                                @Override
                                                public boolean onPreDraw() {
                                                    int bitmapWidth = bitmap.getWidth();
                                                    int bitmapHeight = bitmap.getHeight();
                                                    int viewWidth = imageView.getWidth();
                                                    int viewHeight = imageView.getHeight();
                                                    if (bitmapWidth >= viewWidth << 2 &&
                                                            bitmapHeight >= viewHeight << 2) {
                                                        StringBuffer msg = new StringBuffer();
                                                        msg.append("图片大小不合理：")
                                                                .append("位置" + imageView.getContext())
                                                                .append(",bitmapWidth=").append(bitmapWidth)
                                                                .append(",bitmapHeight=").append(bitmapHeight)
                                                                .append(",viewWidth=").append(viewWidth)
                                                                .append(",viewHeight=").append(viewHeight);
                                                        XLog.e(TAG, msg.toString());
                                                    }
                                                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                                                    return true;
                                                }
                                            });
                                }
                            }
                        }
                    }

                    @Override
                    public void hookConstruct(ImageView imageView, XC_MethodHook.MethodHookParam param) {

                    }
                }, Bitmap.class);
    }
}
