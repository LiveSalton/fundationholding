package com.salton123.arch.view;

import androidx.annotation.IntDef;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/5/6 16:17
 * ModifyTime: 16:17
 * Description:
 */
@IntDef({
        TitleBarStyle.ICON,
        TitleBarStyle.TEXT,
        TitleBarStyle.ICON_TEXT,
        TitleBarStyle.HIDDEN
})
public @interface TitleBarStyle {
    int ICON = 0x1;
    int TEXT = ICON + 1;
    int ICON_TEXT = ICON + 2;
    int HIDDEN = ICON + 3;
}
