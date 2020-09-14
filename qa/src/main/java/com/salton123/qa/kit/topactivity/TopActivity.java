package com.salton123.qa.kit.topactivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.config.TopActivityConfig;
import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.constant.FragmentIndex;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.UniversalActivity;
import com.zhenai.qa.R;

/**
 * 项目名:    Android
 * 包名       com.salton123.qa.kit.topactivity
 * 文件名:    TopActivity
 * 创建时间:  2019-04-29 on 12:13
 * 描述:     当前栈顶的Activity信息
 *
 * @author 阿钟
 */

public class TopActivity implements IKit {

    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_top_activity;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_view_check;
            }

            @Override
            public void onClick(Context context) {
                Intent intent = new Intent(context, UniversalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_TOP_ACTIVITY);
                context.startActivity(intent);
            }

        };
    }

    @Override
    public void onAppInit(Context context) {
        TopActivityConfig.setTopActivityOpen(context, false);
    }
}
