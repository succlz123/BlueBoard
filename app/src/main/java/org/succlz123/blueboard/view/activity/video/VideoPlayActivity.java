package org.succlz123.blueboard.view.activity.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.squareup.okhttp.ResponseBody;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.api.acfun.NewAcApi;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;
import org.succlz123.blueboard.utils.GlobalUtils;
import org.succlz123.blueboard.utils.danmaku.DanmakuHelper;
import org.succlz123.blueboard.view.base.BaseActivity;
import org.succlz123.okplayer.view.OkVideoView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
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

    public static void startActivity(Context activity, String videoId, String sourceId, String sourceType) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra(AcString.VIDEO_ID, videoId);
        intent.putExtra(AcString.SOURCE_ID, sourceId);
        intent.putExtra(AcString.SOURCE_TYPE, sourceType);
        activity.startActivity(intent);
    }

//    @Bind(R.id.buffer)
//    VideoView mOkVideoView;

//    @Bind(R.id.probar)
//    ProgressBar pb;
//
//    @Bind(R.id.download_rate)
//    TextView downloadRateView;
//
//    @Bind(R.id.load_rate)
//    TextView loadRateView;
//
//    @Bind(R.id.video_play_content)
//    FrameLayout mFrameLayout;

    @Bind(R.id.sv_danmaku)
    IDanmakuView mDanmakuView;

    @Bind(R.id.ok_video_view)
    OkVideoView mOkVideoView;

    private String mVideoId;
    private String mSourceId;
    private String mSourceType;

    private Uri mUri;

    private DanmakuContext mDanmakuContext;

    private BaseDanmakuParser mParser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        initDate();
        initDanmaku();

        if (TextUtils.isEmpty(mSourceType)) {
            GlobalUtils.showToastShort(this, "读取视频源出错");
            return;
        }

        if (TextUtils.equals(mSourceType, "zhuzhan")) {
            videoFormZhuZhan();
        } else {
            GlobalUtils.showToastShort(this, "非主站");
        }


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
                        Collections.reverse(list);
                        mUri = Uri.parse(list.get(0).getUrl().get(0));
                        mOkVideoView.setVideoUri(mUri);
                        Log.w(TAG, "call: " + mUri.toString());
                    }
                });
    }

    private void danmuku(InputStream inputStream) {
        if (mDanmakuView != null) {
            mParser = DanmakuHelper.createParser(inputStream);
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
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
        }
    }

    private void initDate() {
        mVideoId = getIntent().getStringExtra(AcString.VIDEO_ID);
        mSourceId = getIntent().getStringExtra(AcString.SOURCE_ID);
        mSourceType = getIntent().getStringExtra(AcString.SOURCE_TYPE);
    }

    @Override
    public void onNewIntent(Intent intent) {
        mOkVideoView.onNewIntent();
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
    }

//    private class onTitleItemClick implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            int i = v.getId();
//            if (i == org.succlz123.okplayer.R.id.video_back) {
//                onBackPressed();
//
//            } else if (i == org.succlz123.okplayer.R.id.video_hd) {
//
//            } else if (i == org.succlz123.okplayer.R.id.video_danmaku) {
//
//            } else {
//
//            }
//        }
//    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos;
            switch (msg.what) {
                case 1:
//                    mediaController.hide();
//                    titleBar.setVisibility(GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.sv_danmaku)
    void xx(View v) {
//        mMediaController.setVisibility(View.VISIBLE);
        GlobalUtils.showToastShort(this,"啊啊啊");
    }


}

