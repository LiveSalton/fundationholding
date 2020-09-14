package com.salton123.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

import java.io.File;

public class DataCleanUtil {
    private DataCleanUtil() {
    }

    public static void cleanInternalCache(Context context) {
        FileUtil.deleteDirectory(context.getCacheDir());
    }


    public static void cleanDatabases(Context context) {
        FileUtil.deleteDirectory(new File(context.getFilesDir().getParent() + "/databases"));
    }

    public static void cleanSharedPreference(Context context) {
        FileUtil.deleteDirectory(new File(context.getFilesDir().getParent() + "/shared_prefs"));
    }

    public static void cleanFiles(Context context) {
        FileUtil.deleteDirectory(context.getFilesDir());
    }

    public static void cleanExternalFilesDir(Context context) {
        File file = context.getExternalFilesDir(null);
        if (file != null) {
            FileUtil.deleteDirectory(file);
        }
    }

    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            FileUtil.deleteDirectory(context.getExternalCacheDir());
        }
    }

    public static void cleanCustomCache(String filePath) {
        FileUtil.deleteDirectory(new File(filePath));
    }

    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        cleanExternalFilesDir(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    public static long getApplicationDataSize(Context context) {
        long size = 0;
        // internal cache
        size += FileUtil.getDirectorySize(context.getCacheDir());
        // databases
        size += FileUtil.getDirectorySize(new File(context.getFilesDir().getParent() + "/databases"));
        // shared preference
        size += FileUtil.getDirectorySize(new File(context.getFilesDir().getParent() + "/shared_prefs"));
        // files
        size += FileUtil.getDirectorySize(context.getFilesDir());

        File file = context.getExternalFilesDir(null);
        if (file != null) {
            size += FileUtil.getDirectorySize(file);
        }
        return size;
    }

    public static String getApplicationDataSizeStr(Context context) {
        return Formatter.formatFileSize(context, getApplicationDataSize(context));
    }
}