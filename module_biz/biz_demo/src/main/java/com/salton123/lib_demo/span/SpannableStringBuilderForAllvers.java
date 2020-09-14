package com.salton123.lib_demo.span;

import android.text.SpannableStringBuilder;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/10 19:53
 * ModifyTime: 19:53
 * Description:
 */
public class SpannableStringBuilderForAllvers extends SpannableStringBuilder {

    public SpannableStringBuilderForAllvers() {
        super("");
    }

    public SpannableStringBuilderForAllvers(CharSequence text) {
        super(text, 0, text.length());
    }

    public SpannableStringBuilderForAllvers(CharSequence text, int start, int end) {
        super(text, start, end);
    }

    @Override
    public SpannableStringBuilder append(CharSequence text) {
        if (text == null) {
            return this;
        }
        int length = length();
        return (SpannableStringBuilderForAllvers) replace(length, length, text, 0, text.length());
    }

    /**
     * 该方法在原API里面只支持API21或者以上，这里适应低版本
     */
    public SpannableStringBuilderForAllvers append(CharSequence text, Object what, int flags) {
        if (text == null) {
            return this;
        }
        int start = length();
        append(text);
        setSpan(what, start, length(), flags);
        return this;
    }
}

