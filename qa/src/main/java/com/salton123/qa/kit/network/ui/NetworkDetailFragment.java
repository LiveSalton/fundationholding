package com.salton123.qa.kit.network.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salton123.qa.kit.network.bean.NetworkRecord;
import com.salton123.qa.ui.base.QBaseFragment;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @desc: 网络抓包详情页，显示request和response的详情
 */

public class NetworkDetailFragment extends QBaseFragment implements View.OnClickListener {
    private static final String TAG = "NetworkDetailFragment";

    private ViewPager mViewPager;
    private View mDiverRequest;
    private View mDiverResponse;
    private TextView mTvRequest;
    private TextView mTvResponse;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_network_monitor_detail;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.dk_kit_network_monitor_detail));
        mViewPager = f(R.id.network_viewpager);
        mDiverRequest = f(R.id.diver_request);
        mDiverResponse = f(R.id.diver_response);
        mTvRequest = f(R.id.tv_pager_request);
        mTvResponse = f(R.id.tv_pager_response);
        mTvRequest.setSelected(true);
        mTvResponse.setSelected(false);
        mTvRequest.setOnClickListener(this);
        mTvResponse.setOnClickListener(this);

        List<NetworkDetailView> views = new ArrayList<>();
        Bundle bundle = getArguments();
        NetworkRecord record = (NetworkRecord) bundle.getSerializable(NetworkListView.KEY_RECORD);
        views.add(new NetworkDetailView(getContext()));
        views.add(new NetworkDetailView(getContext()));
        NetworkPagerAdapter adapter = new NetworkPagerAdapter(views, record);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDiverRequest.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                mDiverResponse.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
                mTvRequest.setSelected(position == 0);
                mTvResponse.setSelected(position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_pager_request) {
            mViewPager.setCurrentItem(0, true);
        } else if (v.getId() == R.id.tv_pager_response) {
            mViewPager.setCurrentItem(1, true);
        }
    }
}