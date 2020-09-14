package com.salton123.feature;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/12/12 14:06
 * ModifyTime: 14:06
 * Description:
 */
public class ArouterFeature implements IFeature {
    private Object target;

    public ArouterFeature(Object target) {
        this.target = target;
    }

    @Override
    public void onBindLogic() {
        ARouter.getInstance().inject(target);
    }

    @Override
    public void onBindUI() {

    }

    @Override
    public void onUnBind() {

    }
}
