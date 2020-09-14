package com.salton123.qa.kit.timecounter;

import android.os.Bundle;

import com.salton123.qa.kit.timecounter.bean.CounterInfo;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @desc: 跳转耗时历史记录列表
 */

public class TimeCounterListFragment extends QBaseFragment {
    private static final String TAG = "TimeCounterListFragment";
    private RecyclerView mBlockList;
    private TimeCounterListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_time_counter_list;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        initView();
        load();
    }

    private void initView() {
        mBlockList = f(R.id.block_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBlockList.setLayoutManager(layoutManager);
        mAdapter = new TimeCounterListAdapter(getContext());
        mBlockList.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mBlockList.addItemDecoration(decoration);
    }

    private void load() {
        List<CounterInfo> infos = new ArrayList<>(TimeCounterManager.get().getHistory());
        infos.add(0, TimeCounterManager.get().getAppSetupInfo());
        Collections.sort(infos, new Comparator<CounterInfo>() {
            @Override
            public int compare(CounterInfo lhs, CounterInfo rhs) {
                return Long.valueOf(rhs.time)
                        .compareTo(lhs.time);
            }
        });
        mAdapter.setData(infos);
    }


}