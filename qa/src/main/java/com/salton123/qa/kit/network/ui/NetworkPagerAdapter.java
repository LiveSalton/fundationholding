package com.salton123.qa.kit.network.ui;

import android.view.View;
import android.view.ViewGroup;

import com.salton123.qa.kit.network.bean.NetworkRecord;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class NetworkPagerAdapter extends PagerAdapter {
    private List<NetworkDetailView> views;
    private NetworkRecord mRecord;

    public NetworkPagerAdapter(List<NetworkDetailView> views, NetworkRecord record) {
        this.views = views;
        mRecord = record;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkDetailView view = views.get(position);
        if (position == 0) {
            view.bindRequest(mRecord);
        } else {
            view.bindResponse(mRecord);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}