package com.salton123.qa.kit.sysinfo;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mobile.mobileinfo.SystemInfoAcitivity;
import com.salton123.qa.QualityAssistant;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.zhenai.qa.R;

/**
 * 设备、app信息
 * Created by zhangweida on 2018/6/22.
 */

public class SysInfo implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_sysinfo;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_sys_info;
            }

            @Override
            public void onClick(Context context) {
                Intent intent = new Intent(context, SystemInfoAcitivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }

}
