package com.salton123.arch.mvp;

import com.salton123.arch.view.IBaseView;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 11:06
 * Time: 11:06
 * Description:
 */
public interface BasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();
}
