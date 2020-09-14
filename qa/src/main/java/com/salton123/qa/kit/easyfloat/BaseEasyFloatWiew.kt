package com.salton123.qa.kit.easyfloat

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnFloatCallbacks
import com.salton123.log.XLog
import com.salton123.utils.ActivityUtils

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/20 14:28
 * ModifyTime: 14:28
 * Description:
 */
abstract class BaseEasyFloatWiew : OnFloatCallbacks {
    private lateinit var builder: EasyFloat.Builder

    abstract fun getLayout(): Int    //当前布局

    init {
        builder = EasyFloat.Builder(getContext())
                .setLayout(getLayout())
                .registerCallbacks(this)
    }

    public fun getContext(): Activity {
        return ActivityUtils.getCurrentResumedActivity()
    }

    abstract fun initViewAndData(view: View?)

    fun toShow() {
        builder.show()
    }

    fun setShowPattern(showPattern: ShowPattern) {
        builder.setShowPattern(showPattern)
    }

    fun setDragEnable(dragEnable: Boolean) {
        builder.setDragEnable(dragEnable)
    }

    fun setSidePattern(sidePattern: SidePattern) {
        builder.setSidePattern(sidePattern)
    }

    @JvmOverloads
    fun setGravity(gravity: Int, offsetX: Int = 0, offsetY: Int = 0) {
        builder.setGravity(gravity, offsetX, offsetY)
    }

    fun setLocation(x: Int, y: Int) {
        builder.setLocation(x, y)
    }

    fun setTag(floatTag: String?) {
        builder.setTag(floatTag)
    }

    override fun createdResult(b: Boolean, s: String?, view: View?) {
        if (b) {
            initViewAndData(view)
        } else {
            XLog.e(this@BaseEasyFloatWiew, "create float window failed")
        }
    }

    override fun dismiss() {
    }

    override fun drag(view: View, motionEvent: MotionEvent) {
    }

    override fun dragEnd(view: View) {
    }

    override fun hide(view: View) {
    }

    override fun show(view: View) {
    }

    override fun touchEvent(view: View, motionEvent: MotionEvent) {
    }
}
