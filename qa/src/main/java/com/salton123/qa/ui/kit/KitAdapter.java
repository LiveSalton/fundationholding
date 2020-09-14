package com.salton123.qa.ui.kit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhenai.qa.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KitAdapter extends RecyclerView.Adapter<KitAdapter.ViewHolder> {

    private List<KitItem> mKitItems = new ArrayList<>();

    @NonNull
    @Override
    public KitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(View.inflate(viewGroup.getContext(), R.layout.adapter_item_view_kit, null));
    }

    @Override
    public void onBindViewHolder(@NonNull KitAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder.rootView.getChildCount() > 0) {
            viewHolder.rootView.removeAllViews();
        }
        viewHolder.rootView.addView(mKitItems.get(position).kit.displayItem());
    }

    @Override
    public int getItemCount() {
        return mKitItems.size();
    }

    public void setData(List<KitItem> kitItems) {
        this.mKitItems = kitItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }

}
