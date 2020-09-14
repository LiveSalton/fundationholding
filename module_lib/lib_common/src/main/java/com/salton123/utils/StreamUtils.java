package com.salton123.utils;

import android.content.Context;
import android.text.TextUtils;

import com.salton123.log.XLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * @data         2019-08-16
 * @author       newSalton@outlook.com
 * @Description  StreamUtils
 */
public class StreamUtils {


    public static String get(Context context, int id) {
        InputStream stream = context.getResources().openRawResource(id);
        return read(stream);
    }

    public static String read(InputStream stream) {
        return read(stream, "utf-8");
    }

    public static String read(InputStream is, String encode) {
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String intList2String(List<Integer> lists, String split) {
        if (lists == null || lists.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lists.size(); i++) {
            sb.append(lists.get(i));
            if (i != lists.size() - 1) {
                sb.append(split);
            }
        }

        return sb.toString();
    }

    public static List<Integer> string2IntList(String s, String split) {
        List<Integer> resultList = new ArrayList<>();
        if (TextUtils.isEmpty(s)) {
            return resultList;
        }

        String[] results = s.split(split);
        for (String result : results) {
            try {
                resultList.add(Integer.parseInt(result));
            } catch (NumberFormatException e) {
                XLog.e("StreamUtils", e.getMessage());
            }
        }

        return resultList;
    }
}
