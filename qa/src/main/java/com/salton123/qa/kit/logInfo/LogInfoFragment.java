package com.salton123.qa.kit.logInfo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.salton123.log.XLog;
import com.salton123.qa.ui.base.QBaseFragment;
import com.salton123.qa.ui.loginfo.LogItemAdapter;
import com.zhenai.qa.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/9 11:51
 * ModifyTime: 11:51
 * Description:
 */
public class LogInfoFragment extends QBaseFragment implements LogInfoManager.OnLogCatchListener {
    private static final int MAX_LOG_LINE_NUM = 10000;
    private static final String TAG = "LogInfoFragment";
    private RecyclerView mLogList;
    private LogItemAdapter mLogItemAdapter;
    private EditText mLogFilter;
    private RadioGroup mRadioGroup;
    private boolean mIsLoaded;

    private int counter = 0;
    private static final int UPDATE_CHECK_INTERVAL = 200;
    private boolean mAutoscrollToBottom = true;

    @Override
    public int getLayout() {
        return R.layout.dk_float_log_info;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {
        LogInfoManager.getInstance().registerListener(this);
        LogInfoManager.getInstance().start();
    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_log_info));
        mLogList = f(R.id.log_list);
        mLogList.setLayoutManager(new LinearLayoutManager(getContext()));
        mLogItemAdapter = new LogItemAdapter(getContext());
        mLogList.setAdapter(mLogItemAdapter);
        mLogFilter = f(R.id.log_filter);
        mRadioGroup = f(R.id.radio_group);
        mRadioGroup.check(R.id.verbose);
    }

    @Override
    public void initListener() {
        super.initListener();
        showTransLoadingView();
        mLogFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLogItemAdapter.getFilter().filter(s);
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.verbose) {
                    mLogItemAdapter.setLogLevelLimit(Log.VERBOSE);
                } else if (checkedId == R.id.debug) {
                    mLogItemAdapter.setLogLevelLimit(Log.DEBUG);
                } else if (checkedId == R.id.info) {
                    mLogItemAdapter.setLogLevelLimit(Log.INFO);
                } else if (checkedId == R.id.warn) {
                    mLogItemAdapter.setLogLevelLimit(Log.WARN);
                } else if (checkedId == R.id.error) {
                    mLogItemAdapter.setLogLevelLimit(Log.ERROR);
                }
                mLogItemAdapter.getFilter().filter(mLogFilter.getText());
            }
        });
        mLogList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // if the bottom of the list isn't visible anymore, then stop autoscrolling
                mAutoscrollToBottom = (layoutManager.findLastCompletelyVisibleItemPosition() ==
                        recyclerView.getAdapter().getItemCount() - 1);
            }
        });
    }

    @Override
    public void onLogCatch(List<LogLine> logLines) {
        if (mLogList == null || mLogItemAdapter == null) {
            return;
        }
        if (!mIsLoaded) {
            mIsLoaded = true;
            showSuccessView();
            mLogList.setVisibility(View.VISIBLE);
        }
        if (logLines.size() == 1) {
            mLogItemAdapter.addWithFilter(logLines.get(0), mLogFilter.getText(), true);
        } else {
            for (LogLine line : logLines) {
                mLogItemAdapter.addWithFilter(line, mLogFilter.getText(), false);
            }
            mLogItemAdapter.notifyDataSetChanged();
        }
        if (++counter % UPDATE_CHECK_INTERVAL == 0
                && mLogItemAdapter.getTrueValues().size() > MAX_LOG_LINE_NUM) {
            int numItemsToRemove = mLogItemAdapter.getTrueValues().size() - MAX_LOG_LINE_NUM;
            mLogItemAdapter.removeFirst(numItemsToRemove);
            XLog.d(TAG, "truncating %d lines from log list to avoid out of memory errors:" + numItemsToRemove);
        }
        if (mAutoscrollToBottom) {
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        mLogList.scrollToPosition(mLogItemAdapter.getItemCount() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogInfoManager.getInstance().stop();
        LogInfoManager.getInstance().removeListener();
    }


}
