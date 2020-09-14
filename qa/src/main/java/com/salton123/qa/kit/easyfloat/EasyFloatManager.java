package com.salton123.qa.kit.easyfloat;

import com.lzf.easyfloat.EasyFloat;
import com.salton123.utils.ActivityUtils;

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/20 13:58
 * ModifyTime: 13:58
 * Description:
 */
public class EasyFloatManager {
    private EasyFloatManager() {
    }

    public static class Holder {
        private static EasyFloatManager INSTANCE = new EasyFloatManager();
    }

    public static EasyFloatManager getInstance() {
        return Holder.INSTANCE;
    }

    public void show(IEasyFloat iEasyFloat) {
        if (iEasyFloat instanceof IActivityFloat) {
            EasyFloat.with(ActivityUtils.getCurrentResumedActivity())
                    .setLayout(iEasyFloat.getLayout())
                    .show();
        } else if (iEasyFloat instanceof IWindowFloat) {

        }
    }

    public void hide(IEasyFloat iEasyFloat) {
        if (iEasyFloat instanceof IActivityFloat) {
            EasyFloat.dismiss(ActivityUtils.getCurrentResumedActivity(), iEasyFloat.getTAG());
        } else if (iEasyFloat instanceof IWindowFloat) {
            EasyFloat.dismissAppFloat(iEasyFloat.getTAG());
        }
    }
}
