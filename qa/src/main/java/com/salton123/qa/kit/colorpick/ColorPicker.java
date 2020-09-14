package com.salton123.qa.kit.colorpick;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.constant.FragmentIndex;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.TranslucentActivity;
import com.zhenai.qa.R;

/**
 * Created by wanglikun on 2018/9/13.
 */

public class ColorPicker implements IKit {
    private static final String TAG = "ColorPicker";

    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {
            @Override
            public int getName() {
                return R.string.dk_kit_color_picker;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_color_picker;
            }

            @Override
            public void onClick(Context context) {
                Intent intent = new Intent(context, TranslucentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_COLOR_PICKER_SETTING);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }
}