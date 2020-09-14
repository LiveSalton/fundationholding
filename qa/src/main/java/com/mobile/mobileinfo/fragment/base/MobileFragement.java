package com.mobile.mobileinfo.fragment.base;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobile.mobileinfo.adapter.MobInfoItemAdapter;
import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.util.StringUtil;
import com.salton123.app.crash.ThreadUtils;
import com.salton123.qa.ui.widget.recyclerview.DividerItemDecoration;
import com.salton123.ui.base.BaseFragment;
import com.zhenai.qa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.CLIPBOARD_SERVICE;

public abstract class MobileFragement extends BaseFragment {
    private RecyclerView mRecyclerView;
    public MobInfoItemAdapter mBaseQuickAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_app;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        mRecyclerView = f(R.id.recyclerView);
        mBaseQuickAdapter = new MobInfoItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        mRecyclerView.setAdapter(mBaseQuickAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mRecyclerView.addItemDecoration(decoration);
        mBaseQuickAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    ClipData clipData =
                            ClipData.newPlainText("Label", (String) mBaseQuickAdapter.getItem(position).getKey() + "\n"
                                    + ((mBaseQuickAdapter.getItem(position).getValue() instanceof String) ?
                                    (String) mBaseQuickAdapter.getItem(position).getValue() : ""));
                    ((ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(clipData);
                    Toast.makeText(getContext(), "copy success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        getParam();
    }

    public void getParam() {
        ThreadUtils.executeBySingle(new ThreadUtils.SimpleTask<List<Param>>() {
            @Nullable
            @Override
            public List<Param> doInBackground() throws Throwable {
                return addListView();
            }

            @Override
            public void onSuccess(@Nullable List<Param> result) {
                mBaseQuickAdapter.addData(result);
            }
        });
    }

    public List<Param> getListParam(String key, Object value) {
        List<Param> list = new ArrayList<>();
        Param param = new Param();
        param.setValue(value + "");
        param.setKey(key);
        list.add(param);
        return list;
    }

    public List<Param> getListParam(JSONObject jsonObject) {
        List<Param> list = new ArrayList<>();
        try {
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                Param param = new Param();
                String key = (String) iterator.next();
                param.setKey(key);
                if (jsonObject.get(key) instanceof Drawable) {
                    param.setValue(jsonObject.get(key));
                } else {
                    param.setValue(StringUtil.formatString(jsonObject.get(key).toString()));
                }
                list.add(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Param> getListParam(JSONArray jsonArray) {
        List<Param> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Param param = new Param();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject.has("isSystem")) {
                    if (!jsonObject.getBoolean("isSystem")) {
                        param.setValue(jsonObject.get("icon"));
                        jsonObject.remove("icon");
                        String key = StringUtil.formatString(jsonObject.toString());
                        param.setKey(key);
                        list.add(param);
                    }
                } else {
                    param.setKey(i + 1 + "");
                    String value = StringUtil.formatString(jsonObject.toString());
                    param.setValue(value);
                    list.add(param);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Param> getListCameraParam(JSONArray jsonArray) {
        List<Param> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Param param = new Param();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                param.setKey(i + 1 + "");
                String value = jsonObject.toString();
                param.setValue(value);

                list.add(param);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public abstract List<Param> addListView();
    
}
