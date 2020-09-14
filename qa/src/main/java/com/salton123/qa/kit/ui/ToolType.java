package com.salton123.qa.kit.ui;

import com.salton123.qa.kit.IKit;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/11 19:59
 * ModifyTime: 19:59
 * Description:
 */
public class ToolType implements IMultiType {
    public List<IKit> mKits;
    public String title;
    public String subTitle;

    public ToolType(List<IKit> kits, String title, String subTitle) {
        this.mKits = kits;
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public int getType() {
        return TYPE_TOOL;
    }
}
