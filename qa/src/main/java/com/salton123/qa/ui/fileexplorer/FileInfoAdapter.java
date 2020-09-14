package com.salton123.qa.ui.fileexplorer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.qa.ui.widget.recyclerview.AbsRecyclerAdapter;
import com.salton123.qa.ui.widget.recyclerview.AbsViewBinder;
import com.salton123.utils.FileUtil;
import com.zhenai.qa.R;

/**
 * Created by wanglikun on 2018/10/16.
 */

public class FileInfoAdapter extends AbsRecyclerAdapter<AbsViewBinder<FileInfo>, FileInfo> {
    private OnViewClickListener mOnViewClickListener;
    private OnViewLongClickListener mOnViewLongClickListener;

    public FileInfoAdapter(Context context) {
        super(context);
    }

    @Override
    protected AbsViewBinder<FileInfo> createViewHolder(View view, int viewType) {
        return new FileInfoViewHolder(view);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_file_info, parent, false);
    }

    public class FileInfoViewHolder extends AbsViewBinder<FileInfo> {
        private TextView mName;
        private ImageView mIcon;
        private ImageView mMoreBtn;

        public FileInfoViewHolder(View view) {
            super(view);
        }

        @Override
        protected void getViews() {
            mName = getView(R.id.name);
            mIcon = getView(R.id.icon);
            mMoreBtn = getView(R.id.more);
        }

        @Override
        public void bind(final FileInfo fileInfo) {
            getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnViewLongClickListener != null && mOnViewLongClickListener.onViewLongClick(v, fileInfo);
                }
            });
            mName.setText(fileInfo.file.getName());
            mName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mName.setMarqueeRepeatLimit(-1);
            mName.requestFocus();
            mName.setSelected(true);
            if (fileInfo.file.isDirectory()) {
                mIcon.setImageResource(R.drawable.dk_dir_icon);
                mMoreBtn.setVisibility(View.VISIBLE);
            } else {
                if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.JPG)) {
                    mIcon.setImageResource(R.drawable.dk_jpg_icon);
                } else if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.TXT)) {
                    mIcon.setImageResource(R.drawable.dk_txt_icon);
                } else if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.DB)) {
                    mIcon.setImageResource(R.drawable.dk_file_db);
                } else {
                    mIcon.setImageResource(R.drawable.dk_file_icon);
                }
                mMoreBtn.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onViewClick(View view, FileInfo data) {
            super.onViewClick(view, data);
            if (mOnViewClickListener != null) {
                mOnViewClickListener.onViewClick(view, data);
            }
        }
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        mOnViewClickListener = onViewClickListener;
    }

    public void setOnViewLongClickListener(OnViewLongClickListener onViewLongClickListener) {
        mOnViewLongClickListener = onViewLongClickListener;
    }

    public interface OnViewClickListener {
        void onViewClick(View v, FileInfo fileInfo);
    }

    public interface OnViewLongClickListener {
        boolean onViewLongClick(View v, FileInfo fileInfo);
    }
}
