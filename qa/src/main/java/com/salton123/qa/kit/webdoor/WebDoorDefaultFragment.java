package com.salton123.qa.kit.webdoor;

import android.os.Bundle;
import android.webkit.WebView;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.ui.base.QBaseFragment;
import com.zhenai.qa.R;

import androidx.annotation.Nullable;

/**
 * Created by wanglikun on 2019/4/4
 */
public class WebDoorDefaultFragment extends QBaseFragment {

    private String mUrl;

    private WebView mWebView;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_web_door_default;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        mUrl = getArguments() == null ? null : getArguments().getString(BundleKey.KEY_URL);
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        mWebView = f(R.id.web_view);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }
}