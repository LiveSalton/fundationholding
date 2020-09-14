package com.salton123.arch.mvp;

import com.salton123.arch.view.IBaseView;

public class BindPresenter<V extends IBaseView> implements BasePresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
