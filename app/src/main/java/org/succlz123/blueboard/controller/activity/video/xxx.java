package org.succlz123.blueboard.controller.activity.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;

import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.text.Cue;

import org.succlz123.okplayer.OkPlayer;
import org.succlz123.okplayer.listener.CaptionListener;
import org.succlz123.okplayer.listener.OkPlayerListener;
import org.succlz123.okplayer.utils.OkPlayerUtils;

import java.util.List;

/**
 * Created by succlz123 on 15/12/1.
 */
public class xxx extends FrameLayout implements
        OkPlayerListener,
        CaptionListener,
        SurfaceHolder.Callback,
        AudioCapabilitiesReceiver.Listener {

    private AspectRatioFrameLayout videoFrame;
    private SurfaceView surfaceView;

    private AudioCapabilitiesReceiver audioCapabilitiesReceiver;

    private MediaController mediaController;
    private OkPlayer player;

    private Uri uri;

    private long playerPosition;

    public xxx(Context context) {
        super(context);
        setup(context, null);
    }

    public xxx(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);

    }

    public xxx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public xxx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }

    private void setup(Context context, AttributeSet attrs) {
        if (context == null) {
            return;
        }
        initView(context);
        readAttributes(context, attrs);
    }

    /**
     * 初始化view
     */
    private void initView(Context context) {
        View.inflate(context, org.succlz123.okplayer.R.layout.ok_video_view, this);

        videoFrame = (AspectRatioFrameLayout) findViewById(org.succlz123.okplayer.R.id.video_frame);
        surfaceView = (SurfaceView) findViewById(org.succlz123.okplayer.R.id.surface_view);

        if (surfaceView != null) {
            initExoPlayer();
        }
    }

    /**
     * 初始化播放器
     */
    private void initExoPlayer() {
        audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(getContext().getApplicationContext(), this);
        audioCapabilitiesReceiver.register();
        player = new OkPlayer(null);

        player.addListener(this);
        player.setId3MetadataListener(null);

        player.setSurface(surfaceView.getHolder().getSurface());
        surfaceView.getHolder().addCallback(this);
    }

    /**
     * 读取自定义配置
     */
    private void readAttributes(Context context, AttributeSet attrs) {
        if (attrs == null || isInEditMode()) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, org.succlz123.okplayer.R.styleable.OkExoPlayerVideoView);
        if (typedArray == null) {
            return;
        }

        boolean enableDefaultControls = typedArray.getBoolean(org.succlz123.okplayer.R.styleable.OkExoPlayerVideoView_ControlsEnabled, false);
        setDefaultControlsEnabled(enableDefaultControls);

        typedArray.recycle();
    }

    public void setDefaultControlsEnabled(boolean enabled) {
        if (enabled) {
            mediaController = new MediaController(getContext());
            mediaController.setAnchorView(videoFrame);
            mediaController.setMediaPlayer(player.getPlayerControl());
            mediaController.setEnabled(true);
        }

        CustomTouchListener listener = new CustomTouchListener(getContext());
        setOnTouchListener(enabled ? listener : null);
    }

    public Uri getVideoUri() {
        return uri;
    }

    /**
     * 设置视频uri
     */
    public void setVideoUri(Uri videoUri) {
        uri = videoUri;

        if (uri == null) {
            return;
        }

        if (player == null) {
            initExoPlayer();
        }

        player.replaceRenderBuilder(OkPlayerUtils.getRendererBuilder(getContext(), uri, OkPlayerUtils.TYPE_OTHER));
        player.prepare();
        player.pushSurface(true);
        player.seekTo(0);
        player.setPlayWhenReady(true);
    }

    public void onNewIntent() {
        release();
    }

    public void onResume(Uri uri) {
        if (player == null) {
            setVideoUri(uri);
        } else {
            player.setPlayWhenReady(true);
        }
    }

    public void onPause() {
        pause();
    }

    public void onDestroy() {
        release();
    }

    /**
     * 暂停
     */
    public void pause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    /**
     * 释放
     */
    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
        playerPosition = 0;

        if (audioCapabilitiesReceiver != null) {
            audioCapabilitiesReceiver.unregister();
            audioCapabilitiesReceiver = null;
        }
    }

    /**
     * {@link OkPlayerListener}
     */
    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
        }
//        playerStateTextView.setText(text);
//        updateButtonVisibilities();
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
//        shutterView.setVisibility(View.GONE);
        //视频比例改变时,同时改变videoFrame的高宽比例
//        videoFrame.setAspectRatio(
//                height == 0 ? 1 : (width * pixelWidthHeightRatio) / height);
    }

    /**
     * {@link SurfaceHolder.Callback}
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (player != null) {
            player.setSurface(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player != null) {
            player.blockingClearSurface();
        }
    }

    /**
     * {@link CaptionListener}
     */
    @Override
    public void onCues(List<Cue> cues) {

    }

    /**
     * {@link AudioCapabilitiesReceiver.Listener}
     */
    @Override
    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (player == null) {
            return;
        }
        boolean backgrounded = player.getBackgrounded();
        boolean playWhenReady = player.getPlayWhenReady();
//        releasePlayer();
//        preparePlayer(playWhenReady);
        player.setBackgrounded(backgrounded);
    }

    /**
     * 手势监听
     */
    public class CustomTouchListener extends GestureDetector.SimpleOnGestureListener implements OnTouchListener {
        private GestureDetector gestureDetector;

        public CustomTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            gestureDetector.onTouchEvent(ev);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent ev) {
            if (mediaController == null) {
                return false;
            }

            if (mediaController.isShowing()) {
                mediaController.hide();
            } else {
                mediaController.show(5000);
            }
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}