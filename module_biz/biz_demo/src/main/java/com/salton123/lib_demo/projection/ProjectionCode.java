package com.salton123.lib_demo.projection;

import androidx.annotation.IntDef;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/30 16:48
 * ModifyTime: 16:48
 * Description:
 */
@IntDef({
        ProjectionCode.CODE_GET_PROJECTION_SUCCEED,
        ProjectionCode.CODE_GET_PROJECTION_FAILED,
})
public @interface ProjectionCode {
    int CODE_GET_PROJECTION_SUCCEED = 0x1;
    int CODE_GET_PROJECTION_FAILED = 0x2;
}
