package com.salton123.qa.kit.crash;

import android.content.Context;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.fileexplorer.FileExploreActivity;
import com.salton123.qa.ui.fileexplorer.FileInfo;
import com.zhenai.qa.R;

import java.io.File;
import java.util.ArrayList;

public class CrashCapture implements IKit {
    @Override
    public int getCategory() {
        return Category.PERFORMANCE;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_crash;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_crash_catch;
            }

            @Override
            public void onClick(Context context) {
                ArrayList<FileInfo> fileInfos = new ArrayList<>();
                fileInfos.add(new FileInfo(new File(XCrashManager.INSTANCE.getCrashPath())));
                FileExploreActivity.Companion.open(context, fileInfos);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }
}