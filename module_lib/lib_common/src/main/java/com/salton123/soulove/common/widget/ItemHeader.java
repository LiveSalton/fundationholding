package com.salton123.soulove.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.soulove.common.R;


public class ItemHeader extends FrameLayout {
    private String mTitle;
    private String mRightText;
    TextView tvTitle;
    TextView tvRightText;
    ImageView ivArrow;

    public ItemHeader(@NonNull Context context) {
        this(context,null);
    }

    public ItemHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.ItemHeader,defStyleAttr,0);
        getAttrs(typedArray);
        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.common_widget_item_header,this);

        tvTitle=findViewById(R.id.tv_title);
        tvRightText=findViewById(R.id.tv_right_text);
        ivArrow=findViewById(R.id.iv_arrow);



        tvTitle.setText(mTitle);
        tvRightText.setText(mRightText);

    }

    public void setTitle(String title) {
       mTitle=title;
       tvTitle.setText(title);
    }

    private void getAttrs(TypedArray typedArray) {
        mTitle=typedArray.getString(R.styleable.ItemHeader_ih_title);
        mRightText=typedArray.getString(R.styleable.ItemHeader_ih_right_text);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        tvRightText.setOnClickListener(l);
        ivArrow.setOnClickListener(l);
    }
}
