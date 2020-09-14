package com.salton123.qa.kit.gpsmock;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salton123.qa.config.GpsMockConfig;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.setting.SettingItem;
import com.salton123.qa.ui.setting.SettingItemAdapter;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.salton123.qa.ui.widget.webview.MyWebView;
import com.salton123.qa.ui.widget.webview.MyWebViewClient;
import com.salton123.utils.WebUtil;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglikun on 2018/9/20.
 */

public class GpsMockFragment extends QBaseFragment
        implements SettingItemAdapter.OnSettingItemSwitchListener, MyWebViewClient.InvokeListener {
    private static final String TAG = "GpsMockFragment";

    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;
    private EditText mLongitude;
    private EditText mLatitude;
    private TextView mMockLocationBtn;
    private MyWebView mWebView;

    private void initWebView() {
        mWebView = f(R.id.web_view);
        WebUtil.webViewLoadLocalHtml(mWebView, "html/map.html");
        mWebView.addInvokeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.removeInvokeListener(this);
    }

    private void initMockLocationArea() {
        mLongitude = f(R.id.longitude);
        mLatitude = f(R.id.latitude);
        mLatitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkInput()) {
                    mMockLocationBtn.setEnabled(true);
                } else {
                    mMockLocationBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLongitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkInput()) {
                    mMockLocationBtn.setEnabled(true);
                } else {
                    mMockLocationBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mMockLocationBtn = f(R.id.mock_location);
        mMockLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                }
                GpsMockManager.getInstance().mockLocation(Double.valueOf(mLatitude.getText().toString()),
                        Double.valueOf(mLongitude.getText().toString()));
                Toast.makeText(getContext(),
                        getString(R.string.dk_gps_location_change_toast, mLatitude.getText(), mLongitude.getText()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mLongitude.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(mLatitude.getText().toString())) {
            return false;
        }
        double longitude = Double.valueOf(mLongitude.getText().toString());
        double latitude = Double.valueOf(mLatitude.getText().toString());
        if (longitude > 180 || longitude < -180) {
            return false;
        }
        if (latitude > 90 || latitude < -90) {
            return false;
        }
        return true;
    }

    private void intiSettingList() {
        mSettingList = f(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSettingList.setLayoutManager(layoutManager);
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(R.string.dk_gpsmock_open,
                GpsMockConfig.isGPSMockOpen(getContext())));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.setData(settingItems);
        mSettingItemAdapter.setOnSettingItemSwitchListener(this);
        mSettingList.setAdapter(mSettingItemAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mSettingList.addItemDecoration(decoration);
    }

    @Override
    public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
        if (data.desc == R.string.dk_gpsmock_open) {
            GpsMockConfig.setGPSMockOpen(getContext(), on);
            if (on) {
                GpsMockManager.getInstance().startMock();
            } else {
                GpsMockManager.getInstance().stopMock();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_gps_mock;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_gps_mock));
        intiSettingList();
        initMockLocationArea();
        initWebView();
    }

    @Override
    public void onNativeInvoke(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        String lastPath = uri.getLastPathSegment();
        if (!"sendLocation".equals(lastPath)) {
            return;
        }
        String lat = uri.getQueryParameter("lat");
        String lnt = uri.getQueryParameter("lng");
        if (TextUtils.isEmpty(lat) && TextUtils.isEmpty(lnt)) {
            return;
        }
        mLatitude.setText(lat);
        mLongitude.setText(lnt);
    }
}