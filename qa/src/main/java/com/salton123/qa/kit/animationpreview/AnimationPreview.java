package com.salton123.qa.kit.animationpreview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.constant.FragmentIndex;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.UniversalActivity;
import com.zhenai.qa.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019-12-16 21:57
 * ModifyTime: 21:57
 * Description:
 */
public class AnimationPreview implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_animation_preview;
            }

            @Override
            public int getIcon() {
                return R.drawable.ic_animation_preview;
            }

            @Override
            public void onClick(Context context) {
                Intent intent = new Intent(context, UniversalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_ANIMATION_PREVIEW);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }
}
