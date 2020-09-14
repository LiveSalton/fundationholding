package com.salton123.qa.kit.alignruler;

import android.content.Context;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.config.AlignRulerConfig;
import com.salton123.qa.constant.PageTag;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.base.FloatPageManager;
import com.salton123.qa.ui.base.PageIntent;
import com.zhenai.qa.R;

/**
 * Created by wanglikun on 2018/9/19.
 */

public class AlignRuler implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {
            @Override
            public int getName() {
                return R.string.dk_kit_align_ruler;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_align_ruler;
            }

            @Override
            public void onClick(Context context) {
                PageIntent pageIntent = new PageIntent(AlignRulerMarkerFloatPage.class);
                pageIntent.tag = PageTag.PAGE_ALIGN_RULER_MARKER;
                pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(pageIntent);

                pageIntent = new PageIntent(AlignRulerLineFloatPage.class);
                pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(pageIntent);

                pageIntent = new PageIntent(AlignRulerInfoFloatPage.class);
                pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                FloatPageManager.getInstance().add(pageIntent);

                AlignRulerConfig.setAlignRulerOpen(context, true);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {
        AlignRulerConfig.setAlignRulerOpen(context, false);
    }
}
