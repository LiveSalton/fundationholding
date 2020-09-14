package com.salton123.lib_demo

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/7 12:19
 * ModifyTime: 12:19
 * Description:
 */

object HighlightDemo {

    fun test(): SpannableStringBuilder {
        var bodys = arrayOf(
                HighlightMsgBody("hello-test55", "girgir://Profile/MeEditor?edit=avatar"),
                HighlightMsgBody("hello-test55", "girgir://Profile/MeEditor?edit=tony"))
        val highlightMsg = HighlightMsg("hello#\${highlightBody}# hi #\${highlightBody}#",
                "#\${highlightBody}#",
                bodys)
        var normalText = highlightMsg.normalText
        val highlightReplaceText = highlightMsg.highlightReplaceText
        val highlightMsgBodys = highlightMsg.highlightMsgBodys
        val builder = SpannableUtil.newInstance()
        normalText.split(highlightReplaceText).forEachIndexed { index, subText ->
            builder.append(subText)
            if (index < highlightMsgBodys.size) {
                val item = highlightMsgBodys[index]
                builder.append(item.highlightText,
                        ForegroundColorSpan(Color.parseColor("#FF7000")),
                        object : ClickableSpan() {
                            override fun onClick(widget: View) {
//                        Axis.getService(IUriService::class.java)?.handlerUri(
//                                it.skipLink
//                        )
                                Log.i("HighlightDemo", "${item.skipLink}")
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                ds.isUnderlineText = false
                            }
                        })
            }
        }
        return builder.build()
    }
}