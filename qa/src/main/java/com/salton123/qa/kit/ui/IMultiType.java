package com.salton123.qa.kit.ui;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/11 19:52
 * ModifyTime: 19:52
 * Description:
 */
public interface IMultiType {
    int TYPE_TOOL = 0x101;
    int TYPE_FOOTER = 0x102;
    int TYPE_HEADER = 0x103;

    int getType();
}
