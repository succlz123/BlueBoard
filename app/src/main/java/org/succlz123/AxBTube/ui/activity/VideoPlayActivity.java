package org.succlz123.AxBTube.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentVideo;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/8/4.
 */
public class VideoPlayActivity extends BaseActivity implements OnInfoListener, OnBufferingUpdateListener {

    public static void startActivity(Context activity, String videoId, String danmakuId, String sourceId, String sourceType) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra(AcString.VIDEO_ID, videoId);
        intent.putExtra(AcString.DANMAKU_ID, danmakuId);
        intent.putExtra(AcString.SOURCE_ID, sourceId);
        intent.putExtra(AcString.SOURCE_TYPE, sourceType);
        activity.startActivity(intent);
    }

    private String mVideoId;
    private String mDanmakuId;
    private String mSourceId;
    private String mSourceType;

    private boolean mIsVitamioReady;

    @Bind(R.id.buffer)
    VideoView mVideoView;

    @Bind(R.id.probar)
    ProgressBar pb;

    @Bind(R.id.download_rate)
    TextView downloadRateView;

    @Bind(R.id.load_rate)
    TextView loadRateView;

    @Bind(R.id.video_play_content)
    FrameLayout mFrameLayout;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //检查播放器有没有初始化
        if (!Vitamio.isInitialized(VideoPlayActivity.this)) {
            mIsVitamioReady = Vitamio.initialize(this, this.getResources().getIdentifier("libarm", "raw", this.getPackageName()));
        } else {
            mIsVitamioReady = true;
        }
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        //初始化传递过来的参数,如视频id
        initDate();

        if (mSourceType != null && mIsVitamioReady) {
            if (TextUtils.equals(mSourceType, "letv")) {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.LETV_URL_BASE).build();
                AcApi.getAcContentVideo acContentVideo = restAdapter.create(AcApi.getAcContentVideo.class);
                acContentVideo.onContentResult(AcApi.getAcContentVideoUrl(mSourceId), new Callback<AcContentVideo>() {

                    @Override
                    public void success(AcContentVideo acContentVideo, Response response) {
                        String base64Url = acContentVideo.getData().getVideo_list().getVideo_4().getMain_url();
                        String path = GlobalUtils.decodeByBase64(base64Url);
                        setVideoView(path);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else if (TextUtils.equals(mSourceType, "youku")) {

            } else if (TextUtils.equals(mSourceType, "tudou")) {

            } else if (TextUtils.equals(mSourceType, "qq")) {

            } else if (TextUtils.equals(mSourceType, "sina")) {

            }
        }

    }

    private void setVideoView(String path) {
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
        MediaController mediaController = new MyMediaController(VideoPlayActivity.this);
        mVideoView.setMediaController(mediaController);
        mVideoView.requestFocus();
        mVideoView.setKeepScreenOn(true);
        //在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。
        mVideoView.setOnInfoListener(VideoPlayActivity.this);
        //在网络视频流缓冲变化时调用。
        mVideoView.setOnBufferingUpdateListener(VideoPlayActivity.this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    private void initDate() {
        mVideoId = getIntent().getStringExtra(AcString.VIDEO_ID);
        mDanmakuId = getIntent().getStringExtra(AcString.DANMAKU_ID);
        mSourceId = getIntent().getStringExtra(AcString.SOURCE_ID);
        mSourceType = getIntent().getStringExtra(AcString.SOURCE_TYPE);
    }

    private class MyMediaController extends MediaController {

        public MyMediaController(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyMediaController(Context context) {
            super(context);
        }

        @Override
        protected View makeControllerView() {
            LayoutInflater inflater = GlobalUtils.getLayoutInflater(VideoPlayActivity.this);
            View view = inflater.inflate(R.layout.video_play_media_controller, mFrameLayout, false);

            return view;
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.GONE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }
}
