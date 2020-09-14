package com.salton123.qa.ui.kit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.qa.kit.Category;
import com.salton123.qa.ui.widget.recyclerview.AbsRecyclerAdapter;
import com.salton123.qa.ui.widget.recyclerview.AbsViewBinder;
import com.zhenai.qa.R;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wanglikun on 2018/11/28.
 */

public class GroupKitAdapter extends AbsRecyclerAdapter<AbsViewBinder<List<KitItem>>, List<KitItem>> {

    public GroupKitAdapter(Context context) {
        super(context);
    }

    @Override
    protected AbsViewBinder<List<KitItem>> createViewHolder(View view, int viewType) {
        return new GroupKitViewHolder(view);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_group_kit, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).get(0).kit.getCategory();
    }

    public class CloseKitViewHolder extends AbsViewBinder<List<KitItem>> {
        public CloseKitViewHolder(View view) {
            super(view);
        }

        @Override
        protected void getViews() {

        }

        @Override
        public void bind(List<KitItem> kitItems) {

        }

        @Override
        protected void onViewClick(View view, List<KitItem> data) {
            super.onViewClick(view, data);
            onDismiss();
        }
    }

    public void onDismiss() {

    }

    public class GroupKitViewHolder extends AbsViewBinder<List<KitItem>> {
        private TextView name;
        private RecyclerView kitContainer;
        private KitAdapter kitAdapter;

        public GroupKitViewHolder(View view) {
            super(view);
        }

        @Override
        protected void getViews() {
            name = getView(R.id.name);
            kitContainer = getView(R.id.group_kit_container);
        }

        @Override
        public void bind(final List<KitItem> kitItems) {
            switch (kitItems.get(0).kit.getCategory()) {
                case Category.BIZ: {
                    name.setText(R.string.dk_category_biz);
                    break;
                }
                case Category.PERFORMANCE: {
                    name.setText(R.string.dk_category_performance);
                    break;
                }
                case Category.TOOLS: {
                    name.setText(R.string.dk_category_tools);
                    break;
                }
                case Category.UI: {
                    name.setText(R.string.dk_category_ui);
                    break;
                }
                default:
                    break;
            }
            kitContainer.setLayoutManager(new GridLayoutManager(getContext(), 4));
            kitAdapter = new KitAdapter();
            kitAdapter.setData(kitItems);
            kitContainer.setAdapter(kitAdapter);
        }
    }
}
