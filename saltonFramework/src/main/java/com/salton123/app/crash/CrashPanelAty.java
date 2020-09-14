package com.salton123.app.crash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.saltonframework.R;
import com.salton123.util.FileUtils;

/**
 * User: newSalton@outlook.com
 * Date: 2019-05-09 21:10
 * ModifyTime: 21:10
 * Description:
 */
public class CrashPanelAty extends Activity {
    public static final String FLAG_INFO = "info";
    private TextView tvCrashInfo;
    private ImageView btnShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salton_aty_crash_panel);
        tvCrashInfo = findViewById(R.id.tvCrashInfo);
        btnShare = findViewById(R.id.btnShare);
        updateLogic();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateLogic();
    }

    private void updateLogic() {
        String info = getIntent().getStringExtra(FLAG_INFO);
        StringBuilder stringBuilder = FileUtils.readFile(info, "utf-8");
        tvCrashInfo.setText("" + stringBuilder);
        Log.i("newsalton", "CrashPanelAty:" + stringBuilder);
        btnShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share(CrashPanelAty.this, tvCrashInfo.getText().toString());
            }
        });
    }

    private void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "崩溃");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, "分享"));
    }
}
