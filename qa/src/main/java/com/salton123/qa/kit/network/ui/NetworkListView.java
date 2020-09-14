package com.salton123.qa.kit.network.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.salton123.qa.kit.network.NetworkManager;
import com.salton123.qa.kit.network.OnNetworkInfoUpdateListener;
import com.salton123.qa.kit.network.bean.NetworkRecord;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @desc: 抓包列表
 */

public class NetworkListView extends LinearLayout implements OnNetworkInfoUpdateListener {
    private static final String TAG = "NetworkListFragment";
    public static final String KEY_RECORD = "record";
    private RecyclerView mNetworkList;
    private NetworkListAdapter mNetworkListAdapter;

    public NetworkListView(Context context) {
        super(context);
        inflate(context, R.layout.dk_fragment_network_monitor_list, this);
        initView();
        initData();
    }

    public NetworkListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.dk_fragment_network_monitor_list, this);
        initView();
        initData();
    }

    private void initView() {
        mNetworkList = findViewById(R.id.network_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNetworkList.setLayoutManager(layoutManager);
        mNetworkListAdapter = new NetworkListAdapter(getContext());
        mNetworkList.setAdapter(mNetworkListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        decoration.showHeaderDivider(true);
        mNetworkList.addItemDecoration(decoration);
        mNetworkListAdapter.setOnItemClickListener(new NetworkListAdapter.OnItemClickListener() {
            @Override
            public void onClick(NetworkRecord record) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_RECORD, record);
                ((QBaseActivity) getContext()).showContent(NetworkDetailFragment.class, bundle);
            }
        });
        ((EditText) findViewById(R.id.network_list_filter)).addTextChangedListener(mTextWatcher);
    }

    private void initData() {
        synchronized (this) {
            List<NetworkRecord> records = new ArrayList<>(NetworkManager.get().getRecords());
            Collections.reverse(records);
            mNetworkListAdapter.setData(records);
        }
    }

    public void registerNetworkListener() {
        NetworkManager.get().setOnNetworkInfoUpdateListener(this);
    }

    public void unRegisterNetworkListener() {
        NetworkManager.get().setOnNetworkInfoUpdateListener(null);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mNetworkListAdapter.getFilter().filter(s);
        }

    };

    @Override
    public void onNetworkInfoUpdate(NetworkRecord record, boolean add) {
        synchronized (this) {
            if (add) {
                mNetworkListAdapter.append(record, 0);
            }
            mNetworkListAdapter.notifyDataSetChanged();
        }
    }
}