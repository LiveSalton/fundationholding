// package com.salton123.qa.kit;
//
// import android.content.Context;
// import android.graphics.Rect;
// import androidx.annotation.NonNull;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
// import android.view.View;
//
// import com.salton123.utils.ActivityUtils;
// import com.salton123.qa.kit.Category;
// import com.salton123.qa.ui.widget.tableview.utils.DensityUtils;
// import com.lxj.xpopup.impl.FullScreenPopupView;
// import com.salton123.qa.kit.ui.FooterType;
// import com.salton123.qa.kit.ui.HeaderType;
// import com.salton123.qa.kit.ui.IMultiType;
// import com.salton123.qa.kit.ui.MobileManagmentAdapter;
// import com.salton123.qa.kit.ui.ToolType;
// import com.wuhenzhizao.titlebar.statusbar.StatusBarUtils;
// import com.zhenai.qa.R;
//
// import java.util.ArrayList;
// import java.util.List;
//
// /**
//  * User: newSalton@outlook.com
//  * Date: 2019/12/31 9:53
//  * ModifyTime: 9:53
//  * Description:
//  */
// public class ToolKitPopup extends FullScreenPopupView {
//     private RecyclerView mRecyclerView;
//     private MobileManagmentAdapter mAdapter;
//     private LinearLayoutManager mLinearLayoutManager;
//
//     public ToolKitPopup(@NonNull Context context) {
//         super(context);
//         // StatusBarUtils.transparentStatusBar(getWi);
//         // StatusBarUtils.setDarkMode(window);
//     }
//
//     @Override
//     protected void onCreate() {
//         super.onCreate();
//         mLinearLayoutManager = new LinearLayoutManager(getContext());
//         mRecyclerView = findViewById(R.id.rvMobileManagment);
//         mRecyclerView.setLayoutManager(mLinearLayoutManager);
//         mRecyclerView.setHasFixedSize(true);
//         mAdapter = new MobileManagmentAdapter();
//         mRecyclerView.setAdapter(mAdapter);
//         if (mRecyclerView.getItemDecorationCount() == 0) {
//             mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//                 @Override
//                 public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                     super.getItemOffsets(outRect, view, parent, state);
//                     int position = parent.getChildAdapterPosition(view);
//                     int margin = DensityUtils.dp2px(view.getContext(), 10f);
//                     int margin15dp = DensityUtils.dp2px(view.getContext(), 15f);
//                     if (position == 0) {
//                         outRect.set(margin15dp, margin, margin15dp, margin);
//                     } else {
//                         outRect.set(margin15dp, 0, margin15dp, margin);
//                     }
//                 }
//             });
//         }
//         List<IMultiType> multiTypes = new ArrayList<>();
//         multiTypes.add(new HeaderType());
//         multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.TOOLS), "常用工具", "手到拿来，方便快捷"));
//         multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.PERFORMANCE), "性能监控", "多维度监控，挖掘性能极限"));
//         multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.UI), "视觉工具", "一眼看出界面问题"));
//         if (KitManager.getInstance().getKits(Category.BIZ).size() > 0) {
//             multiTypes.add(new ToolType(KitManager.getInstance().getKits(Category.BIZ), "系统工具", "高效系统级服务"));
//         }
//         multiTypes.add(new FooterType());
//         mAdapter.addAll(multiTypes);
//     }
//
//     @Override
//     protected int getImplLayoutId() {
//         return R.layout.comp_tool_kits;
//     }
// }
