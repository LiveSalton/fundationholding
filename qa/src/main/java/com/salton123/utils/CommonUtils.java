package com.salton123.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.MemoryFile;
import android.os.Process;
import android.text.TextUtils;

import com.salton123.qa.QualityAssistant;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/11 10:29
 * ModifyTime: 10:29
 * Description:
 */
public class CommonUtils {

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(Reader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the given stream inside a try/catch. Does nothing if stream is null.
     */
    public static void safeClose(Writer out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void safeClose(MemoryFile mf) {
        if (mf != null) {
            mf.close();
        }
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void safeClose(Socket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void safeClose(ServerSocket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * read file
     *
     * @param filePath    文件路径
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    private static Application sApplication = QualityAssistant.application;

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static double getMyProcessMemory() {
        return getMemoryForProcess(Process.myPid()) * 1.0 / 1024;
    }

    // 进程总内存
    private static int getMemoryForProcess(int pid) {
        ActivityManager am = (ActivityManager) getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return 0;
        }
        int[] myMempid = new int[]{pid};
        Debug.MemoryInfo memoryInfo = am.getProcessMemoryInfo(myMempid)[0];
        // 首先用反射拿uss的值
        Class clazz = memoryInfo.getClass();
        try {
            Method getTotalUssMethod = clazz.getDeclaredMethod("getTotalUss", new Class[]{});
            getTotalUssMethod.setAccessible(true);
            Integer totalUss = (Integer) getTotalUssMethod.invoke(memoryInfo, new Object[]{});
            return totalUss;
        } catch (Exception e) {
            // 因为getTotalUss方法是hide的，也许部分手机反射会出问题，所以这里套一个public的获取
            // 经过测试pss 会比uss 高出几M到十M左右
            return memoryInfo.getTotalPss();
        }

    }

    /**
     * 获得栈中最顶层的Activity
     *
     * @return
     */
    public static String getTopActivity() {
        android.app.ActivityManager manager =
                (android.app.ActivityManager) getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            String shortClassName = (runningTaskInfos.get(0).topActivity).getShortClassName();
            int startIndex = shortClassName.lastIndexOf(".") + 1;
            return shortClassName.substring(startIndex) + "";
        } else {
            return "";
        }
    }

    public static double getCpuUsage() {
        java.lang.Process process = null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                process = Runtime.getRuntime().exec("top -n 1 -d 0");
            } else {
                process = Runtime.getRuntime().exec("top -n 1");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int cpuIndex = -1;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                // 找到CPU指标的下表索引，不同的系统版本下标索引不同
                int tempIndex = -1;
                if (line.contains("CPU")) {
                    String[] titles = line.split("\\s+");
                    for (int i = 0; i < titles.length; i++) {
                        if (titles[i].contains("CPU")) {
                            tempIndex = i;
                        }
                    }
                }
                if (tempIndex != -1) {
                    cpuIndex = tempIndex;
                    continue;
                }
                if (line.startsWith(String.valueOf(Process.myPid()))) {
                    if (cpuIndex == -1) {
                        continue;
                    }
                    String[] param = line.split("\\s+");
                    if (param.length <= cpuIndex) {
                        continue;
                    }
                    String cpu = param[cpuIndex];
                    // 有些设备自带了%，需要去除
                    if (cpu.endsWith("%")) {
                        cpu = cpu.substring(0, cpu.lastIndexOf("%"));
                    }
                    try {
                        return Double.parseDouble(cpu) / Runtime.getRuntime().availableProcessors();
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return 0;
    }

    public static Application getApp() {
        return QualityAssistant.application;
    }
}
