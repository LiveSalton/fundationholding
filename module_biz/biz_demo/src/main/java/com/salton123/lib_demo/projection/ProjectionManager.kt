package com.salton123.lib_demo.projection

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.os.Build
import android.os.Environment
import cc.suitalk.ipcinvoker.IPCAsyncInvokeTask
import cc.suitalk.ipcinvoker.IPCInvokeCallback
import cc.suitalk.ipcinvoker.IPCInvoker
import cc.suitalk.ipcinvoker.IPCTask
import cc.suitalk.ipcinvoker.activate.DefaultInitDelegate
import cc.suitalk.ipcinvoker.activate.IPCInvokerInitializer
import cc.suitalk.ipcinvoker.type.IPCBoolean
import com.hjq.toast.ToastUtils
import com.salton123.app.BaseAppDelegate
import com.salton123.lib_demo.projection.task.ForegroundNotificationTask
import com.salton123.log.XLog
import com.salton123.soulove.lib_demo.BuildConfig
import java.io.File

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/6/1 10:57
 * ModifyTime: 10:57
 * Description:
 */
object ProjectionManager {
    var gMediaProjection: MediaProjection? = null
    const val TAG = "ProjectionManager"
    val PROJECTION_PROCESS_NAME =
            "${BaseAppDelegate.getInstance<Application>().packageName}${BuildConfig.PROCESS_NAME}"

    fun setup(context: Application) {
        IPCInvoker.setup(context, object : DefaultInitDelegate() {
            override fun onAttachServiceInfo(initializer: IPCInvokerInitializer) {
                initializer.setDebugger { com.salton123.soulove.common.BuildConfig.APP_DEVELOP }
                initializer.addIPCService(PROJECTION_PROCESS_NAME, ProjectionService::class.java)
            }
        })
    }

    private fun toNotification(isNotify: Boolean) {
        IPCInvoker.invokeAsync(PROJECTION_PROCESS_NAME, IPCBoolean(isNotify), ForegroundNotificationTask::class.java,
                { data -> XLog.i(TAG, "${data?.value}") })
    }

    private var mediaRecorder: MediaRecorder? = null
    private var virtualDisplay: VirtualDisplay? = null
    private val width = 720
    private val height = 1280
    var saveFile = File(Environment.getExternalStorageDirectory(),
            System.currentTimeMillis().toString() + ".mp4")

    fun toProjection(isStart: Boolean) {
        toNotification(isStart)
        IPCTask.create(PROJECTION_PROCESS_NAME)
                .timeout(10)
                .async(AsyncInvokeTask::class.java)
                .data(isStart)
                .callback(true) { data -> /// callback on UI Thread
                    XLog.i(TAG, "invokeAsync result : $data")
                }.invoke()
    }

    class AsyncInvokeTask : IPCAsyncInvokeTask<Boolean, Boolean> {
        override fun invoke(data: Boolean?, callback: IPCInvokeCallback<Boolean>?) {
            val ret = if (data!!) {
                gMediaProjection?.let {
                    saveFile = File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis().toString() + ".mp4")
//                    mediaRecorder?.stop()
                    mediaRecorder?.release()
                    mediaRecorder = MediaRecorder()
                    mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                    mediaRecorder?.setVideoSource(MediaRecorder.VideoSource.SURFACE)
                    mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    mediaRecorder?.setOutputFile(saveFile.absolutePath)
                    mediaRecorder?.setVideoSize(width, height)
                    mediaRecorder?.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                    mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    mediaRecorder?.setVideoEncodingBitRate(5 * 1024 * 1024)
                    mediaRecorder?.setVideoFrameRate(30)
                    try {
                        mediaRecorder?.prepare()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        XLog.i(TAG, "$e")
                    }
                    //开始录制屏幕
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        virtualDisplay = gMediaProjection?.createVirtualDisplay("record", width, height, 320,
                                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder?.surface,
                                null, null)
                        mediaRecorder?.start()
                    }
                    ToastUtils.show("开始录屏：$saveFile")
                    true
                } ?: false
            } else {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                virtualDisplay?.release()
                ToastUtils.show("保存成功：$saveFile")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gMediaProjection?.stop()
                }
                true
            }
            callback?.onCallback(ret)
        }
    }

    fun reqeustProjection(attachHost: Activity, requestCode: Int) {
        val intent = Intent(attachHost, ProjectionActivity::class.java)
        attachHost.startActivityForResult(intent, requestCode)
    }
}