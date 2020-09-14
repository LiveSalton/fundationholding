package com.salton123.xmly.base;

import android.os.Bundle;

import com.salton123.arch.mvp.BasePresenter;
import com.salton123.arch.view.IBaseView;
import com.salton123.ui.base.BaseDialogFragment;

import androidx.annotation.Nullable;

/**
 * User: newSalton@outlook.com
 * Date: 2019/9/29 17:18
 * ModifyTime: 17:18
 * Description:
 */
public abstract class MvpDialogFragment<T extends BasePresenter> extends BaseDialogFragment implements IBaseView {

    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    public abstract T getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
