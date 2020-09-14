package com.salton123.qa.kit.animationpreview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.salton123.qa.ui.CaptureActivity;
import com.salton123.qa.ui.base.QBaseFragment;
import com.zhenai.qa.R;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

/**
 * User: newSalton@outlook.com
 * Date: 2019-12-16 22:04
 * ModifyTime: 22:04
 * Description:
 */
public class AnimationPreviewFragment extends QBaseFragment implements View.OnClickListener {
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_QR_CODE = 3;
    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA};
    private TextView tvSVGA, tvLottie, ivChangeBackgound;
    private String currentUrl;
    private FrameLayout flAnimation;

    @Override
    public int getLayout() {
        return R.layout.fragment_animation_preview;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        tvSVGA = f(R.id.tvSVGA);
        tvLottie = f(R.id.tvLottie);
        ivChangeBackgound = f(R.id.ivChangeBackgound);
        flAnimation = f(R.id.flAnimation);
        tvSVGA.setOnClickListener(this);
        tvLottie.setOnClickListener(this);
        ivChangeBackgound.setOnClickListener(this);
        qrCode();
    }

    private void qrCode() {
        if (!ownPermissionCheck()) {
            requestPermissions(PERMISSIONS_CAMERA, REQUEST_CAMERA);
            return;
        }
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_QR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_QR_CODE) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            currentUrl = result;
        }
    }

    private boolean ownPermissionCheck() {
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    longToast(getString(R.string.dk_error_tips_permissions_less));
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvSVGA) {
            flAnimation.removeAllViews();
            SVGAImageView svgaImageView = new SVGAImageView(getContext());
            SVGAParser parser = new SVGAParser(getContext());
            try {
                parser.decodeFromURL(new URL(currentUrl), new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                        svgaImageView.setVideoItem(svgaVideoEntity);
                        svgaImageView.setLoops(0);
                        svgaImageView.startAnimation();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), "无法识别资源", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            flAnimation.addView(svgaImageView);
        } else if (id == R.id.ivChangeBackgound) {

        } else if (id == R.id.tvLottie) {
            LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
            // lottieAnimationView.setIm
        }
    }
}
