package com.salton123.soulove.common.event;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/9 21:42
 * ModifyTime: 21:42
 * Description:
 */
public class TypeEvent {
    public static final int TYPE_CLEAR_HISTORY = 1;
    private int type;

    public TypeEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
