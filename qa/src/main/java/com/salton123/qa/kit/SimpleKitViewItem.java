package com.salton123.qa.kit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhenai.qa.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/18 11:07
 * ModifyTime: 11:07
 * Description:
 */
public abstract class SimpleKitViewItem extends FrameLayout {
    private ImageView ivIcon;
    private TextView tvName;
    private LinearLayout llRoot;

    public SimpleKitViewItem(@NonNull Context context) {
        this(context, null);
    }

    public SimpleKitViewItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleKitViewItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_stub_simple_kit_item, this);
        ivIcon = findViewById(R.id.ivIcon);
        tvName = findViewById(R.id.tvName);
        llRoot = findViewById(R.id.llRoot);
        ivIcon.setImageResource(getIcon());
        tvName.setText(getName());
        llRoot.setOnClickListener(v -> SimpleKitViewItem.this.onClick(v.getContext()));
    }

    public int getName() {
        return 0;
    }

    public int getIcon() {
        return 0;
    }

    public void onClick(Context context) {
    }
}
