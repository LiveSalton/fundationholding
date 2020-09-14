package com.salton123.utils

import android.widget.ImageView
import android.widget.TextView
import com.hjq.toast.ToastUtils
import com.salton123.app.BaseApplication
import com.salton123.soulove.common.R
import com.salton123.util.PreferencesUtils
import com.salton123.xmly.TingAppHelper
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl

/**
 * User: newSalton@outlook.com
 * Date: 2019/10/12 18:16
 * ModifyTime: 18:16
 * Description:
 */
object SouloveUtil {
    const val PLAY_MODE = "PLAY_MODE"
    fun changePlayMode(ivIcon: ImageView?, tvMode: TextView?) {
        when (TingAppHelper.INSTANCE.playerManager.playMode) {
            XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM -> {
                tvMode?.text = "单曲循环"
                ivIcon?.setImageResource(R.drawable.ic_repeat_one)
                TingAppHelper.INSTANCE.playerManager.playMode =
                        XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP
                ToastUtils.show("单曲循环")
                PreferencesUtils.putInt(BaseApplication.sInstance, PLAY_MODE,
                        XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP.ordinal)
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP -> {
                tvMode?.text = "列表循环"
                ToastUtils.show("列表循环")
                ivIcon?.setImageResource(R.drawable.ic_repeat)
                TingAppHelper.INSTANCE.playerManager.playMode =
                        XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP
                PreferencesUtils.putInt(BaseApplication.sInstance, PLAY_MODE,
                        XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP.ordinal)
            }
            else -> {
                tvMode?.text = "随机播放"
                ToastUtils.show("随机播放")
                ivIcon?.setImageResource(R.drawable.ic_shuffle)
                TingAppHelper.INSTANCE.playerManager.playMode =
                        XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM
                PreferencesUtils.putInt(BaseApplication.sInstance, PLAY_MODE,
                        XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM.ordinal)
            }
        }
    }

    fun updatePlayMode(ivIcon: ImageView?, tvMode: TextView?) {
        when (TingAppHelper.INSTANCE.playerManager.playMode) {
            XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM -> {
                tvMode?.text = "随机播放"
                ivIcon?.setImageResource(R.drawable.ic_repeat_one)
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP -> {
                tvMode?.text = "单曲循环"
                ivIcon?.setImageResource(R.drawable.ic_repeat)
            }
            else -> {
                tvMode?.text = "列表循环"
                ivIcon?.setImageResource(R.drawable.ic_shuffle)
            }
        }
    }

    fun getPlayMode(): String {
        when (TingAppHelper.INSTANCE.playerManager.playMode) {
            XmPlayListControl.PlayMode.PLAY_MODEL_LIST -> {
                return "列表播放"
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE -> {
                return "单曲播放"
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP -> {
                return "单曲循环"
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP -> {
                return "列表循环"
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM -> {
                return "随机播放"
            }
        }
    }

    fun getPlayModeInt(): XmPlayListControl.PlayMode {
        val mode = PreferencesUtils.getInt(BaseApplication.sInstance, PLAY_MODE,
                XmPlayListControl.PlayMode.PLAY_MODEL_LIST.ordinal)
        when (mode) {
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE.ordinal -> {
                return XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP.ordinal -> {
                return XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP.ordinal -> {
                return XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM.ordinal -> {
                return XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM
            }
            else -> {
                return XmPlayListControl.PlayMode.PLAY_MODEL_LIST
            }
        }
    }
}