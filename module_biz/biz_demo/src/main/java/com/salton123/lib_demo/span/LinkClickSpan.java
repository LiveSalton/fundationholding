package com.salton123.lib_demo.span;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/10 19:53
 * ModifyTime: 19:53
 * Description:
 */
public class LinkClickSpan extends ClickableSpan {
    private int mColor = Color.parseColor("#FF5722");
    private String mUrl;
    private String mContent;
    UrlSpanClickListener mClickListener;

    public LinkClickSpan(String url, String content, UrlSpanClickListener onClickListener) {
        super();
        mUrl = url;
        mContent = content;
        mClickListener = onClickListener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mColor);
        ds.linkColor = mColor;
        ds.setUnderlineText(true);//设置是否下划线
        ds.clearShadowLayer();
    }

    @Override
    public void onClick(View widget) {
        if (mClickListener != null) {
            mClickListener.onClick(widget, mUrl, mContent);
        }
    }
}