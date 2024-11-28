package com.instagram.downloader.Views.Activities;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.instagram.downloader.R;
import com.instagram.downloader.Utils.Widget.VideoControllerView;
import com.instagram.downloader.databinding.ActivityPlayerBinding;

import java.io.IOException;

public class VideoPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {

    ActivityPlayerBinding binding;
    private static boolean FullScreen = true;
    static MediaPlayer mediaPlayer;
    ImageView back;
    VideoControllerView videoControllerView;
    String filepath;
    String fromStreaming;

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.video_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.videoSurface.getHolder().addCallback(this);
        Bundle extras = getIntent().getExtras();
        this.filepath = extras.getString(getString(R.string.pathvideo));
        this.fromStreaming = extras.getString(getString(R.string.fromstreaming));
        mediaPlayer = new MediaPlayer();
        this.videoControllerView = new VideoControllerView(this);
        getWindow().setFlags(1024, 1024);
        try {
            if (this.fromStreaming == null) {
                mediaPlayer.setDataSource(this.filepath);
            } else {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(this.filepath));
            }
            mediaPlayer.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }

        this.back = binding.imageViewClose;
        binding.imageViewClose.setOnClickListener(view -> {
            VideoPlayerActivity.mediaPlayer.stop();
            VideoPlayerActivity.this.onBackPressed();
        });
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.videoControllerView.show();
        return false;
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.prepareAsync();
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.videoControllerView.setMediaPlayer(this);
        float videoWidth = ((float) VideoPlayerActivity.mediaPlayer.getVideoWidth()) / ((float) VideoPlayerActivity.mediaPlayer.getVideoHeight());
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        float f = (float) width;
        float f2 = (float) height;
        ViewGroup.LayoutParams layoutParams = this.binding.videoSurface.getLayoutParams();
        if (videoWidth > f / f2) {
            layoutParams.width = width;
            layoutParams.height = (int) (f / videoWidth);
        } else {
            layoutParams.width = (int) (videoWidth * f2);
            layoutParams.height = height;
        }
        this.binding.videoSurface.setLayoutParams(layoutParams);
        this.videoControllerView.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        VideoPlayerActivity.mediaPlayer.start();
    }

    @Override
    public int getCurrentPosition() {
        MediaPlayer mediaPlayer = VideoPlayerActivity.mediaPlayer;
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        MediaPlayer mediaPlayer = VideoPlayerActivity.mediaPlayer;
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public boolean isPlaying() {
        MediaPlayer mediaPlayer = VideoPlayerActivity.mediaPlayer;
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        MediaPlayer mediaPlayer = VideoPlayerActivity.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            VideoPlayerActivity.mediaPlayer = null;
        }
        finish();
        return true;
    }

    @Override
    public boolean isFullScreen() {
        if (FullScreen) {
            Log.v(getString(R.string.fullscreen), getString(R.string.set_icon_full_screen));
            return false;
        }
        Log.v(getString(R.string.fullscreen), getString(R.string.set_icon_small_full_screen));
        return true;
    }

    @Override
    public void toggleFullScreen() {
        setFullScreen(isFullScreen());
    }

    @SuppressLint("WrongConstant")
    public void setFullScreen(boolean z) {
        if (FullScreen) {
            setRequestedOrientation(0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            int i2 = displayMetrics.widthPixels;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.binding.videoSurface.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = i;
            layoutParams.setMargins(0, 0, 0, 0);
            FullScreen = false;
            return;
        }
        setRequestedOrientation(1);
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics2);
        int height = ((FrameLayout) findViewById(R.id.videoSurfaceContainer)).getHeight();
        int i3 = displayMetrics2.widthPixels;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.binding.videoSurface.getLayoutParams();
        layoutParams2.width = i3;
        layoutParams2.height = height;
        layoutParams2.setMargins(0, 0, 0, 0);
        FullScreen = true;
    }
}