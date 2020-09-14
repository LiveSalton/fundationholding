package com.salton123.lib_demo.projection

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.salton123.log.XLog

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/30 16:10
 * ModifyTime: 16:10
 * Description: help you to request relative permissions and mediaprojection as simple
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MediaProjectionDelegate(var attchHost: FragmentActivity) {
    private var mediaProjectionManager: MediaProjectionManager? = null
    private var projectionFragment: Lazy<ProjectionFragment>
    val TAG = "MediaProjectionDelegate"

    init {
        mediaProjectionManager = attchHost.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as?
                MediaProjectionManager
        projectionFragment = getLazySingleton(attchHost.supportFragmentManager)
    }

    private var mMediaProjection: MediaProjection? = null
    fun requestProjection(callback: (ret: Int, mediaProjection: MediaProjection?) -> Unit) {
        if (hasRecordScreenFeature()) {
            projectionFragment.get()
                    ?.requestRecordScreen(mediaProjectionManager) { resultCode, data ->
                        mediaProjectionManager?.getMediaProjection(resultCode, data)?.let {
                            mMediaProjection = it
                            callback.invoke(
                                    ProjectionCode.CODE_GET_PROJECTION_SUCCEED, it)
                        } ?: callback.invoke(
                                ProjectionCode.CODE_GET_PROJECTION_FAILED, null)
                    } ?: callback.invoke(
                    ProjectionCode.CODE_GET_PROJECTION_FAILED, null)
        } else {
            callback.invoke(ProjectionCode.CODE_GET_PROJECTION_FAILED, null)
        }
    }

    fun stopProjection() {
        mMediaProjection?.stop()
        mMediaProjection = null
    }

    private fun hasRecordScreenFeature(): Boolean {
        val ret = if (mediaProjectionManager == null) {
            false
        } else {
            val intent = mediaProjectionManager!!.createScreenCaptureIntent()
            attchHost.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null
        }
        XLog.i(TAG, "[hasRecordScreenFeature] $ret")
        return ret
    }

    private fun getLazySingleton(fragmentManager: FragmentManager): Lazy<ProjectionFragment> {
        return object :
                Lazy<ProjectionFragment> {
            private var fragment: ProjectionFragment? = null

            @Synchronized
            override fun get(): ProjectionFragment? {
                if (this.fragment == null) {
                    this.fragment = this@MediaProjectionDelegate.getProjectionFragment(fragmentManager)
                }
                return this.fragment
            }
        }
    }

    private fun getProjectionFragment(fragmentManager: FragmentManager): ProjectionFragment? {
        var fragment: ProjectionFragment? = this.findProjectionFragment(fragmentManager)
        val isNewInstance = fragment == null
        if (isNewInstance) {
            fragment = ProjectionFragment()
            fragmentManager.beginTransaction().add(fragment, TAG).commitNowAllowingStateLoss()
        }
        return fragment
    }

    private fun findProjectionFragment(fragmentManager: FragmentManager): ProjectionFragment? {
        return fragmentManager.findFragmentByTag(TAG) as ProjectionFragment?
    }

    @FunctionalInterface
    interface Lazy<V> {
        fun get(): V?
    }
}