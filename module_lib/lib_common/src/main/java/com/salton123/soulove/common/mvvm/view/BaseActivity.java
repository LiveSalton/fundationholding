package com.salton123.soulove.common.mvvm.view;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.salton123.soulove.common.R;
import com.salton123.soulove.common.event.ActivityEvent;
import com.salton123.soulove.common.mvvm.view.status.LoadingStatus;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * Author: Thomas.
 * <br/>Date: 2019/9/10 8:23
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:Activity基类
 */
public abstract class BaseActivity extends SwipeBackActivity implements BaseView {
    protected static final String TAG = BaseActivity.class.getSimpleName();
    //Rxview解绑
    private CompositeDisposable mCompositeDisposable;
    //用于延时显示loading状态
    private Handler mLoadingHandler = new Handler();
    //状态页管理
    protected LoadService mBaseLoadService;
    //默认标题栏
    protected CommonTitleBar mSimpleTitleBar;
    //公用Handler
    protected Handler mHandler = new Handler();


    protected abstract int onBindLayout();

    protected void initParam() {
    }

    public abstract void initView();

    public void initListener() {
    }

    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout_root);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initCommonView();
        initParam();
        initView();
        initListener();
        initData();
    }

    /**
     * RxView添加订阅
     */
    protected void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消RxView所有订阅
     */
    protected void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 取消RxView某个订阅
     */
    protected void removeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.remove(disposable);
        }
    }

    /**
     * 初始化基本布局
     */
    private void initCommonView() {
        ViewStub viewStubContent = findViewById(R.id.view_stub_content);
        mSimpleTitleBar = findViewById(R.id.ctb_simple);
        if (enableSimplebar()) {
            mSimpleTitleBar.setBackgroundResource(R.drawable.shap_common_simplebar);
            mSimpleTitleBar.setVisibility(View.VISIBLE);
            initSimpleBar(mSimpleTitleBar);
        }
        viewStubContent.setLayoutResource(onBindLayout());
        View contentView = viewStubContent.inflate();

        LoadSir.Builder builder = new LoadSir.Builder()
                .addCallback(getInitStatus())
                .addCallback(getEmptyStatus())
                .addCallback(getErrorStatus())
                .addCallback(getLoadingStatus())
                .setDefaultCallback(getInitStatus().getClass());
        if (!CollectionUtils.isEmpty(getExtraStatus())) {
            for (Callback callback : getExtraStatus()) {
                builder.addCallback(callback);
            }
        }
        mBaseLoadService = builder.build().register(contentView, (Callback.OnReloadListener) v -> BaseActivity.this.onReload(v));
        mBaseLoadService.showSuccess();
    }

    /**
     * 点击标题栏返回按钮事件
     */
    public void onSimpleBackClick() {
        pop();
    }

    /**
     * 设置标题栏背景颜色
     *
     * @return
     */
    protected void setSimpleBarBg(@ColorInt int color) {
        mSimpleTitleBar.setBackgroundColor(color);
    }


    /**
     * 设置标题栏标题
     *
     * @return
     */
    protected void setTitle(String[] strings) {
        if (!enableSimplebar()) {
            throw new IllegalStateException("导航栏中不可用,请设置enableSimplebar为true");
        } else if (onBindBarCenterStyle() != SimpleBarStyle.CENTER_TITLE) {
            throw new IllegalStateException("导航栏中间布局不为标题类型,请设置onBindBarCenterStyle(TitleBarStyle.CENTER_TITLE)");
        } else {
            if (strings != null && strings.length > 0) {
                if (strings.length > 0 && null != strings[0] && strings[0].trim().length() > 0) {
                    TextView title = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_title);
                    title.setVisibility(View.VISIBLE);
                    title.setText(strings[0]);
                }
                if (strings.length > 1 && null != strings[1] && strings[1].trim().length() > 0) {
                    TextView subtitle = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_subtitle);
                    subtitle.setVisibility(View.VISIBLE);
                    subtitle.setText(strings[1]);
                }
            }
        }
    }

    /**
     * 设置标题栏文本颜色
     *
     * @return
     */
    protected void setBarTextColor(@ColorInt int color) {
        if (!enableSimplebar()) {
            throw new IllegalStateException("导航栏中不可用,请设置enableSimplebar为true");
        } else {
            TextView tvBack = mSimpleTitleBar.getLeftCustomView().findViewById(R.id.tv_back);
            tvBack.setTextColor(color);
            TextView tvTitle = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_title);
            tvTitle.setTextColor(color);
            TextView tvSubtitle = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_subtitle);
            tvSubtitle.setTextColor(color);
            TextView tv1 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.tv1_right);
            tv1.setTextColor(color);
            TextView tv2 = mSimpleTitleBar.getRightCustomView().findViewById(R.id.tv2_right);
            tv2.setTextColor(color);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (!enableSimplebar()) {
            return;
        }
        if (onBindBarCenterStyle() == SimpleBarStyle.CENTER_TITLE && !TextUtils.isEmpty(title)) {
            TextView tvtitle = mSimpleTitleBar.getCenterCustomView().findViewById(R.id.tv_title);
            tvtitle.setVisibility(View.VISIBLE);
            tvtitle.setText(title);
        }
    }


    /**
     * 显示初始化状态页
     */
    public void showInitView() {
        clearStatus();
        mBaseLoadService.showCallback(getInitStatus().getClass());
    }

    /**
     * 显示出错状态页
     */
    public void showErrorView() {
        clearStatus();
        mBaseLoadService.showCallback(getErrorStatus().getClass());
    }

    /**
     * 显示空数据状态页
     */
    public void showEmptyView() {
        clearStatus();
        mBaseLoadService.showCallback(getEmptyStatus().getClass());

    }

    /**
     * 显示loading状态页
     *
     * @param tip 为null时不带提示文本
     */
    public void showLoadingView(String tip) {
        mLoadingHandler.removeCallbacksAndMessages(null);
        mBaseLoadService.showSuccess();
        mBaseLoadService.setCallBack(getLoadingStatus().getClass(), (context, view1) -> {
            TextView tvTip = view1.findViewById(R.id.tv_tip);
            if (tvTip == null) {
                throw new IllegalStateException(getLoadingStatus().getClass() + "必须带有显示提示文本的TextView,且id为R.id.tv_tip");
            }
            if (tip == null) {
                tvTip.setVisibility(View.GONE);
            } else {
                tvTip.setVisibility(View.VISIBLE);
                tvTip.setText(tip);
            }
        });
        //延时100毫秒显示,避免闪屏
        mLoadingHandler.postDelayed(() -> mBaseLoadService.showCallback(getLoadingStatus().getClass()), 300);
    }

    /**
     * 清除所有状态页
     */
    public void clearStatus() {
        mLoadingHandler.removeCallbacksAndMessages(null);
        mBaseLoadService.showSuccess();
    }

    /**
     * 点击状态页默认执行事件
     */
    protected void onReload(View v) {
        clearStatus();
        initData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActivityEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventSticky(ActivityEvent event) {
    }

    /**
     * findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onBackPressedSupport() {
        //如果正在显示loading,则清除
        if (mBaseLoadService.getCurrentCallback() == LoadingStatus.class) {
            clearStatus();
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        KeyboardUtils.fixSoftInputLeaks(this);
        EventBus.getDefault().unregister(this);
        clearDisposable();
    }

}
