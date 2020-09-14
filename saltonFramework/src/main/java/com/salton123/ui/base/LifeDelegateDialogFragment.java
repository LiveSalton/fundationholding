package com.salton123.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.feature.IFeature;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 19:01
 * ModifyTime: 19:01
 * Description:
 */
public abstract class LifeDelegateDialogFragment extends DialogFragment implements IComponentLife {
    private LifeDelegate mLifeDelegate;
    public List<IFeature> mFeatures = new ArrayList<>();

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    public void addFeature(IFeature feature) {
        this.mFeatures.add(feature);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLifeDelegate = new LifeDelegate(this);
        mLifeDelegate.initVariable(savedInstanceState);
        bindLogic();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBind();
    }

    @Override
    public void syncTitleBar(View titleBarView) {
        mLifeDelegate.syncTitleBar(titleBarView);
    }

    @Override
    public Activity activity() {
        return mLifeDelegate.activity();
    }

    @Override
    public <T extends View> T f(int resId) {
        return mLifeDelegate.f(resId);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void longToast(String toast) {
        mLifeDelegate.longToast(toast);
    }

    @Override
    public void shortToast(String toast) {
        mLifeDelegate.shortToast(toast);
    }

    @Override
    public void log(String msg) {
        mLifeDelegate.log(msg);
    }

    @Override
    public void openActivity(Class<?> clz, @Nullable Bundle bundle) {
        mLifeDelegate.openActivity(clz, bundle);
    }

    @Override
    public void openActivityForResult(Class<?> clz, @Nullable Bundle bundle, int requestCode) {
        mLifeDelegate.openActivityForResult(clz, bundle, requestCode);
    }

    public void openActivity(Class<?> clz) {
        if (activity() != null) {
            Intent intent = new Intent(activity(), clz);
            activity().startActivity(intent);
        }
    }

    public void openActivityForResult(Class<?> clz, int requestCode) {
        if (activity() != null) {
            Intent intent = new Intent(activity(), clz);
            activity().startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void setListener(int... ids) {
        mLifeDelegate.setListener(ids);
    }

    @Override
    public void setListener(View... views) {
        mLifeDelegate.setListener(views);
    }

    @Override
    public void show(View... views) {
        mLifeDelegate.show(views);
    }

    @Override
    public void hide(View... views) {
        mLifeDelegate.hide(views);
    }

    @Override
    public IComponentLife self() {
        return this;
    }

    public void bindLogic() {
        for (IFeature item : mFeatures) {
            item.onBindLogic();
        }
    }

    public void bindUI() {
        for (IFeature item : mFeatures) {
            item.onBindUI();
        }
    }

    public void unBind() {
        for (IFeature item : mFeatures) {
            item.onUnBind();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = mLifeDelegate.getRootView();
        bindUI();
        return view;
    }
}
