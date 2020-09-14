package com.salton123;

import android.app.Application;
import android.os.Environment;

import com.salton123.app.BaseApplication;

import java.io.File;

/**
 * User: newSalton@outlook.com
 * Date: 2019-05-09 23:42
 * ModifyTime: 23:42
 * Description:
 */
public class C {
    public static final String ARG_ITEM = "arg_item";
    public static Application APP = BaseApplication.getInstance();
    public static String BASE_PATH = new File(Environment.getExternalStorageDirectory(), "salton").getPath()
            + File.separator + BaseApplication.getInstance().getPackageName();

    public interface Provider {
        String APP = "/provider/app";
        String RECORD = "/provider/record";
    }
}
