package org.succlz123.AxBTube.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentVideo;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
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

    private String videoId;
    private String danmakuId;
    private String sourceId;
    private String sourceType;



    private Uri uri;
    private VideoView mVideoView;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.videobuffer);

        videoId = getIntent().getStringExtra(AcString.VIDEO_ID);
        danmakuId = getIntent().getStringExtra(AcString.DANMAKU_ID);
        sourceId = getIntent().getStringExtra(AcString.SOURCE_ID);
        sourceType = getIntent().getStringExtra(AcString.SOURCE_TYPE);


        if (sourceType != null) {
            if (TextUtils.equals(sourceType, "letv")) {
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.LETV_URL_BASE).build();
                AcApi.getAcContentVideo acContentVideo = restAdapter.create(AcApi.getAcContentVideo.class);
                acContentVideo.onContentResult(AcApi.getAcContentVideoUrl(sourceId), new Callback<AcContentVideo>() {

                    @Override
                    public void success(AcContentVideo acContentVideo, Response response) {
                        String base64 = acContentVideo.getData().getVideo_list().getVideo_1().getMain_url();
                        byte[] result = Base64.decode(base64, Base64.DEFAULT);
                        String path = new String(result);
                        uri = Uri.parse(path);

                        mVideoView.setVideoURI(uri);
                        mVideoView.setMediaController(new MediaController(VideoPlayActivity.this));
                        mVideoView.requestFocus();
                        mVideoView.setOnInfoListener(VideoPlayActivity.this);
                        mVideoView.setOnBufferingUpdateListener(VideoPlayActivity.this);
                        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                // optional need Vitamio 4.0
                                mediaPlayer.setPlaybackSpeed(1.0f);
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        }

        mVideoView = (VideoView) findViewById(R.id.buffer);
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
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
