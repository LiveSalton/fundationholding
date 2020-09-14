//package com.salton123.qa.kit.easyfloat;
//
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.RequiresApi;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//
//import com.salton123.qa.constant.PageTag;
//import com.salton123.qa.kit.colorpick.ColorPickConstants;
//import com.salton123.qa.kit.colorpick.ColorPickerInfoFloatPage;
//import com.salton123.qa.kit.colorpick.ImageCapture;
//import com.salton123.qa.ui.base.FloatPageManager;
//import com.salton123.qa.ui.colorpicker.ColorPickerView;
//import com.salton123.utils.ImageUtil;
//import com.salton123.utils.UIUtils;
//import com.zhenai.qa.R;
//
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
///**
// * User: newSalton@outlook.com
// * Date: 2020/1/20 15:07
// * ModifyTime: 15:07
// * Description:
// */
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class ColorPickFloatView extends BaseEasyFloatWiew {
//    private ImageCapture mImageCapture;
//    private ColorPickerView mPickerView;
//    private ColorPickerInfoFloatPage mInfoFloatPage;
//    private int width;
//    private int height;
//    private int statuBarHeight;
//
//    @Override
//    public int getLayout() {
//        return R.layout.dk_float_color_picker;
//    }
//
//    @Override
//    public void initViewAndData(@Nullable View view) {
//        mInfoFloatPage = (ColorPickerInfoFloatPage) FloatPageManager.getInstance().getFloatPage(PageTag.PAGE_COLOR_PICKER_INFO);
//        mImageCapture = new ImageCapture();
//        mImageCapture.init(getContext(), new Bundle());
//        mPickerView = view.findViewById(R.id.picker_view);
//        ViewGroup.LayoutParams params = mPickerView.getLayoutParams();
//        //大小必须是2的倍数
//        params.width = ColorPickConstants.PICK_VIEW_SIZE;
//        params.height = ColorPickConstants.PICK_VIEW_SIZE;
//        mPickerView.setLayoutParams(params);
//        width = UIUtils.getWidthPixels(getContext());
//        height = UIUtils.getHeightPixels(getContext());
//        statuBarHeight = UIUtils.getStatusBarHeight(getContext());
//        captureInfo(500);
//    }
//
//    @Override
//    public void dismiss() {
//        super.dismiss();
//        mImageCapture.destroy();
//    }
//
//    private void showInfo() {
//        int x = getLayoutParams().x;
//        int y = getLayoutParams().y;
//        int pickAreaSize = ColorPickConstants.PICK_AREA_SIZE;
//        int startX = x + ColorPickConstants.PICK_VIEW_SIZE / 2 - pickAreaSize / 2;
//        int startY = y + ColorPickConstants.PICK_VIEW_SIZE / 2 - pickAreaSize / 2 + UIUtils.getStatusBarHeight(getContext());
//        Bitmap bitmap = mImageCapture.getPartBitmap(startX, startY, pickAreaSize, pickAreaSize);
//        if (bitmap == null) {
//            return;
//        }
//        int xCenter = bitmap.getWidth() / 2;
//        int yCenter = bitmap.getHeight() / 2;
//        int colorInt = ImageUtil.getPixel(bitmap, xCenter, yCenter);
//        mPickerView.setBitmap(bitmap, colorInt, startX, startY);
//        mInfoFloatPage.showInfo(colorInt, startX, startY);
//    }
//
//    private void captureInfo(int delay) {
//        getRootView().setVisibility(View.GONE);
//        getRootView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mImageCapture.capture();
//                getRootView().setVisibility(View.VISIBLE);
//                showInfo();
//            }
//        }, delay);
//    }
//
//    @Override
//    public void drag(@NotNull View view, @NotNull MotionEvent motionEvent) {
//        super.drag(view, motionEvent);
//        WindowManager.LayoutParams params = getLayoutParams();
//        params.x += dx;
//        params.y += dy;
//        checkBound(params);
//        mWindowManager.updateViewLayout(getRootView(), getLayoutParams());
//        showInfo();
//    }
//    private void checkBound(WindowManager.LayoutParams layoutParams) {
//        if (layoutParams.x < -mPickerView.getWidth() / 2) {
//            layoutParams.x = -mPickerView.getWidth() / 2;
//        }
//        if (layoutParams.x > width - mPickerView.getWidth() / 2 - ColorPickConstants.PIX_INTERVAL) {
//            layoutParams.x = width - mPickerView.getWidth() / 2 - ColorPickConstants.PIX_INTERVAL;
//        }
//        if (layoutParams.y < -mPickerView.getHeight() / 2 - statuBarHeight) {
//            layoutParams.y = -mPickerView.getHeight() / 2 - statuBarHeight;
//        }
//        if (layoutParams.y > height - mPickerView.getHeight() / 2 - ColorPickConstants.PIX_INTERVAL) {
//            layoutParams.y = height - mPickerView.getHeight() / 2 - ColorPickConstants.PIX_INTERVAL;
//        }
//    }
//
//
//    @Override
//    public void dragEnd(@NotNull View view) {
//        super.dragEnd(view);
//        captureInfo(100);
//    }
//}
