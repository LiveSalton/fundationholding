package com.salton123.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Collection;

public class BitmapUtil {

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static boolean isValid(final Bitmap bmp) {
        return bmp != null && !bmp.isRecycled();
    }

    public static boolean release(Bitmap bmp) {
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            return true;
        }
        return false;
    }

    /**
     * @param fileName
     * @return
     */
    public static Bitmap loadImageFromAssets(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public static Bitmap byteToBitmap(final byte[] cameraBytes, int maxW,
                                      int maxH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = null;
        options.inScaled = false;

        int inSampleSize = 1;

        int[] size = getBitmapSize(cameraBytes);

        if (size != null) {
            inSampleSize = Math.max(size[0] / maxW, size[1] / maxH);
        }

        if (inSampleSize > 1) {
            options.inSampleSize = inSampleSize;
        }


        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        bitmap = BitmapFactory.decodeByteArray(cameraBytes, 0,
                cameraBytes.length, options);

        // 内存较低的机子，resizeBitmap的时候容易内存溢出
        if (!Build.MODEL.equals("MT15i")) {
            if (bitmap != null && maxW > 0 && maxH > 0) {
                Bitmap bmp = resizeBitmap(bitmap, maxW, maxH);
                if (bmp != bitmap) {
                    release(bitmap);
                }
                bitmap = bmp;
            }
        }

        return bitmap;
    }

    /**
     * 从图片路径加载Bitmap,进行一次inSampleSize的缩放
     *
     * @param path
     * @param max
     * @return
     */
    public static Bitmap loadFromPath(String path, int max) {
        return loadFromPath(path, max, max);
    }

    /**
     * 从图片路径加载Bitmap,并有精确的裁剪成所需的宽高
     *
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap loadFromPath(String path, int destWidth, int destHeight) {
        return loadFromPath(path, destWidth, destHeight, null);
    }

    /**
     * 从图片路径加载Bitmap,并有精确的裁剪成所需的宽高
     *
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap loadFromPath(String path, int destWidth,
                                      int destHeight, Config config) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = config != null ? config
                : Config.ARGB_8888;
        int inSampleSize = 1;
        int[] size = getBitmapSize(path);
        if (size != null) {
            inSampleSize = Math.max(size[0] / destWidth, size[1] / destHeight);
        }
        if (inSampleSize > 1) {
            options.inSampleSize = inSampleSize;
        }
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap != null) {
            Bitmap newBitmap = resizeBitmap(bitmap, destWidth, destHeight);
            if (newBitmap != null && bitmap != newBitmap) {
                bitmap.recycle();
                bitmap = newBitmap;
            }
        }
        return bitmap;
    }


    /**
     * 获取bitmap大小
     *
     * @param fileName
     * @return
     */
    public static int[] getBitmapSize(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);

        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 获取bitmap大小
     *
     * @param data
     * @return
     */
    public static int[] getBitmapSize(byte[] data) {
        if (data == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 从图片路径加载Bitmap,并读取Exif进行旋转和有精确的裁剪成所需的宽高
     *
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap loadWithExifFromPath(String path, int destWidth, int destHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Config.ARGB_8888;
        int inSampleSize = 1;
        int[] size = getBitmapSize(path);
        if (size != null) {
            inSampleSize = Math.max(size[0] / destWidth, size[1] / destHeight);
        }
        if (inSampleSize > 1) {
            options.inSampleSize = inSampleSize;
        }
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int orientation = readPictureOrientation(path);
        if (orientation != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        if (bitmap != null) {
            Bitmap newBitmap = resizeBitmap(bitmap, destWidth, destHeight);
            if (newBitmap != null && bitmap != newBitmap) {
                bitmap.recycle();
                bitmap = newBitmap;
            }
        }

        return bitmap;
    }

    public static Bitmap resizeBitmap(Bitmap input, int destWidth, int destHeight) {
        if (input == null || input.isRecycled()) {
            return null;
        }

        if (destWidth <= 0 || destHeight <= 0 || (destWidth == input.getWidth() && destHeight == input.getHeight())) {
            return input;
        }

        int srcWidth = input.getWidth();
        int srcHeight = input.getHeight();
        boolean needsResize = false;
        float p;
        if (srcWidth > destWidth || srcHeight > destHeight) {
            needsResize = true;
            if (srcWidth > srcHeight && srcWidth > destWidth) {
                p = (float) destWidth / (float) srcWidth;
                destHeight = (int) (srcHeight * p);
            } else {
                p = (float) destHeight / (float) srcHeight;
                destWidth = (int) (srcWidth * p);
            }
        } else {
            destWidth = srcWidth;
            destHeight = srcHeight;
        }
        if (needsResize) {
            Bitmap output = Bitmap.createScaledBitmap(input, destWidth,
                    destHeight, true);
            return output;
        } else {
            return input;
        }
    }

    /**
     * 将一张图片适应窗口大小 缩放或放大  得到的图片大于或等于w ,h
     */
    public static Bitmap FittingWindow(Bitmap src, int w, int h, boolean isNeedRelease) {
        Bitmap bmpDst = null;
        try {
            float scale;
            int srcW, srcH, dstW, dstH;
            srcW = src.getWidth();
            srcH = src.getHeight();
            if (srcW * h > srcH * w) {// 适应宽度
                dstW = w;
                dstH = srcH * w / srcW;
                scale = (float) dstW / srcW;
            } else { // 适应高度
                dstH = h;
                dstW = srcW * h / srcH;
                scale = (float) dstH / srcH;
            }
            scale = ((int) (scale * 1000)) / 1000.0f;
            Matrix matrix = new Matrix();
            matrix.reset(); // 重置矩阵
            // matrix.postScale(scale, scale);
            matrix.preScale(scale, scale);

            if (srcW == dstW && srcH == dstH) {// 相同尺寸，返回的是原图的引用
                Config config = src.getConfig();
                if (config == null) {
                    config = Config.ARGB_8888;
                }
                bmpDst = src.copy(src.getConfig(), true);
            } else {
                bmpDst = createScaledBitmap(src, dstW, dstH, src.getConfig());
            }
            if (isNeedRelease) {
                release(src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpDst;
    }

    /**
     * 创建一张新的图片
     */
    public static Bitmap createScaledBitmap(Bitmap putBitmap, int dstW, int dstH, Config dstConfig) {
        Bitmap bitmap = null;
        try {
            Paint p = new Paint();
            p.setDither(false);
            p.setAntiAlias(true);
            p.setFilterBitmap(true);
            if (dstW <= 0) {
                dstW = 1;
            }
            if (dstH <= 0) {
                dstH = 1;
            }
            if (dstConfig == null) {
                dstConfig = Config.ARGB_8888;
            }
            bitmap = Bitmap.createBitmap(dstW, dstH, dstConfig);
            Canvas c2 = new Canvas(bitmap);
            c2.drawBitmap(putBitmap, new Rect(0, 0, putBitmap.getWidth(),
                    putBitmap.getHeight()), new Rect(0, 0, dstW, dstH), p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从bitmap转成byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] BitmapToByte(Bitmap bitmap, int quality) {

        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            return baos.toByteArray();

        }
        return null;
    }

    /**
     * 处理图片
     *
     * @param bitmap
     * @param sx      默认为1
     * @param sy      默认为1
     * @param degrees 0
     * @return
     */
    public static Bitmap processBitmap(Bitmap bitmap, float sx, float sy,
                                       int degrees, boolean recycle) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        matrix.postRotate(degrees);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        if (newBitmap != bitmap && recycle) {
            bitmap.recycle();
        }
        return newBitmap;
    }

    // 获取旋转角度,返回值1~6,1 代表正常情况
    // 1 2 3 4 5 6 7 8
    //
    // 888888 888888 88 88 8888888888 88 88 8888888888
    // 88 88 88 88 88 88 88 88 88 88 88 88
    // 8888 8888 8888 8888 88 8888888888 8888888888 88
    // 88 88 88 88
    // 88 88 888888 888888
    public static String getJpgOritation(String name) {
        String ori = "";
        ori = getAtt.getAttri(name);
        return ori;
    }

    private static class getAtt {
        public static String getAttri(String name) {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(name);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        }
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度 获取从相册中选中图片的角度
     */
    public static int readPictureOrientation(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int orientation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (attributeInt) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    orientation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    orientation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    orientation = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orientation;
    }

    /**
     * 剪切到固定给出的尺寸，带有圆角,gyl,2012.7.25
     *
     * @param srcBmp       原bitmap
     * @param desW         目标宽像素
     * @param desH         目标高像素
     * @param cornerPixels 圆角半径像素
     * @param needRelease  是否需要release原bitmap
     * @return 新的bitmap
     */
    public static Bitmap cutFixedSizeWithRoundCorners(Bitmap srcBmp, int desW,
                                                      int desH, int cornerPixels, boolean needRelease) {
        Bitmap bmp = FittingWindow(srcBmp, desW, desH, needRelease);
        if (bmp != null) {
            bmp = FittingSquare(bmp, true);
            if (bmp != null && cornerPixels > 0) {
                bmp = toRoundCorner(bmp, cornerPixels, true);
            }
        }
        return bmp;
    }

    /**
     * @param bmp           裁成正方形
     * @param isNeadRecycle
     * @return
     */
    public static Bitmap FittingSquare(Bitmap bmp, boolean isNeadRecycle) {
        Bitmap bmpDst = null;
        try {
            int w = bmp.getWidth();
            int h = bmp.getHeight();
            if (w == h) {
                return bmp;
            }
            int dx, dy, dw, dh;
            if (w > h) {
                dw = dh = h;
                dx = (w - h) / 2;
                dy = 0;
            } else {
                dw = dh = w;
                dx = 0;
                dy = (h - w) / 2;
            }
            Bitmap bmpD = Bitmap.createBitmap(bmp, dx, dy, dw, dh);
            if (bmpD != bmp && isNeadRecycle) {
                bmp.recycle();
            }

            return bmpD;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpDst;
    }

    /**
     * 添加圆角,gyl,2012.7.25
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels,
                                       boolean needRelease) {
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            if (needRelease) {
                release(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * 保存图片
     *
     * @param jpeg
     * @param picPath
     */
    public static void saveImage(byte[] jpeg, String picPath) {
        // Save the image.

        File parent = new File(picPath).getParentFile();

        if (!parent.exists()) {
            parent.mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picPath);
            out.write(jpeg);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }

    }

    // 保存文件
    public static boolean saveImage(String path, Bitmap bmp,
                                    Bitmap.CompressFormat format, int quality) {
        if (bmp == null || bmp.isRecycled()) {
            return false;
        }
        File myCaptureFile = new File(path);

        File parent = myCaptureFile.getParentFile();

        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            bmp.compress(format, quality, bos);
            try {
                bos.flush();
                bos.close();
                // writeEixf(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Bitmap getThumbBmp(String filePath, int maxLength) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(filePath), null,
                    options);
            float width_tmp = options.outWidth;
            float height_tmp = options.outHeight;
            float bigger = Math.max(height_tmp, width_tmp);
            float scale = bigger / maxLength;
            options.inSampleSize = (int) (scale);
            options.inJustDecodeBounds = false;
            options.inDither = false;
//			options.inPreferredConfig = Config.ARGB_8888;
            return BitmapFactory.decodeStream(new FileInputStream(filePath),
                    null, options);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 缩放图片
     */
    public static Bitmap scale(Bitmap src, float scale, boolean isNeedRelease) {
        Bitmap bmpDst = null;
        try {
            /* 产生Resize后的Bitmap对象 */
            if (scale == 1) {// 相同尺寸，返回的是原图的引用
                bmpDst = src.copy(src.getConfig(), true);
            } else {
                int dstW = (int) (src.getWidth() * scale);
                int dstH = (int) (src.getHeight() * scale);
                if (dstW < 1) {
                    dstW = 1;
                }
                if (dstH < 1) {
                    dstH = 1;
                }
                bmpDst = createScaledBitmap(src, dstW, dstH, src.getConfig());
            }
            if (isNeedRelease) {
                src.recycle();
                src = null;
            }
        } catch (OutOfMemoryError e) {
            Log.e("BitmapUtil.java", "-- scale --", e);
        }
        return bmpDst;
    }

    /**
     * 镜像
     *
     * @param kind 1:左右镜像 , 2:上下镜像 , 3:左右、上下组合镜像
     */
    public static Bitmap mirror(Bitmap src, int kind, boolean isNeedRelease) {
        Bitmap mirrorPic = null;
        try {
            Matrix mirrorMatrix = new Matrix();
            switch (kind) {
                case 1:// 左右镜像
                    mirrorMatrix.preScale(-1, 1);
                    break;
                case 2:// 上下镜像
                    mirrorMatrix.preScale(1, -1);
                    break;
                case 3:// 左右、上下 貌似这样无法用，生成的图片为空
                    mirrorMatrix.preScale(-1, 1);
                    mirrorMatrix.postScale(1, -1);
                default:
                    return null;
            }
            // 这里不缩放，不会影响镜像后的config
            mirrorPic = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), mirrorMatrix, true); // 镜像
            if (isNeedRelease) {
                release(src);
            }
        } catch (Exception e) {
            Log.e("BitmapUtil.java", "-- mirror --", e);
        } catch (OutOfMemoryError e) {
            Log.e("BitmapUtil.java", "-- mirror --", e);
        }
        return mirrorPic;
    }

    /**
     * 旋转 angle=0~360
     *
     * @param src
     * @param degress
     * @return
     */
    public static Bitmap rotate(Bitmap src, float degress, boolean isNeedRelease) {
        Bitmap bmpDst = null;
        try {/* 产生Resize后的Bitmap对象 */
            if (degress == 0 || degress == 360) {
                Config config = src.getConfig();
                if (config == null) {
                    config = Config.RGB_565;
                }
                bmpDst = src.copy(config, true);
            } else {// 相同尺寸，返回的是原图的引用
                Matrix matrix = new Matrix();
                matrix.preRotate(degress);
                bmpDst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            }
            if (isNeedRelease) {
                release(src);
            }
        } catch (Exception e) {
            Log.e("BitmapUtil.java", "-- rotate --", e);
        } catch (OutOfMemoryError e) {
            Log.e("BitmapUtil.java", "-- rotate --", e);
        }
        return bmpDst;
    }

    /**
     * 合并叠加图片用的实体对象
     */
    public static class MergeMutilBitmapBean implements Serializable {
        public Bitmap srcBitmap;//经过旋转缩放等处理后的bmp或者原始bmp等
        public float leftX;//坐标
        public float topY;//坐标
    }

    /**
     * 合并叠加图片
     */
    public static Bitmap MergeMutilBitmap(Bitmap bgBMP, Collection<MergeMutilBitmapBean> mergeMutilBitmapBeans) {
        if (bgBMP == null || mergeMutilBitmapBeans == null) {
            return null;
        }
        if (mergeMutilBitmapBeans.isEmpty()) {
            return bgBMP;
        }
        try {
            Bitmap newBitmap = Bitmap.createBitmap(bgBMP).copy(Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));//抗锯齿
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAlpha(0);
            canvas.drawRect(0, 0, bgBMP.getWidth(), bgBMP.getHeight(), paint);
            for (MergeMutilBitmapBean bean : mergeMutilBitmapBeans) {
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setDither(true);
                canvas.drawBitmap(bean.srcBitmap, bean.leftX, bean.topY, paint);
            }
            canvas.save();
            // 存储新合成的图片
            canvas.restore();
            return newBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees, float scale) {
//        if (degrees != 0 && bitmap != null) {
//            Matrix matrix = new Matrix();
//            matrix.setRotate(degrees, (float) bitmap.getWidth() / 2,
//                    (float) bitmap.getHeight() / 2);
//            matrix.postScale(scale, scale);
//            Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), matrix, true);
//            if (bitmap != b2) {
//                bitmap.recycle();
//                bitmap = b2;
//            }
//            return bitmap;
//        }
//        return bitmap;
//    }

    /**
     * 校验图片路径校验是否为有效的图片
     *
     * @param filepath
     * @return
     */
    public static boolean isValidImageFile(String filepath) {
        if (filepath == null || filepath.equalsIgnoreCase("") || filepath.lastIndexOf("/") < 0 || filepath.length() < 4) {
            return false;
        }

        if (!filepath.equals("")) {
            int index = filepath.lastIndexOf(".");
            if (index == -1) {
                return false;
            }

            String extName = filepath.substring(index + 1).toLowerCase();

            if (extName.equals("bmp") || extName.equals("jpg") || extName.equals("jpeg") || extName.equals("peg")
                    || extName.equals("png") || extName.equals("gif") || extName.equals("wbmp") || extName.equals("webp")) {

                File lFile = new File(filepath);
                return lFile.exists();

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Bitmap转成byte流
     *
     * @param bitmap
     * @return
     */
    public static byte[] decodeFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        byte[] buffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 4];
        bitmap.copyPixelsToBuffer(ByteBuffer.wrap(buffer));
        return buffer;
    }


    public static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                               int paddingLeft, int paddingTop) {
        if (src == null || watermark == null) {
            return null;
        }
        try {
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个新的和SRC长度宽度一样的位图
            Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            //将该图片作为画布
            Canvas canvas = new Canvas(newb);
            //在画布 0，0坐标上开始绘制原始图片
            canvas.drawBitmap(src, 0, 0, null);
            //在画布上绘制水印图片
            canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
            // 保存
            canvas.save();
            // 存储
            canvas.restore();
            return newb;
        } catch (Exception ignore) {
            ignore.printStackTrace();
            return null;
        }
    }

}
