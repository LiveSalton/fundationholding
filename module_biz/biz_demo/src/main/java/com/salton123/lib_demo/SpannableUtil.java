package com.salton123.lib_demo;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/7 14:23
 * ModifyTime: 14:23
 * Description:
 */
public class SpannableUtil {
    private SpannableStringBuilder spanBuilder;

    private SpannableUtil() {
        this(new SpannableStringBuilder());
    }

    private SpannableUtil(SpannableStringBuilder spanBuilder) {
        this.spanBuilder = spanBuilder;
    }

    public static SpannableUtil newInstance() {
        return new SpannableUtil();
    }

    public static SpannableUtil newInstance(SpannableStringBuilder builder) {
        return new SpannableUtil(builder);
    }

    public SpannableUtil append(CharSequence s, Object... spans) {
        if (s == null || s.length() == 0) {
            return this;
        }
        spanBuilder.append(s);
        if (spans == null || spans.length == 0) {
            return this;
        }
        for (Object span : spans) {
            spanBuilder.setSpan(span, spanBuilder.length() - s.length(), spanBuilder.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    public SpannableUtil append(char s, Object... spans) {
        spanBuilder.append(s);
        if (spans == null || spans.length == 0) {
            return this;
        }
        for (Object span : spans) {
            spanBuilder.setSpan(span, spanBuilder.length() - 1, spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    public SpannableUtil clearSpans() {
        spanBuilder.clearSpans();
        return this;
    }

    public SpannableUtil clear() {
        spanBuilder.clear();
        return this;
    }

    public SpannableStringBuilder build() {
        return spanBuilder;
    }
}
