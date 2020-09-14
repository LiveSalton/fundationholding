package com.salton123.xmly.base;

import com.salton123.arch.mvp.BaseMvpFragment;
import com.salton123.xmly.mvp.RequestContract;
import com.salton123.xmly.mvp.RequestPresenter;

/**
 * User: newSalton@outlook.com
 * Date: 2019-09-15 22:38
 * ModifyTime: 22:38
 * Description:
 */
public abstract class TingRequestFragment extends BaseMvpFragment<RequestPresenter> implements RequestContract.IRequestView{
    @Override
    public RequestPresenter getPresenter() {
        return new RequestPresenter();
    }
}
