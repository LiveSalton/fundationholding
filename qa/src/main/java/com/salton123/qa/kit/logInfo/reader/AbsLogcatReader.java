package com.salton123.qa.kit.logInfo.reader;

public abstract class AbsLogcatReader implements LogcatReader {

    protected boolean recordingMode;

    public AbsLogcatReader(boolean recordingMode) {
        this.recordingMode = recordingMode;
    }

    public boolean isRecordingMode() {
        return recordingMode;
    }
}
