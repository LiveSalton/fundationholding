package com.salton123.arch.mvvm;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.salton123.arch.mvvm.viewmodel.BaseRefreshViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;


public abstract class BaseMvvmRefreshFragment<VM extends BaseRefreshViewModel, T> extends BaseMvvmFragment<VM>
        implements OnRefreshLoadMoreListener {

    private WrapRefresh mWrapRefresh;

    @Override
    public void initListener() {
        super.initListener();
        mWrapRefresh = onBindWrapRefresh();
        mWrapRefresh.refreshLayout.setOnRefreshLoadMoreListener(this);
    }


    protected abstract @NonNull
    WrapRefresh onBindWrapRefresh();

    @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        mViewModel.getFinishRefreshEvent().observe(this, (Observer<List<T>>) list -> {
            if (list == null) {
                mWrapRefresh.refreshLayout.finishRefresh(false);
                return;
            }
            if (list.size() == 0) {
                mWrapRefresh.refreshLayout.finishRefresh(true);
                return;
            }
            mWrapRefresh.refreshLayout.finishRefresh(true);
            onRefreshSucc(list);
        });
        mViewModel.getFinishLoadmoreEvent().observe(this, (Observer<List<T>>) list -> {
            if (list == null) {
                mWrapRefresh.refreshLayout.finishLoadMore(false);
                return;
            }
            if (list.size() == 0) {
                mWrapRefresh.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
            mWrapRefresh.refreshLayout.finishLoadMore(true);
            onLoadMoreSucc(list);
        });
    }

    protected void onRefreshSucc(List<T> list) {
        if (mWrapRefresh.quickAdapter != null) {
            mWrapRefresh.quickAdapter.setNewData(list);
        }
    }

    protected void onLoadMoreSucc(List<T> list) {
        if (mWrapRefresh.quickAdapter != null) {
            mWrapRefresh.quickAdapter.addData(list);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mViewModel.onViewLoadmore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.onViewRefresh();
    }

    protected class WrapRefresh {
        SmartRefreshLayout refreshLayout;
        BaseQuickAdapter<T, BaseViewHolder> quickAdapter;

        public WrapRefresh(@NonNull SmartRefreshLayout refreshLayout, BaseQuickAdapter<T, BaseViewHolder> quickAdapter) {
            this.refreshLayout = refreshLayout;
            this.quickAdapter = quickAdapter;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mWrapRefresh) {
            mWrapRefresh.refreshLayout.setOnRefreshLoadMoreListener(null);
        }
    }
}
