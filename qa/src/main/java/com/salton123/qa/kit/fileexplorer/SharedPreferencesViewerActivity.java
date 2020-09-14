package com.salton123.qa.kit.fileexplorer;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.constant.SpInputType;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.utils.SharedPrefsUtil;
import com.zhenai.qa.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.salton123.utils.FileUtil.XML;

public class SharedPreferencesViewerActivity extends QBaseActivity {
    private String spTableName;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_sp_show;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_file_explorer));
        List<SpBean> spBeans = getSpBeans();
        if (spBeans.isEmpty()) {
            finish();
            return;
        }
        RecyclerView recyclerView = f(R.id.rv_sp);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity(), DividerItemDecoration.VERTICAL));
        SpAdapter spAdapter = new SpAdapter(activity());
        spAdapter.setOnSpDataChangerListener(bean -> spUpData(bean));
        spAdapter.append(spBeans);
        recyclerView.setAdapter(spAdapter);
        if (spTableName != null) {
            setTitleText(spTableName);
        }
    }

    private List<SpBean> getSpBeans() {
        ArrayList<SpBean> spBeans = new ArrayList<>();

        File mFile = (File) getIntent().getSerializableExtra(BundleKey.FILE_KEY);
        if (mFile == null) {
            return spBeans;
        }
        spTableName = mFile.getName().replace(XML, "");
        SharedPreferences sp = SharedPrefsUtil.getSharedPrefs(activity(), spTableName);
        Map<String, ?> all = sp.getAll();
        if (all.isEmpty()) {
            return spBeans;
        }
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            spBeans.add(new SpBean(entry.getKey(), entry.getValue()));
        }
        return spBeans;

    }

    public void spUpData(SpBean bean) {
        String key = bean.key;
        switch (bean.value.getClass().getSimpleName()) {
            case SpInputType.STRING:
                SharedPrefsUtil.putString(activity(), key, bean.value.toString());
                break;
            case SpInputType.BOOLEAN:
                SharedPrefsUtil.putBoolean(activity(), spTableName, key, (Boolean) bean.value);
                break;
            case SpInputType.INTEGER:
                SharedPrefsUtil.putInt(activity(), spTableName, key, (Integer) bean.value);
                break;
            case SpInputType.FLOAT:
                SharedPrefsUtil.putFloat(activity(), spTableName, key, (Float) bean.value);
                break;
            case SpInputType.LONG:
                SharedPrefsUtil.putLong(activity(), spTableName, key, (Long) bean.value);
                break;
        }
    }
}
