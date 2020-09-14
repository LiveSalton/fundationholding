package com.salton123.qa.ui.fileexplorer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.salton123.log.XLog
import com.salton123.qa.constant.BundleKey
import com.salton123.qa.kit.fileexplorer.DatabaseDetailActivity
import com.salton123.qa.kit.fileexplorer.SharedPreferencesViewerActivity
import com.salton123.qa.kit.fileexplorer.TextViewerActivity
import com.salton123.qa.ui.base.QBaseActivity
import com.salton123.qa.ui.mediapreview.MediaEntry
import com.salton123.qa.ui.mediapreview.PreviewActivity
import com.salton123.qa.ui.mediapreview.TYPE_IMAGE
import com.salton123.qa.ui.mediapreview.TYPE_VIDEO
import com.salton123.utils.FileUtil
import com.zhenai.qa.R
import kotlinx.android.synthetic.main.adapter_item_tool_holder.*
import java.io.File
import java.util.ArrayList

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/28 11:48
 * ModifyTime: 11:48
 * Description:
 */
class FileExploreActivity : QBaseActivity() {
    private lateinit var mFileInfoAdapter: FileInfoAdapter
    private var mCurDir: File? = null
    private fun initFileInfoList() {
        recyclerView.layoutManager = LinearLayoutManager(activity())
        mFileInfoAdapter = FileInfoAdapter(activity())
        mFileInfoAdapter.setOnViewClickListener { _, fileInfo ->
            if (fileInfo.file.isFile) {
                val bundle = Bundle()
                bundle.putSerializable(BundleKey.FILE_KEY, fileInfo.file)
                when {
                    FileUtil.isImage(fileInfo.file) || FileUtil.isVideo(fileInfo.file) -> {
                        val targetFile = fileInfo.file.parentFile.listFiles { dir, name ->
                            XLog.i(TAG, "dir:" + dir.absolutePath)
                            FileUtil.isImage(name) || FileUtil.isVideo(name)
                        }.map {
                            MediaEntry(if (FileUtil.isImage(it.name)) TYPE_IMAGE else TYPE_VIDEO, it.absolutePath,
                                    it.absolutePath, it.absolutePath)
                        }
                     
                        PreviewActivity.startActivity(activity(), targetFile,
                                targetFile.indexOf(targetFile.find { it.mediaUrl == fileInfo.file.absolutePath }))
                    }
                    FileUtil.isDB(fileInfo.file) -> openActivity(
                            DatabaseDetailActivity::class.java, bundle)
                    FileUtil.isSp(fileInfo.file) -> openActivity(
                            SharedPreferencesViewerActivity::class.java, bundle)
                    else -> openActivity(TextViewerActivity::class.java, bundle)
                }
            } else {
                mCurDir = fileInfo.file
                mCurDir?.let {
                    setAdapterData(getFileInfos(it))
                    titleText = it.name
                } ?: resetText()
            }
        }
        mFileInfoAdapter.setOnViewLongClickListener { _, fileInfo ->
            val dialog = FileExplorerChooseDialog(fileInfo.file, null)
            dialog.setOnButtonClickListener(object : FileExplorerChooseDialog.OnButtonClickListener {
                override fun onDeleteClick(dialog: FileExplorerChooseDialog) {
                    FileUtil.deleteDirectory(fileInfo.file)
                    dialog.dismiss()
                    mCurDir?.let {
                        setAdapterData(getFileInfos(it))
                        titleText = it.name
                    } ?: resetText()
                }

                override fun onShareClick(dialog: FileExplorerChooseDialog) {
                    FileUtil.systemShare(activity(), fileInfo.file)
                    dialog.dismiss()
                }
            })
            showDialog(dialog)
            true
        }
        setAdapterData(mFileInfos)
        recyclerView.adapter = mFileInfoAdapter
    }

    private fun getFileInfos(dir: File): List<FileInfo> {
        val fileInfos = ArrayList<FileInfo>()
        if (dir.listFiles() == null) {
            return fileInfos
        }
        for (file in dir.listFiles()) {
            val fileInfo = FileInfo(file)
            fileInfos.add(fileInfo)
        }
        return fileInfos
    }

    override fun getLayout(): Int {
        return R.layout.common_item_recyclerview
    }

    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        if (mFileInfos.isNullOrEmpty()) {
            finish()
            return
        }
        mCurDir = null
        initFileInfoList()
        titleText = getString(R.string.dk_kit_file_explorer)
    }

    override fun onBackPressedSupport() {
        if (mCurDir == null) {
            super.onBackPressedSupport()
            return
        }
        if (isRootFile()) {
            titleText = getString(R.string.dk_kit_file_explorer)
            setAdapterData(mFileInfos)
            mCurDir = null
        } else {
            mCurDir = mCurDir?.parentFile
            mCurDir?.let {
                setAdapterData(getFileInfos(it))
                titleText = it.name
            } ?: resetText()
        }
    }

    private fun resetText() {
        titleText = ""
    }

    private fun setAdapterData(fileInfos: List<FileInfo>) {
        if (fileInfos.isEmpty()) {
            mFileInfoAdapter.clear()
        } else {
            mFileInfoAdapter.setData(fileInfos)
        }
    }

    private fun isRootFile(): Boolean {
        return if (mCurDir == null) {
            false
        } else {
            return mFileInfos.find { it.file.absolutePath == mCurDir?.absolutePath } != null
        }
    }

    companion object {
        private val TAG = "FileExploreActivity"
        private var mFileInfos: MutableList<FileInfo> = mutableListOf()
        fun open(context: Context, fileInfos: MutableList<FileInfo>) {
            this.mFileInfos = fileInfos
            val intent = Intent(context, FileExploreActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}