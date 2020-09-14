package com.salton123.qa.kit.fileexplorer;

import android.content.Context;
import android.os.Environment;
import android.view.View;

import com.salton123.qa.QualityAssistant;
import com.salton123.qa.kit.Category;
import com.salton123.qa.kit.IKit;
import com.salton123.qa.kit.SimpleKitViewItem;
import com.salton123.qa.ui.fileexplorer.FileExploreActivity;
import com.salton123.qa.ui.fileexplorer.FileInfo;
import com.zhenai.qa.R;

import java.util.ArrayList;

public class FileExplorer implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public View displayItem() {
        return new SimpleKitViewItem(QualityAssistant.application) {

            @Override
            public int getName() {
                return R.string.dk_kit_file_explorer;
            }

            @Override
            public int getIcon() {
                return R.drawable.dk_file_explorer;
            }

            @Override
            public void onClick(Context context) {
                ArrayList<FileInfo> fileInfos = new ArrayList<>();
                fileInfos.add(new FileInfo(context.getFilesDir().getParentFile()));
                fileInfos.add(new FileInfo(context.getExternalCacheDir()));
                fileInfos.add(new FileInfo(context.getExternalFilesDir(null)));
                fileInfos.add(new FileInfo(Environment.getExternalStorageDirectory()));
                FileExploreActivity.Companion.open(context, fileInfos);
            }
        };
    }

    @Override
    public void onAppInit(Context context) {

    }

}
