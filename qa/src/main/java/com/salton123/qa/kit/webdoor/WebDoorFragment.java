package com.salton123.qa.kit.webdoor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.salton123.qa.ui.CaptureActivity;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.webdoor.WebDoorHistoryAdapter;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.zhenai.qa.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglikun on 2018/10/10.
 */

public class WebDoorFragment extends QBaseFragment {
    private EditText mWebAddressInput;
    private TextView mUrlExplore;
    private RecyclerView mHistoryList;
    private WebDoorHistoryAdapter mWebDoorHistoryAdapter;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_QR_CODE = 3;
    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA};

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_web_door;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_web_door));
        mWebAddressInput = f(R.id.web_address_input);
        mWebAddressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkInput()) {
                    mUrlExplore.setEnabled(true);
                } else {
                    mUrlExplore.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUrlExplore = f(R.id.url_explore);
        f(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebDoorManager.getInstance().clearHistory(getContext());
                mWebDoorHistoryAdapter.clear();
            }
        });
        f(R.id.qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrCode();
            }
        });
        mUrlExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(mWebAddressInput.getText().toString());
            }
        });
        mHistoryList = f(R.id.history_list);
        mHistoryList.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mHistoryList.setLayoutManager(layoutManager);
        List<String> historyItems = WebDoorManager.getInstance().getHistory(getContext());

        mWebDoorHistoryAdapter = new WebDoorHistoryAdapter(getContext());
        mWebDoorHistoryAdapter.setData(historyItems);
        mWebDoorHistoryAdapter.setOnItemClickListener(new WebDoorHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                doSearch(data);
            }
        });
        mHistoryList.setAdapter(mWebDoorHistoryAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mHistoryList.addItemDecoration(decoration);
    }

    private void doSearch(String url) {
        WebDoorManager.getInstance().saveHistory(getContext(), url);
        WebDoorManager.getInstance().getWebDoorCallback().overrideUrlLoading(getContext(), url);
        mWebDoorHistoryAdapter.setData(WebDoorManager.getInstance().getHistory(getContext()));
    }

    private boolean checkInput() {
        return !TextUtils.isEmpty(mWebAddressInput.getText());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_QR_CODE) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            if (!TextUtils.isEmpty(result)) {
                doSearch(result);
            }
        }
    }

    private void qrCode() {
        if (!ownPermissionCheck()) {
            requestPermissions(PERMISSIONS_CAMERA, REQUEST_CAMERA);
            return;
        }
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_QR_CODE);
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


}
