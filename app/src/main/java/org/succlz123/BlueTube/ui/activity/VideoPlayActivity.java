package org.succlz123.bluetube.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.newacfun.NewAcVideo;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.helper.acfun.NewAcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.support.utils.OkHttpClientManager;
import org.succlz123.bluetube.ui.activity.acfun.CompressionTools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

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

    @Bind(R.id.sv_danmaku)
    IDanmakuView mDanmakuView;

    private String mVideoId;
    private String mDanmakuId;
    private String mSourceId;
    private String mSourceType;
    private boolean mIsVitamioReady;
    private BaseDanmakuParser mParser;

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

                Call<NewAcVideo> call = RetrofitConfig.getNewAcVideo().onResult(mDanmakuId);

                call.enqueue(new Callback<NewAcVideo>() {
                    @Override
                    public void onResponse(Response<NewAcVideo> response) {
                        NewAcVideo acVideo = response.body();

                        if (acVideo != null && acVideo.getData() != null) {
                            final String url = acVideo.getData().getFiles().get(0).getUrl().get(0);

//                            setVideoView(url);


                            Request.Builder builder = new Request.Builder();
                            builder.url("http://danmu.aixifan.com/" + mDanmakuId)
                                    .addHeader(NewAcString.APP_VERSION, NewAcString.APP_VERSION_400)
                                    .addHeader(NewAcString.DEVICETYPE, NewAcString.DEVICETYPE_1)
                                    .addHeader(NewAcString.MARKET, NewAcString.MARKET_PORTAL)
                                    .addHeader(NewAcString.PRODUCTID, NewAcString.PRODUCTID_2000)
                                    .addHeader(NewAcString.RESOLUTION, NewAcString.RESOLUTION_WIDTH_HEIGHT)
                                    .addHeader(NewAcString.UUID, NewAcString.UUID_X);

                            Request request = builder.build();

                            com.squareup.okhttp.Call call = OkHttpClientManager.getInstance().newCall(request);
                            call.enqueue(new com.squareup.okhttp.Callback() {
                                             @Override
                                             public void onFailure(Request request, IOException e) {

                                             }

                                             @Override
                                             public void onResponse(final com.squareup.okhttp.Response response) throws IOException {

                                                 InputStream stream = null;
                                                 try {
                                                     stream = new ByteArrayInputStream(CompressionTools.decompressXML(response.body().bytes()));
                                                 } catch (DataFormatException e) {
                                                     e.printStackTrace();
                                                 }

                                                 runOnUiThread(new Runnable() {
                                                     @Override
                                                     public void run() {
//                                                         setVideoView(url, stream);
                                                     }
                                                 });

                                             }
                                         }
                            );

//                            Call<NewAcDanmuku> danmakuCall = RetrofitConfig.getNewAcDanmaku().onResult(mDanmakuId);
//                            danmakuCall.enqueue(new Callback<NewAcDanmuku>() {
//
//                                @Override
//                                public void onResponse(Response<NewAcDanmuku> response) {
//                                    try {
//                                        setVideoView(url, response.raw().body().byteStream());
//                                    } catch (IOException e) {
////
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                    t.toString();
//                                }
//                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            } else {
//                RetrofitConfig.getAcContentHtml5Video().onContentResult(AcApi.getAcContentHtml5VideoUrl("2440995"), new Callback<AcGetH5ByVid>() {
//
//                    @Override
//                    public void onResponse(Response<AcGetH5ByVid> response) {
//                        if (response.body().isSuccess() && response.body().getResult().getC20().getFiles().size() != 0) {
//                            setVideoView(response.body().getResult().getC20().getFiles().get(0).getUrl(), null);
//                        } else {
//                            if (!VideoPlayActivity.this.isDestroyed()) {
//                                GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "不支持的视频源");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        if (!VideoPlayActivity.this.isDestroyed()) {
//                            GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络异常");
//                        }
//                    }
//                });
            }
//            else if (TextUtils.equals(mSourceType, "youku")) {
//                GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "优酷 不支持的视频源");
//            } else if (TextUtils.equals(mSourceType, "tudou")) {
//                GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "土豆 不支持的视频源");
//            } else if (TextUtils.equals(mSourceType, "qq")) {
//                GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "企鹅 不支持的视频源");
//            } else if (TextUtils.equals(mSourceType, "sina")) {
//                GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "渣浪 不支持的视频源");
//            }
        }
    }

    private void setVideoView(String path, InputStream in) {

        Uri uri = Uri.parse(path);
//        mVideoView.setVideoURI(uri);
        final MediaController mediaController = new MyMediaController(VideoPlayActivity.this);
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
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示3行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

//        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
//        DanmakuGlobalConfig.DEFAULT
//                .setDanmakuStyle(DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3)
//                .setDuplicateMergingEnabled(false)
//                .setScrollSpeedFactor(1.2f)
//                .setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer()); // 图文混排使用SpannedCacheStuffer
////        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
////                .setMaximumLines(maxLinesPair)
////                .preventOverlapping(overlappingEnablePair);
//
//        if (mDanmakuView != null) {
        mParser = createParser(in);
//            mDanmakuView.setCallback(new DrawHandler.Callback() {
//                @Override
//                public void updateTimer(DanmakuTimer timer) {
//                }
//
//                @Override
//                public void prepared() {
//                    mDanmakuView.start();
//                }
//            });
//            mDanmakuView.prepare(mParser);
//            mDanmakuView.showFPS(true);
//            mDanmakuView.enableDanmakuDrawingCache(true);
//            ((View) mDanmakuView).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    mediaController.setVisibility(View.VISIBLE);
//                }
//            });
//        }
//
//        if (mVideoView != null) {
//            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
        mVideoView.setVideoURI(uri);
//            mDanmakuView.start();
//        }
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

    private BaseDanmakuParser createParser(InputStream stream) {

//        if (stream == null) {
//            return new BaseDanmakuParser() {
//
//                @Override
//                protected Danmakus parse() {
//                    return new Danmakus();
//                }
//            };
//        }
//
//        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_ACFUN);
////        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
//
//        try {
//            loader.load(stream);
//        } catch (IllegalDataException e) {
//            e.printStackTrace();
//        }
//        BaseDanmakuParser parser = new AcFunDanmakuParser();
////        BaseDanmakuParser parser = new BiliDanmukuParser();
//
//        IDataSource<?> dataSource = loader.getDataSource();
//        parser.load(dataSource);
//
//        return parser;

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);

        return parser;
    }

    private static class BackgroundCacheStuffer extends SpannedCacheStuffer {
        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        final Paint paint = new Paint();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint) {
            danmaku.padding = 10;  // 在背景绘制模式下增加padding
            super.measure(danmaku, paint);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            paint.setColor(0x8125309b);
            canvas.drawRect(left + 2, top + 2, left + danmaku.paintWidth - 2, top + danmaku.paintHeight - 2, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
