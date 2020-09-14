package com.salton123.qa.kit.fileexplorer;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.format.draw.FastTextDrawFormat;
import com.bin.david.form.data.table.ArrayTableData;
import com.salton123.qa.constant.BundleKey;
import com.salton123.qa.ui.base.QBaseActivity;
import com.salton123.utils.DatabaseUtil;
import com.zhenai.qa.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseDetailActivity extends QBaseActivity {

    private SmartTable smartTable;
    private ListView tableListView;

    @Override
    public int getLayout() {
        return R.layout.dk_fragment_db_detail;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initViewAndData() {
        super.initViewAndData();
        setTitleText(getString(R.string.dk_kit_db_detail));
        Bundle data = getIntent().getExtras();
        SQLiteDatabase sqLiteDatabase = null;
        List<String> tableNames = new ArrayList<>();
        if (data != null) {
            File mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
            String path = mFile.getPath();
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
            tableNames = DatabaseUtil.queryTableName(sqLiteDatabase);
        }
        smartTable = f(R.id.smartTable);
        // FontStyle fontStyle =
        //         new FontStyle(activity(), 15, ContextCompat.getColor(activity(), R.color.dk_color_000000));
        // TableConfig.setVerticalPadding(10).setHorizontalPadding(10);
        // TableConfig.columnTitleStyle = fontStyle;
        smartTable.setZoom(true, 2f, 0.4f);


        tableListView = f(R.id.lv_table_name);
        tableListView.setAdapter(new DBListAdapter(activity(), tableNames));
        final List<String> finalStrings = tableNames;
        final SQLiteDatabase finalSqLiteDatabase = sqLiteDatabase;
        tableListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectTableName = finalStrings.get(position);
            String[][] data1 = DatabaseUtil.queryAll(finalSqLiteDatabase, finalStrings.get(position));
            String[] titleName = DatabaseUtil.queryTableColumnName(finalSqLiteDatabase, selectTableName);
            if (smartTable.getTableData() != null) {
                smartTable.getTableData().clear();
            }
            smartTable.setTableData(ArrayTableData.create(selectTableName, titleName, data1, new FastTextDrawFormat<>()));
            smartTable.getMatrixHelper().reset();
            tableListView.setVisibility(View.GONE);
            smartTable.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (smartTable.getVisibility() == View.VISIBLE) {
            smartTable.setVisibility(View.GONE);
            tableListView.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }
}