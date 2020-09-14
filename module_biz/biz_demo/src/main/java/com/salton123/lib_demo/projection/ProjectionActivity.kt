package com.salton123.lib_demo.projection

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.salton123.log.XLog

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/1 10:20
 * ModifyTime: 10:20
 * Description:
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ProjectionActivity : FragmentActivity() {
    private var mediaProjectionManager: MediaProjectionManager? = null

    companion object {
        private val TAG = "ProjectionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as?
                MediaProjectionManager
        requestProjection()
        XLog.i(TAG, "[onCreate]")
    }

    private fun requestProjection() {
        if (hasRecordScreenFeature()) {
            val intent = mediaProjectionManager?.createScreenCaptureIntent()
            startActivityForResult(intent, ProjectionFragment.REQUEST_CODE)
        } else {
            setResult(ProjectionCode.CODE_GET_PROJECTION_FAILED)
            finish()
        }
    }

    private fun hasRecordScreenFeature(): Boolean {
        val ret = if (mediaProjectionManager == null) {
            false
        } else {
            mediaProjectionManager?.createScreenCaptureIntent()?.let {
                packageManager.resolveActivity(it, PackageManager.MATCH_DEFAULT_ONLY) != null
            }
        }
        XLog.i(TAG, "[hasRecordScreenFeature] $ret")
        return ret ?: false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProjectionFragment.REQUEST_CODE) {
            data?.let {
                mediaProjectionManager?.getMediaProjection(resultCode, it)?.let {
                    ProjectionManager.gMediaProjection = it
                    setResult(ProjectionCode.CODE_GET_PROJECTION_SUCCEED)
                }
            } ?: setResult(ProjectionCode.CODE_GET_PROJECTION_FAILED)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        XLog.i(TAG, "[onDestroy]")
    }
}