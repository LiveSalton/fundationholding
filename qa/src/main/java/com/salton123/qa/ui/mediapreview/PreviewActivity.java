package com.salton123.qa.ui.mediapreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.hjq.toast.ToastUtils;
import com.salton123.GlideApp;
import com.zhenai.qa.R;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PreviewActivity extends Activity {
    private static final String TAG = "PreviewActivity";
    private SparseArray<View> views;
    private View currentVideoView;
    private ViewPager viewPager;
    private MMPagerAdapter adapter;

    private static int currentPosition = -1;
    private static List<MediaEntry> entries;
    private boolean pendingPreviewInitialMedia;

    private class MMPagerAdapter extends PagerAdapter {
        private List<MediaEntry> entries;

        public MMPagerAdapter(List<MediaEntry> entries) {
            this.entries = entries;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view;
            MediaEntry entry = entries.get(position);
            if (entry.isVideoType()) {
                view = LayoutInflater.from(PreviewActivity.this).inflate(R.layout.im_preview_video, null);
            } else {
                view = LayoutInflater.from(PreviewActivity.this).inflate(R.layout.im_preview_photo, null);
            }
            container.addView(view);
            views.put(position % 3, view);
            if (pendingPreviewInitialMedia) {
                initViewData(view, entry);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return entries == null ? 0 : entries.size();
        }

        public MediaEntry getEntry(int position) {
            return entries.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            View view = views.get(position % 3);
            if (view == null) {
                return;
            }
            if (currentVideoView != null) {
                resetVideoView(currentVideoView);
                currentVideoView = null;
            }
            MediaEntry entry = adapter.getEntry(position);
            initViewData(view, entry);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void resetVideoView(View view) {
        ImageView imageView = view.findViewById(R.id.imageView);
        ProgressBar loadingProgressBar = view.findViewById(R.id.loading);
        ImageView playButton = view.findViewById(R.id.btnVideo);
        VideoView videoView = view.findViewById(R.id.videoView);
        imageView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
        playButton.setVisibility(View.VISIBLE);
        videoView.stopPlayback();
        videoView.setVisibility(View.GONE);
    }

    private void initVideo(View view, MediaEntry entry) {
        MediaController mediaController = new MediaController(view.getContext());
        ImageView imageView = view.findViewById(R.id.imageView);
        GlideApp.with(imageView).load(entry.getPhotoUrl())
                .thumbnail(Glide.with(imageView).load(entry.getThumbnailUrl())).into(imageView);
        VideoView videoView = view.findViewById(R.id.videoView);
        videoView.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);
        ProgressBar loadingProgressBar = view.findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.GONE);
        findViewById(R.id.tv_back).setOnClickListener(v -> finish());
        ImageView btn = view.findViewById(R.id.btnVideo);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(entry.getMediaUrl())) {
                return;
            }
            btn.setVisibility(View.INVISIBLE);
            Uri uri = Uri.parse(entry.getMediaUrl());
            if (UriUtil.isNetworkUri(uri)) {
                playVideo(view, entry.getMediaUrl());
            } else {    //认为是本地地址
                if (new File(entry.getMediaUrl()).exists()) {
                    playVideo(view, entry.getMediaUrl());
                } else {
                    ToastUtils.show("文件不存在");
                }
            }
        });
    }

    private void initImage(View view, MediaEntry entry) {
        PhotoView ivPreview = view.findViewById(R.id.ivPreview);
        GlideApp.with(ivPreview).load(entry.getPhotoUrl())
                .thumbnail(Glide.with(ivPreview).load(entry.getThumbnailUrl())).into(ivPreview);
        ivPreview.setOnViewTapListener((view1, x, y) -> finish());
        view.findViewById(R.id.tv_back).setOnClickListener(v -> finish());
    }

    private void initViewData(View view, MediaEntry entry) {
        if (entry.isVideoType()) {
            initVideo(view, entry);
        } else {
            initImage(view, entry);
        }
    }

    private void playVideo(View view, String videoUrl) {
        VideoView videoView = view.findViewById(R.id.videoView);
        videoView.setVisibility(View.GONE);
        ImageView btn = view.findViewById(R.id.btnVideo);
        ImageView imageView = view.findViewById(R.id.imageView);
        btn.setVisibility(View.GONE);
        ProgressBar loadingProgressBar = view.findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.VISIBLE);
        currentVideoView = view;
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(videoUrl);
        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(PreviewActivity.this, "play error", Toast.LENGTH_SHORT).show();
            resetVideoView(view);
            return true;
        });
        videoView.setOnPreparedListener(mp -> {
            loadingProgressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        });
        videoView.setOnCompletionListener(mp -> resetVideoView(view));
        videoView.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_preview);
        views = new SparseArray<>(3);
        viewPager = findViewById(R.id.viewPager);
        adapter = new MMPagerAdapter(entries);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(pageChangeListener);
        if (currentPosition == 0) {
            viewPager.post(() -> pageChangeListener.onPageSelected(0));
        } else {
            viewPager.setCurrentItem(currentPosition);
            pendingPreviewInitialMedia = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        entries = null;
    }

    public static void startActivity(Context context, List<MediaEntry> entries, int current) {
        if (entries == null || entries.isEmpty()) {
            Log.w(PreviewActivity.class.getSimpleName(), "message is null or empty");
            return;
        }
        PreviewActivity.entries = entries;
        PreviewActivity.currentPosition = current;
        Intent intent = new Intent(context, PreviewActivity.class);
        context.startActivity(intent);
    }
}
