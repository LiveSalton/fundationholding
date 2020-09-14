package com.salton123.utils;

import android.content.Context;

import com.salton123.log.XLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by wanglikun on 2018/11/17.
 */
public class CacheUtils {
    private static final String TAG = "CacheUtils";

    private CacheUtils() {
    }

    public static boolean saveObject(Context context, String key, Serializable ser) {
        File file = new File(context.getCacheDir() + "/" + key);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                XLog.e(TAG, e.toString());
            }
        }
        return saveObject(ser, file);
    }

    public static Serializable readObject(Context context, String key) {
        File file = new File(context.getCacheDir() + "/" + key);
        return readObject(file);
    }

    public static boolean saveObject(Serializable ser, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (IOException e) {
            XLog.e(TAG, e.toString());
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    XLog.e(TAG, e.toString());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    XLog.e(TAG, e.toString());
                }
            }
        }
    }

    public static Serializable readObject(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (IOException e) {
            if (e instanceof InvalidClassException) {
                file.delete();
            }
            XLog.e(TAG, e.toString());
            return null;
        } catch (ClassNotFoundException e) {
            XLog.e(TAG, e.toString());
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    XLog.e(TAG, e.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    XLog.e(TAG, e.toString());
                }
            }
        }
    }
}