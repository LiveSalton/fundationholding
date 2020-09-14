package com.mobile.mobileinfo.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobile.mobileinfo.data.Param;
import com.zhenai.qa.R;

import androidx.annotation.NonNull;

public class MobInfoItemAdapter extends BaseQuickAdapter<Param, BaseViewHolder> {

    public MobInfoItemAdapter() {
        super(R.layout.mob_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Param item) {
        helper.setText(R.id.mob_list_tv_key, item.getKey());
        Object value = item.getValue();
        if (value instanceof Drawable) {
            helper.getView(R.id.mob_list_iv_logo).setVisibility(View.VISIBLE);
            helper.setImageDrawable(R.id.mob_list_iv_logo, (Drawable) value);
            helper.setGone(R.id.mob_list_tv_param, false);
        } else {
            helper.setGone(R.id.mob_list_iv_logo, false);
            helper.setGone(R.id.mob_list_tv_param, true);
            helper.setText(R.id.mob_list_tv_param, item.getValue().toString());
        }
    }

}
