package com.salton123.lib_demo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Patterns
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.toast.ToastUtils
import com.salton123.lib_demo.span.LinkClickSpan
import com.salton123.log.XLog
import com.salton123.soulove.common.Constants
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fm_hello.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/7 15:19
 * ModifyTime: 15:19
 * Description:
 */
@Route(path = Constants.Router.Test.F_SPAN)
class HelloFragment : BaseFragment() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        tvHello.setOnClickListener {
            XLog.i("HelloFragment", "url:${Patterns.WEB_URL}")

        }
        XLog.i("HelloFragment", "url:${Patterns.WEB_URL}")
        tvHello.text = identifyUrl(SpannableStringBuilder("测www.baidu.com nihao www.so.com"))
//        tvHello.text = "测www.baidu.com nihao www.so.com"
        tvHello.movementMethod = LinkMovementMethod.getInstance()
    }

    fun identifyUrl(spannableStr: SpannableStringBuilder): SpannableStringBuilder {
//        val r = Patterns.WEB_URL
        val r = Pattern.compile("^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$")
        val m: Matcher
        m = r.matcher(spannableStr)
        //匹配成功
        while (m.find()) {
            //得到网址数m.group()
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

    override fun getLayout(): Int = R.layout.fm_hello
}