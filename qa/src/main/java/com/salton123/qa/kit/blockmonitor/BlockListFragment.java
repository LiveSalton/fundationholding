package com.salton123.qa.kit.blockmonitor;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.salton123.qa.kit.blockmonitor.bean.BlockInfo;
import com.salton123.qa.kit.blockmonitor.core.BlockMonitorManager;
import com.salton123.qa.kit.blockmonitor.core.OnBlockInfoUpdateListener;
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
 * @desc: 卡顿检测日志列表
 */

public class BlockListFragment extends QBaseFragment implements OnBlockInfoUpdateListener {
    private static final String TAG = "BlockMonitorIndexFragment";

    private RecyclerView mBlockList;
    private BlockListAdapter mBlockListAdapter;
    private TextView mLogDetail;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_block_list;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        initView();
        load();
        BlockMonitorManager.getInstance().setOnBlockInfoUpdateListener(this);
    }

    private void initView() {
        setTitleText(getString(R.string.dk_kit_block_monitor_list));
        mBlockList = f(R.id.block_list);
        mLogDetail = f(R.id.tx_block_detail);
        mLogDetail.setMovementMethod(ScrollingMovementMethod.getInstance());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBlockList.setLayoutManager(layoutManager);
        mBlockListAdapter = new BlockListAdapter(getContext());
        mBlockList.setAdapter(mBlockListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mBlockList.addItemDecoration(decoration);
        mBlockListAdapter.setOnItemClickListener(new BlockListAdapter.OnItemClickListener() {
            @Override
            public void onClick(BlockInfo info) {
                mLogDetail.setText(info.toString());
                mLogDetail.setVisibility(View.VISIBLE);
                mBlockList.setVisibility(View.GONE);
                setTitleText(getResources().getString(R.string.dk_kit_block_monitor_detail));
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mLogDetail.getVisibility() == View.VISIBLE) {
            mLogDetail.setVisibility(View.GONE);
            mBlockList.setVisibility(View.VISIBLE);
            setTitleText(getString(R.string.dk_kit_block_monitor_list));
            return true;
        }
        return super.onBackPressedSupport();
    }

    private void load() {
        List<BlockInfo> infos = new ArrayList<>(BlockMonitorManager.getInstance().getBlockInfoList());
        Collections.sort(infos, new Comparator<BlockInfo>() {
            @Override
            public int compare(BlockInfo lhs, BlockInfo rhs) {
                return Long.valueOf(rhs.time)
                        .compareTo(lhs.time);
            }
        });
        mBlockListAdapter.setData(infos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BlockMonitorManager.getInstance().setOnBlockInfoUpdateListener(null);
    }

    @Override
    public void onBlockInfoUpdate(BlockInfo blockInfo) {
        mBlockListAdapter.append(blockInfo, 0);
    }
}