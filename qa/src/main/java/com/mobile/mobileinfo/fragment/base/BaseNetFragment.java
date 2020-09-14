package com.mobile.mobileinfo.fragment.base;

import com.mobile.mobileinfo.data.Param;
import com.mobile.mobileinfo.util.OkHttpUrlLoader;

import org.json.JSONObject;

import java.util.List;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpListener;
import fairy.easy.httpmodel.resource.HttpType;

public abstract class BaseNetFragment extends MobileFragement {

    public abstract HttpType getType();

    public void getParam() {
        HttpModelHelper.getInstance()
                .init(getContext())
                .setChina(false)
                .setModelLoader(new OkHttpUrlLoader())
                .setFactory()
                .addType(getType())
                .build()
                .startAsync("https://www.baidu.com", new HttpListener() {
                    @Override
                    public void onFail(String data) {
                        mBaseQuickAdapter.addData(getListParam(getType().getName(), data));
                    }

                    @Override
                    public void onFinish(JSONObject result) {

                    }

                    @Override
                    public void onSuccess(HttpType httpType, JSONObject result) {
                        mBaseQuickAdapter.addData(getListParam(result));
                    }
                });
    }

    @Override
    public List<Param> addListView() {
        return null;
    }
}
