package org.succlz123.blueboard.view.activity.video;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.devbrackets.android.exomedia.EMVideoView;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.api.acfun.NewAcApi;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;
import org.succlz123.blueboard.utils.GlobalUtils;
import org.succlz123.blueboard.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by succlz123 on 15/8/4.
 */
public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {

    public static void startActivity(Context activity, String videoId, String danmakuId, String sourceId, String sourceType) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra(AcString.VIDEO_ID, videoId);
        intent.putExtra(AcString.DANMAKU_ID, danmakuId);
        intent.putExtra(AcString.SOURCE_ID, sourceId);
        intent.putExtra(AcString.SOURCE_TYPE, sourceType);
        activity.startActivity(intent);
    }

//    @Bind(R.id.buffer)
//    VideoView mVideoView;

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

//    @Bind(R.id.sv_danmaku)
//    IDanmakuView mDanmakuView;

    @Bind(R.id.video_play_activity_video_view)
    EMVideoView mVideoView;

    private String mVideoId;
    private String mDanmakuId;
    private String mSourceId;
    private String mSourceType;
    private BaseDanmakuParser mParser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        initDate();
        mVideoView.setOnPreparedListener(this);

        if (TextUtils.isEmpty(mSourceType)) {
            GlobalUtils.showToastShort(this, "读取视频源出错");
            return;
        }

//        Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                subscriber.onNext(TextUtils.equals(mSourceType, "zhuzhan"));
//            }
//        });

        if (TextUtils.equals(mSourceType, "zhuzhan")) {
            videoFormzhuzhan();
        }
    }

    private void videoFormzhuzhan() {
        Observable<NewAcVideo> observable = NewAcApi.getNewAcVideo().onResult(mVideoId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewAcVideo>() {
                    @Override
                    public void call(NewAcVideo newAcVideo) {
                        Uri uri = Uri.parse(newAcVideo.getData().getFiles().get(0).getUrl().get(0));
                        mVideoView.setVideoURI(uri);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void initDate() {
        mVideoId = getIntent().getStringExtra(AcString.VIDEO_ID);
        mDanmakuId = getIntent().getStringExtra(AcString.DANMAKU_ID);
        mSourceId = getIntent().getStringExtra(AcString.SOURCE_ID);
        mSourceType = getIntent().getStringExtra(AcString.SOURCE_TYPE);
    }


    @Override
    protected void onPause() {
        super.onPause();
//        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
//            mDanmakuView.pause();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
//            mDanmakuView.resume();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mDanmakuView != null) {
//            // dont forget release!
//            mDanmakuView.release();
//            mDanmakuView = null;
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (mDanmakuView != null) {
//            // dont forget release!
//            mDanmakuView.release();
//            mDanmakuView = null;
//        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoView.start();
    }
}
