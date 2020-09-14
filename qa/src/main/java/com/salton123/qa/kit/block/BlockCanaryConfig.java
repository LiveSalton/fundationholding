package com.salton123.qa.kit.block;

import android.content.Context;

import com.salton123.log.Utils;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/18 17:32
 * ModifyTime: 17:32
 * Description:
 */
public class BlockCanaryConfig {
    private static final int DEFAULT_THRESHOLD = 1000;

    private String qualifier;
    private int blockThreshold = DEFAULT_THRESHOLD;
    private int dumpInterval = DEFAULT_THRESHOLD;
    private boolean stopWhenDebugging = true;
    private Context context;
    private String savePath = Utils.getDefaultPath();  //保存位置
    private int logDeleteDelayDay = 10;  //默认10天后删除

    public Context provideContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String provideQualifier() {
        return qualifier;
    }

    public BlockCanaryConfig setQualifier(String qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public int provideBlockThreshold() {
        return blockThreshold;
    }

    public BlockCanaryConfig setBlockThreshold(int blockThreshold) {
        this.blockThreshold = blockThreshold;
        return this;
    }

    public int provideDumpInterval() {
        return dumpInterval;
    }

    public BlockCanaryConfig setDumpInterval(int dumpInterval) {
        this.dumpInterval = dumpInterval;
        return this;
    }

    public boolean stopWhenDebugging() {
        return stopWhenDebugging;
    }

    public BlockCanaryConfig setStopWhenDebugging(boolean stopWhenDebugging) {
        this.stopWhenDebugging = stopWhenDebugging;
        return this;
    }

    public String getSavePath() {
        return savePath;
    }

    public BlockCanaryConfig setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public int getLogDeleteDelayDay() {
        return logDeleteDelayDay;
    }

    public BlockCanaryConfig setLogDeleteDelayDay(int logDeleteDelayDay) {
        this.logDeleteDelayDay = logDeleteDelayDay;
        return this;
    }

}
