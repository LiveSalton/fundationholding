package com.salton123.qa.kit.anr;

import android.content.Context;
import android.widget.Toast;

import com.salton123.qa.QualityAssistant;
import com.salton123.util.StringUtils;

import org.json.JSONObject;

import java.util.Map;

import xcrash.ICrashCallback;
import xcrash.TombstoneParser;
import xcrash.XCrash;

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/8 14:48
 * ModifyTime: 14:48
 * Description:静态内部类实现单例，既实现赖加载又实现线程安全
 */
public class AnrManager implements ICrashCallback {
    private AnrManager() {
    }

    private static class Holder {
        private static AnrManager INSTANCE = new AnrManager();
    }

    public static AnrManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context, String appVersion) {
        XCrash.init(context, new XCrash.InitParameters()
                .setAppVersion(appVersion)
                .setJavaRethrow(false)
                .disableJavaCrashHandler()
                .disableNativeCrashHandler()
                .setNativeRethrow(false)
                .setAnrRethrow(true)
                .setAnrLogCountMax(10)
                .setAnrCallback(this)
        );
    }

    @Override
    public void onCrash(String logPath, String emergency) throws Exception {
        if (!StringUtils.isBlank(logPath)) {
            Toast.makeText(QualityAssistant.application, "发现Anr:" + logPath, Toast.LENGTH_LONG).show();
        }
        Map<String, String> info = TombstoneParser.parse(logPath, emergency);
        String result = new JSONObject(info).toString();
        //todo update anr info to server
    }
}
