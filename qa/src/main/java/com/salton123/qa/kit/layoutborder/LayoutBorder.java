package com.salton123.qa.kit.layoutborder;

import android.content.Context;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.config.LayoutBorderConfig;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.zhenai.qa.R;

/**
 * Created by wanglikun on 2019/1/7
 */
public class LayoutBorder implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {
            @Override
            public int getName() {
                return R.string.dk_kit_layout_border;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_view_border;
            }

            @Override
            public void onClick(Context context) {
                LayoutBorderManager.getInstance().start();
                LayoutBorderConfig.setLayoutBorderOpen(true);
                PageIntent intent = new PageIntent(LayoutLevelFloatPage.class);
                intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(intent);
                LayoutBorderConfig.setLayoutLevelOpen(true);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {
        LayoutBorderConfig.setLayoutBorderOpen(false);
        LayoutBorderConfig.setLayoutLevelOpen(false);
    }
}