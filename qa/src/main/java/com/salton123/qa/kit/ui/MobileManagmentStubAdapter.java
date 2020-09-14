package com.salton123.qa.kit.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.salton123.qa.kit.IKit;
import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/11 19:50
 * ModifyTime: 19:50
 * Description:
 */
public class MobileManagmentStubAdapter extends RecyclerView.Adapter<MobileManagmentStubAdapter.ViewHolder> {
    public List<IKit> getDatas() {
        return mDatas;
    }

    public void setDatas(List<IKit> datas) {
        mDatas = datas;
    }

    public void addData(IKit multiType) {
        this.mDatas.add(multiType);
        int index = mDatas.indexOf(multiType);
        notifyItemInserted(index);
    }

    public void addAll(List<IKit> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    private List<IKit> mDatas = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item_mobile_management_stub, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (viewHolder.rootView.getChildCount() > 0) {
            viewHolder.rootView.removeAllViews();
        }
        viewHolder.rootView.addView(mDatas.get(position).displayItem());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }
}
