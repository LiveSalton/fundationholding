package com.salton123.qa.kit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.salton123.qa.kit.ui.FooterType;
import com.salton123.qa.kit.ui.HeaderType;
import com.salton123.qa.kit.ui.IMultiType;
import com.salton123.qa.kit.ui.MobileManagmentAdapter;
import com.salton123.qa.kit.ui.ToolType;
import com.salton123.ui.base.BaseActivity;
import com.salton123.util.ScreenUtils;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/11 19:41
 * ModifyTime: 19:41
 * Description:
 */
public class ToolKitsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MobileManagmentAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ToolKitsActivity.class));
    }

    @Override
    public int getLayout() {
        return R.layout.comp_tool_kits;
    }

    @Override
    public void initVariable(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initViewAndData() {
        mLinearLayoutManager = new LinearLayoutManager(activity());
        mRecyclerView = f(R.id.rvMobileManagment);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MobileManagmentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        if (mRecyclerView.getItemDecorationCount() == 0) {
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                                           @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);
                    int margin = ScreenUtils.dp2px(10f);
                    int margin15dp = ScreenUtils.dp2px(15f);
                    if (position == 0) {
                        outRect.set(margin15dp, margin, margin15dp, margin);
                    } else {
                        outRect.set(margin15dp, 0, margin15dp, margin);
                    }
                }
            });
        }
        List<IMultiType> multiTypes = new ArrayList<>();
        multiTypes.add(new HeaderType());
        multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.TOOLS), "常用工具", "手到拿来，方便快捷"));
        multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.PERFORMANCE), "性能监控", "多维度监控，挖掘性能极限"));
        multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.UI), "视觉工具", "一眼看出界面问题"));
        if (KitManager.getInstance().getKits(Category.BIZ).size() > 0) {
            multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.BIZ), "系统工具", "高效系统级服务"));
        }
        multiTypes.add(new FooterType());
        mAdapter.addAll(multiTypes);
    }
}
