package com.salton123.qa.kit.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.utils.CommonUtils;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/11 19:50
 * ModifyTime: 19:50
 * Description:
 */
public class MobileManagmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<IMultiType> getDatas() {
        return mDatas;
    }

    public void setDatas(List<IMultiType> datas) {
        mDatas = datas;
    }

    public void addData(IMultiType multiType) {
        this.mDatas.add(multiType);
        int index = mDatas.indexOf(multiType);
        notifyItemInserted(index);
    }

    public void addAll(List<IMultiType> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    private List<IMultiType> mDatas = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == IMultiType.TYPE_TOOL) {
            return new ToolViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_item_tool_holder, viewGroup, false));
        } else if (viewType == IMultiType.TYPE_HEADER) {
            return new HeaderVH(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_item_confession_wall_header, viewGroup, false));
        } else {
            return new FooterVH(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_item_confession_wall_footer, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        IMultiType iMultiType = mDatas.get(position);
        if (iMultiType instanceof ToolType && viewHolder instanceof ToolViewHolder) {
            ((ToolViewHolder) viewHolder).slove((ToolType) iMultiType);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ToolViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;
        private TextView tvTitle, tvTitleSub;
        private MobileManagmentStubAdapter mMobileManagmentStubAdapter = new MobileManagmentStubAdapter();

        public ToolViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 4));
            mRecyclerView.setAdapter(mMobileManagmentStubAdapter);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTitleSub = itemView.findViewById(R.id.tvTitleSub);
        }

        public void slove(ToolType toolType) {
            tvTitle.setText(toolType.title);
            tvTitleSub.setText("| " + toolType.subTitle);
            mMobileManagmentStubAdapter.addAll(toolType.mKits);
        }
    }

    static class HeaderVH extends RecyclerView.ViewHolder {
        private TextView tvCurAtyName;

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            tvCurAtyName = itemView.findViewById(R.id.tvCurAtyName);
            tvCurAtyName.setText("(" + CommonUtils.getTopActivity() + ")");
        }
    }

    static class FooterVH extends RecyclerView.ViewHolder {

        public FooterVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
