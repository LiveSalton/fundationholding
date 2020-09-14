package com.salton123.infopanel

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

import com.salton123.infopanel.entity.Info

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/9 10:30
 * ModifyTime: 10:30
 * Description:
 */
class UsageInfoView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), IInfoView {
    override fun updateInfo(info: Info) {
        text = info.processMemory + "|" + info.cpuUsage
    }
}
