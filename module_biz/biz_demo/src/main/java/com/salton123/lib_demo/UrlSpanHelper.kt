package com.salton123.lib_demo

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import com.hjq.toast.ToastUtils
import com.salton123.lib_demo.span.LinkClickSpan
import java.util.regex.Matcher

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/10 20:02
 * ModifyTime: 20:02
 * Description:
 */
object UrlSpanHelper {
    fun identifyUrl(spannableStr: SpannableStringBuilder): SpannableStringBuilder {
        val r = Patterns.WEB_URL
        val m: Matcher
        m = r.matcher(spannableStr)
        while (m.find()) {
            if (m.start() < m.end()) {
                spannableStr.setSpan(
                        LinkClickSpan(
                                m.group(),
                                m.group()
                        ) { _, url, _ ->
                            if (!TextUtils.isEmpty(url)) {
                                ToastUtils.show(url)
                            }
                        },
                        m.start(), m.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableStr
    }

    fun hasNetUrlHead(url: String): Boolean {
        return !TextUtils.isEmpty(url) && (url.startsWith("http://") ||
                url.startsWith("https://") ||
                url.startsWith("ftp://"))
    }
}
