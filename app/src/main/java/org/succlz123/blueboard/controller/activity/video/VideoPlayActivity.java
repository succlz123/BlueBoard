package org.succlz123.blueboard.controller.activity.video;

import com.squareup.okhttp.ResponseBody;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.base.BaseActivity;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.api.acfun.NewAcApi;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.SysUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.model.utils.danmaku.DanmakuHelper;
import org.succlz123.okplayer.OkPlayer;
import org.succlz123.okplayer.listener.OkPlayerListener;
import org.succlz123.okplayer.view.OkVideoView;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;
import butterknife.OnTouch;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;
import retrofit.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by succlz123 on 15/8/4.
 */
public class VideoPlayActivity extends BaseActivity {

    public static void newInstance(Context activity, String videoId, String sourceId, String sourceType, String sourceTitle) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra(AcString.VIDEO_ID, videoId);
        intent.putExtra(AcString.SOURCE_ID, sourceId);
        intent.putExtra(AcString.SOURCE_TYPE, sourceType);
        intent.putExtra(AcString.SOURCE_TITLE, sourceTitle);

        activity.startActivity(intent);
    }

    private FrameLayout mFlLoading;
    private FrameLayout mFlDownloadRate;
    private TextView mTvDownloadRate;

    private LinearLayout mLlBrightness;
    private LinearLayout mLlVolume;
    private TextView mTvBrightness;
    private TextView mTvVolume;

    private ImageView mIvHD;
    private ImageView mIvDanmaku;

    private TextView mTvTitle;
    private LinearLayout mTitleBar;

    private ImageView mIvPlay;
    private TextView mTvRate;
    private SeekBar mPbRate;
    private LinearLayout mControllerBar;

    private DanmakuSurfaceView mDanmakuView;
    private OkVideoView mOkVideoView;

    private AudioManager mAudioManager;

    private int GESTURE_FLAG = 0;
    private static final int GESTURE_MODIFY_PROGRESS = 1;
    private static final int GESTURE_MODIFY_BRIGHT = 2;
    private static final int GESTURE_MODIFY_VOLUME = 3;

    private String mVideoId;
    private String mSourceId;
    private String mSourceType;
    private String mSourceTitle;
    private boolean isSeekBarThreadRun = false;
    private boolean mIsPlayOver = false;
    private Uri mUri;

    private boolean mIsDanmakuReday;
    private boolean mIsFirstIn = true;
    private int mCheckCount;

    private DanmakuContext mDanmakuContext;

    private BaseDanmakuParser mParser;
    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        View decorView = getWindow().getDecorView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewUtils.toggleHideyBar(decorView);

        setContentView(R.layout.activity_video_play);

        initDate();
        initView();
        initDanmaku();

        if (mSourceTitle != null) {
            mTvTitle.setText(mSourceTitle);
        }
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this, new CustomTouchListener());

        if (TextUtils.equals(mSourceType, "zhuzhan")) {
            videoFormZhuZhan();
        } else {
            GlobalUtils.showToastShort("读取视频源出错,非主站");
        }
    }

    private void initDate() {
        mVideoId = getIntent().getStringExtra(AcString.VIDEO_ID);
        mSourceId = getIntent().getStringExtra(AcString.SOURCE_ID);
        mSourceType = getIntent().getStringExtra(AcString.SOURCE_TYPE);
        mSourceTitle = getIntent().getStringExtra(AcString.SOURCE_TITLE);
    }

    private void initView() {
        mFlLoading = f(R.id.fl_loading);
        mFlDownloadRate = f(R.id.fl_download_rate);
        mTvDownloadRate = f(R.id.tv_download_rate);
        mLlBrightness = f(R.id.ll_brightness);
        mLlVolume = f(R.id.ll_volume);
        mTvBrightness = f(R.id.tv_brightness);
        mTvVolume = f(R.id.tv_volume);
        mIvHD = f(R.id.video_hd);
        mIvDanmaku = f(R.id.video_danmaku);
        mTvTitle = f(R.id.tv_title);
        mTitleBar = f(R.id.video_title_bar);
        mIvPlay = f(R.id.iv_play);
        mTvRate = f(R.id.tv_rate);
        mPbRate = f(R.id.sb_rate);
        mControllerBar = f(R.id.video_controller_bar);
        mDanmakuView = f(R.id.sv_danmaku);
        mOkVideoView = f(R.id.ok_video_view);
    }

    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        // 滚动弹幕最大显示3行
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), null) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
//        (View)mDanmakuView.
    }

    private void videoFormZhuZhan() {
        final Observable<NewAcVideo> getVideo = NewAcApi.getNewAcVideo().onResult(mVideoId);
        final Observable<Response<ResponseBody>> getDanmaku = NewAcApi.getNewAcDanmaku().onResult(mVideoId);

        getDanmaku.subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<ResponseBody>, Observable<NewAcVideo>>() {
                    @Override
                    public Observable<NewAcVideo> call(Response<ResponseBody> response) {
                        try {
                            danmuku(new BufferedInputStream(response.body().byteStream()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                        }
                        return getVideo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewAcVideo>() {
                    @Override
                    public void call(NewAcVideo newAcVideo) {
                        List<NewAcVideo.DataEntity.FilesEntity> list = newAcVideo.getData().getFiles();
//                        Collections.reverse(list);
                        mUri = Uri.parse(list.get(0).getUrl().get(0));
                        mOkVideoView.setVideoUri(mUri, false);
                        Log.w(TAG, "call: " + mUri.toString());
                    }
                });
    }

    private void danmuku(InputStream inputStream) {
        if (mDanmakuView != null) {
            mParser = DanmakuHelper.createParser(inputStream);
            mDanmakuView.setCallback(new DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void prepared() {
                    mIsDanmakuReday = true;
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
                @Override
                public void onDanmakuClick(BaseDanmaku latest) {
//                    Log.d("DFM", "onDanmakuClick text:" + latest.text);
                }

                @Override
                public void onDanmakuClick(IDanmakus danmakus) {
//                    Log.d("DFM", "onDanmakuClick danmakus size:" + danmakus.size());
                }
            });
            mDanmakuView.prepare(mParser, mDanmakuContext);
            mDanmakuView.enableDanmakuDrawingCache(true);
//            mDanmakuView.showFPS(true);

            mOkVideoView.addListener(new OkPlayerListener() {
                @Override
                public void onStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == OkPlayer.STATE_ENDED) {
                        mIsPlayOver = true;
                        mPbRate.setMax((int) mOkVideoView.getDuration());
                        mPbRate.setProgress((int) mOkVideoView.getDuration());
                        mFlDownloadRate.setVisibility(View.GONE);

                    } else if (playbackState == OkPlayer.STATE_READY) {
                        mFlDownloadRate.setVisibility(View.GONE);

                        if (playWhenReady) {
                            mFlLoading.setVisibility(View.GONE);
                            if (!isSeekBarThreadRun) {
                                onSeekBarRun();
                            }
                        } else {
                            syncVideoAndDanmaku();
                        }
                    } else if (playbackState == OkPlayer.STATE_BUFFERING) {
                        mFlDownloadRate.setVisibility(View.VISIBLE);
                    }
                    Log.w(TAG, "" + playWhenReady + "/" + playbackState);
                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

                }
            });
            mPbRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (mOkVideoView.getPlaybackState() == OkPlayer.STATE_READY) {
                        mOkVideoView.seekTo(seekBar.getProgress());
                        mDanmakuView.seekTo((long) seekBar.getProgress());

                        long duration = mOkVideoView.getDuration();
                        long currentPosition = mOkVideoView.getCurrentPosition();
                        showPlayerTime(duration, currentPosition);
                    }
                }
            });
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        mOkVideoView.onNewIntent();
        mIsDanmakuReday = false;
        mIsFirstIn = true;
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOkVideoView.onResume(mUri);
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOkVideoView.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOkVideoView.onDestroy();
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
        mIsDanmakuReday = false;
        mIsPlayOver = true;
    }

    private void onSeekBarRun() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                isSeekBarThreadRun = true;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDownloadRate.setText(mOkVideoView.getBufferedPercentage() + "%");

                        if (mOkVideoView.getPlaybackState() != OkPlayer.STATE_READY) {
                            return;
                        }

                        long duration = mOkVideoView.getDuration();
                        long currentPosition = mOkVideoView.getCurrentPosition();

                        mPbRate.setMax((int) duration);
                        mPbRate.setProgress((int) currentPosition);

                        showPlayerTime(duration, currentPosition);
                    }
                });
                if (mIsPlayOver) {
                    isSeekBarThreadRun = false;
                    return;
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    private synchronized void showPlayerTime(long duration, long currentPosition) {
        String totalTime = GlobalUtils.getTimeFromMillisecond(duration);
        String currentTime = GlobalUtils.getTimeFromMillisecond(currentPosition);
        mTvRate.setText(currentTime + " / " + totalTime);
    }

    private void syncVideoAndDanmaku() {
        if (mIsFirstIn) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mIsDanmakuReday) {
                        mHandler.sendEmptyMessage(1);
                    } else if (mCheckCount < 3) {
                        mHandler.postDelayed(this, 1000);
                        mCheckCount++;
                    } else {
                        GlobalUtils.showToastShort("弹幕解析失败,请重试");
                    }
                }
            });
            mIsFirstIn = false;
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mDanmakuView.start();
                    mOkVideoView.setPlayWhenReady(true);
                    break;
                default:
                    break;
            }
        }
    };

    @OnTouch(R.id.sv_danmaku)
    boolean onDanmakuViewTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                mLlBrightness.setVisibility(View.GONE);
                mLlVolume.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @OnClick(R.id.video_back)
    void onVideoBack(View v) {
        onBackPressed();
    }

    @OnClick(R.id.video_hd)
    void onVideoHD(View v) {
        GlobalUtils.showToastShort("啊啊啊");
    }

    @OnClick(R.id.video_danmaku)
    void onVideoDanmaku(View v) {
        if (mDanmakuView.isShown()) {
            mDanmakuView.hide();
            GlobalUtils.showToastShort("关闭弹幕");
        } else {
            mDanmakuView.show();
            GlobalUtils.showToastShort("打开弹幕");
        }
    }

    @OnClick(R.id.iv_play)
    void onPlayClick(View v) {
        if (mOkVideoView.getPlaybackState() == OkPlayer.STATE_READY) {
            boolean playWhenReady = mOkVideoView.getPlayWhenReady();
            if (playWhenReady) {
                mOkVideoView.setPlayWhenReady(false);
                mDanmakuView.pause();
                mIvPlay.setSelected(true);
            } else {
                mOkVideoView.setPlayWhenReady(true);
                mDanmakuView.resume();
                mIvPlay.setSelected(false);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public class CustomTouchListener extends GestureDetector.SimpleOnGestureListener {
        private static final int CHANGE_STEP = 3000;
        private int gestureStep;

        public CustomTouchListener() {
            super();
            gestureStep = GlobalUtils.dip2px(VideoPlayActivity.this, 10);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mTitleBar.getVisibility() == View.INVISIBLE && mControllerBar.getVisibility() == View.INVISIBLE) {
                mTitleBar.setVisibility(View.VISIBLE);
                mControllerBar.setVisibility(View.VISIBLE);
            } else {
                mTitleBar.setVisibility(View.INVISIBLE);
                mControllerBar.setVisibility(View.INVISIBLE);
            }
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        //在ACTION_MOVE触发
        //移动的像素
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float oldX = e1.getX();
            float oldY = e1.getY();
            int newY = (int) e2.getY();

            int width = mOkVideoView.getWidth();
            int height = mOkVideoView.getHeight();

            if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                mLlBrightness.setVisibility(View.GONE);
                mLlVolume.setVisibility(View.GONE);
                GESTURE_FLAG = GESTURE_MODIFY_PROGRESS;
            } else {
                if (oldX < width / 2) {
                    //亮度
                    mLlVolume.setVisibility(View.GONE);
                    mLlBrightness.setVisibility(View.VISIBLE);
                    GESTURE_FLAG = GESTURE_MODIFY_BRIGHT;
                } else if (oldX > width / 2) {
                    //音量
                    mLlBrightness.setVisibility(View.GONE);
                    mLlVolume.setVisibility(View.VISIBLE);
                    GESTURE_FLAG = GESTURE_MODIFY_VOLUME;
                }
            }

            if (GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
                long position = mOkVideoView.getCurrentPosition();
                long duration = mOkVideoView.getDuration();

                long changePosition = SysUtils.onProgressSlide(distanceX, distanceY, gestureStep, CHANGE_STEP, position, duration);
//                Log.w(TAG, "onScroll: "+changePosition);
                mOkVideoView.seekTo(changePosition);
                mDanmakuView.seekTo(changePosition);
            } else if (GESTURE_FLAG == GESTURE_MODIFY_BRIGHT) {
                float percent = SysUtils.onBrightnessSlide(VideoPlayActivity.this, (oldY - newY) / width);
                mTvBrightness.setText((int) (percent * 100) + "%");
            } else if (GESTURE_FLAG == GESTURE_MODIFY_VOLUME) {
                int percent = SysUtils.onVolumeSlide(mAudioManager, gestureStep, distanceY, (oldY - newY) / width);
                mTvVolume.setText(String.valueOf(percent) + "%");
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //在ACTION_UP时才会触发
        //坐标轴上的移动速度，像素/秒
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }
}

