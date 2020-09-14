package com.salton123.qa.kit.sysinfo;

import java.io.Serializable;

/**
 * Created by wanglikun on 2018/9/14.
 */

public class SysInfoItem implements Serializable {
    public final String name;
    public final String value;

    public SysInfoItem(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
