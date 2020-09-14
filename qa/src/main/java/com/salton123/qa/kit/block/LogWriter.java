/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.salton123.qa.kit.block;

import android.util.Log;

import com.salton123.qa.kit.block.internal.BlockInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Log writer which runs in standalone thread.
 */
public class LogWriter {

    private static final String TAG = "LogWriter";

    private static final Object SAVE_DELETE_LOCK = new Object();

    private LogWriter() {
        throw new InstantiationError("Must not instantiate this class");
    }

    /**
     * Save log to file
     *
     * @param str block info string
     * @return log file path
     */
    public static String save(String str) {
        String path;
        synchronized (SAVE_DELETE_LOCK) {
            path = save("looper", str);
        }
        return path;
    }

    /**
     * Delete obsolete log files, which is by default 2 days.
     */
    public static void cleanObsolete() {
        HandlerThreadFactory.getWriteLogThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                synchronized (SAVE_DELETE_LOCK) {
                    deleteOldLogs();
                }
            }
        });
    }

    private static void deleteOldLogs() {
        File dirFile = detectedBlockDirectory();
        if (!dirFile.exists()) {
            return;
        }
        long now = System.currentTimeMillis();
        File[] files = dirFile.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                long lastModifiedTime = file.lastModified();
                if (now - lastModifiedTime >
                        BlockCanaryInternals.config().getLogDeleteDelayDay() * 24 * 60 * 60 * 1000) {
                    file.delete();
                }
            }
        }
    }

    public static void deleteAll() {
        synchronized (SAVE_DELETE_LOCK) {
            try {
                File[] files = getLogFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            } catch (Throwable e) {
                Log.e(TAG, "deleteAll: ", e);
            }
        }
    }

    private static String save(String logFileName, String str) {
        String path = "";
        BufferedWriter writer = null;
        try {
            File file = detectedBlockDirectory();
            long time = System.currentTimeMillis();
            path = file.getAbsolutePath() + "/"
                    + logFileName + "-"
                    + BlockInfo.TIME_FORMATTER.format(time) + ".log";

            OutputStreamWriter out =
                    new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8");

            writer = new BufferedWriter(out);

            writer.write(BlockInfo.SEPARATOR);
            writer.write("**********************");
            writer.write(BlockInfo.SEPARATOR);
            writer.write(BlockInfo.TIME_FORMATTER.format(time) + "(write log time)");
            writer.write(BlockInfo.SEPARATOR);
            writer.write(BlockInfo.SEPARATOR);
            writer.write(str);
            writer.write(BlockInfo.SEPARATOR);

            writer.flush();
            writer.close();
            writer = null;

        } catch (Throwable t) {
            Log.e(TAG, "save: ", t);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "save: ", e);
            }
        }
        return path;
    }

    static File detectedBlockDirectory() {
        File directory = new File(BlockCanaryInternals.config().getSavePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public static File[] getLogFiles() {
        File f = detectedBlockDirectory();
        if (f.exists() && f.isDirectory()) {
            return f.listFiles();
        }
        return null;
    }

}
