package com.salton123.fundation.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/14 10:25
 * ModifyTime: 10:25
 * Description:
 */
public class AssetsUtils {
    public static void copyAssetFile(Context context, String dirPath, String name) {
        String filePath = dirPath + "/" + name;

        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(filePath);
            if (!file.exists()) {
                InputStream is = context.getAssets().open(name);
                FileOutputStream fs = new FileOutputStream(file);
                byte[] buffer = new byte[1024];

                int count;
                while((count = is.read(buffer)) > 0) {
                    fs.write(buffer, 0, count);
                }

                fs.close();
                is.close();
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }
}
