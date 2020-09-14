package com.salton123.qa.kit.fileexplorer;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.FileUtils;
import com.salton123.log.XLog;
import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.qa.ui.fileexplorer.TextViewerAdapter;
import com.zhenai.qa.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/27 20:37
 * ModifyTime: 20:37
 * Description:
 */
@SuppressLint("CheckResult")
public class TextViewerActivity extends QBaseActivity {
    private static final String TAG = "TextViewerActivity";
    private RecyclerView mRecyclerView;
    private TextViewerAdapter mAdapter;
    private Disposable mDisposable;

    @Override
    public int getLayout() {
        return R.layout.common_item_recyclerview;
    }
    private File targetFile;
    @Override
    public void initViewAndData() {
        super.initViewAndData();
        mRecyclerView = f(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        mAdapter = new TextViewerAdapter(activity());
        mRecyclerView.setAdapter(mAdapter);
        targetFile = (File) getIntent().getSerializableExtra(BundleKey.FILE_KEY);
        if (targetFile == null){
            finish();
            return;
        }
        mDisposable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            try {
                FileReader fileReader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(fileReader);
                String textLine;
                while ((textLine = br.readLine()) != null) {
                    emitter.onNext(textLine);
                }
                br.close();
                fileReader.close();
            } catch (IOException e) {
                XLog.e(TAG, e.toString());
            }
        }).subscribe(s -> mAdapter.addItemAndNotify(s), throwable -> {
            XLog.e(TAG, throwable.getMessage());
        });
    }

    @Override
    public String getTitleText() {
        return FileUtils.getFileName(targetFile);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
