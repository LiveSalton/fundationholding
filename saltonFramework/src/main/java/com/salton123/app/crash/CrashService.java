package com.salton123.app.crash;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

/**
 * User: newSalton@outlook.com
 * Date: 2019-05-09 21:40
 * ModifyTime: 21:40
 * Description:奇怪的魅族的手机崩溃的时候直接调用startActivity会出现卡死，因此放到service里
 */
public class CrashService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CrashService(String name) {
        super(name);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public CrashService() {
        super("CrashService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String crashInfo = intent.getStringExtra(CrashPanelAty.FLAG_INFO);
        Intent crashIntent = new Intent(getApplication(), CrashPanelAty.class);
        crashIntent.putExtra(CrashPanelAty.FLAG_INFO, crashInfo);
        crashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(crashIntent);
    }
}

