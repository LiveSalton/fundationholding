package com.salton123.qa.kit.weaknetwork;

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
 * 模拟弱网
 *
 * @author denghaha
 * created 2019/5/7 19:05
 */
public class WeakNetwork implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_weak_network;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_weak_network;
            }

            @Override
            public void onClick(Context context) {
                Intent intent = new Intent(context, UniversalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_WEAK_NETWORK);
                context.startActivity(intent);
            }

        };
    }

    @Override
    public void onAppInit(Context context) {

    }
}