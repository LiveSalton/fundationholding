package com.salton123.qa.kit.infopanel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.salton123.infopanel.InfoDispatcher;
import com.salton123.qa.QualityAssistant;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.zhenai.qa.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019/7/23 10:01
 * ModifyTime: 10:01
 * Description:
 */
public class InfoPanelKit implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {
            @Override
            public int getName() {
                return R.string.dk_frameinfo_infopanel;
            }

            @Override
            public int getIcon() {
                if (InfoDispatcher.INSTANCE.isInstalled) {
                    return R.drawable.ic_info_panel_open;
                } else {
                    return R.drawable.ic_info_panel_close;
                }
            }

            @Override
            public void onClick(Context context) {
                if (InfoDispatcher.INSTANCE.isInstalled) {
                    InfoDispatcher.INSTANCE.uninstall();
                    Toast.makeText(context, "实时信息面板已关闭", Toast.LENGTH_SHORT).show();
                } else {
                    InfoDispatcher.INSTANCE.install();
                    Toast.makeText(context, "实时信息面板已打开", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }
}
