package com.salton123.arch.view;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 15:22
 * Time: 15:22
 * Description:
 */
public interface IMultiStatusView {
    void showInitLoadView();

    void showNoDataView();

    void showTransLoadingView();

    void showNetWorkErrView();

    void showSuccessView();
}
