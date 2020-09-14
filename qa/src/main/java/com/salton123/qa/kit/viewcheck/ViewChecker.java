package com.salton123.qa.kit.viewcheck;

import android.content.Context;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.config.ViewCheckConfig;
import com.salton123.qa.constant.PageTag;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.zhenai.qa.R;

/**
 * Created by wanglikun on 2018/11/20.
 */

public class ViewChecker implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_view_check;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_view_check;
            }

            @Override
            public void onClick(Context context) {
                PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
                intent.tag = PageTag.PAGE_VIEW_CHECK;
                intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(intent);

                intent = new PageIntent(ViewCheckInfoFloatPage.class);
                intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(intent);

                intent = new PageIntent(ViewCheckDrawFloatPage.class);
                intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(intent);

                ViewCheckConfig.setViewCheckOpen(context, true);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {
        ViewCheckConfig.setViewCheckOpen(context, false);
    }
}