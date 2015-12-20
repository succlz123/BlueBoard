package org.succlz123.blueboard.service;

import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.api.acfun.NewAcApi;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.okdownload.OkDownloadEnqueueListener;
import org.succlz123.okdownload.OkDownloadError;
import org.succlz123.okdownload.OkDownloadManager;
import org.succlz123.okdownload.OkDownloadRequest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by succlz123 on 15/9/2.
 */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    public static void startService(Context context, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);
        context.startService(intent);
    }

    public static void bindService(Context context, ServiceConnection serviceConnection, int flags) {
        Intent intent = new Intent(context, DownloadService.class);
//        context.startService(intent);
        context.bindService(intent, serviceConnection, flags);
    }

    private OkDownloadManager mOkDownloadManager;
    private long previousTime;
    private ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> mDownLoadList = new ArrayList<>();
    private HashMap<Integer, OkDownloadEnqueueListener> mListenerHashMap = new HashMap<>();
    private int mPosition;

    public HashMap<Integer, OkDownloadEnqueueListener> getListenerHashMap() {
        return mListenerHashMap;
    }

    public void setListenerHashMap(HashMap<Integer, OkDownloadEnqueueListener> listenerHashMap) {
        this.mListenerHashMap = listenerHashMap;
    }

    public void setListener(OkDownloadEnqueueListener listener, int position) {
        mListenerHashMap.put(position, listener);
    }

    public ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> getDownLoadList() {
        return mDownLoadList;
    }

    public void setDownLoadList(ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        mDownLoadList = downLoadList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOkDownloadManager = OkDownloadManager.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder result = null;
        if (null == result) {
            result = new MyBinder();
        }
        return result;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity>
                downLoadList = intent.getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);

        if (downLoadList != null) {
            mDownLoadList.addAll(downLoadList);
        }

        initDownload();

        return super.onStartCommand(intent, flags, startId);
    }

    private void initDownload() {
        for (int i = 0; i < mDownLoadList.size(); i++) {
            AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity = mDownLoadList.get(i);
            if (TextUtils.equals(videosEntity.getSourceType(), "zhuzhan")) {
                downloadFromZhuZhan(i, videosEntity);
            } else {
                GlobalUtils.showToastShort("非主站");
            }
        }
    }

    private void downloadFromZhuZhan(final int position, final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity) {
        final String videoId = videosEntity.getCommentId();
        final String filePath = this.getExternalFilesDir("video").getAbsolutePath() + File.separator + videoId + ".mp4";

        final int notificationId = Integer.valueOf(videoId);
        final String title = videosEntity.getVideoTitle();
        final String description = videosEntity.getName();

        Observable<NewAcVideo> videoObservable = NewAcApi.getNewAcVideo().onResult(videoId);
        videoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewAcVideo>() {
                    @Override
                    public void call(NewAcVideo newAcVideo) {
                        List<NewAcVideo.DataEntity.FilesEntity> list = newAcVideo.getData().getFiles();
                        Collections.reverse(list);
                        String url = list.get(0).getUrl().get(0);

                        OkDownloadRequest request = new OkDownloadRequest.Builder()
                                .url(url)
                                .filePath(filePath)
                                .build();

                        download(position, request, videosEntity);
                    }
                });
    }

    public void download(final int position, OkDownloadRequest request, final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity) {
        if (mListenerHashMap == null) {
            return;
        }
        final OkDownloadEnqueueListener listener = mListenerHashMap.get(position);

        mOkDownloadManager.enqueue(request, new OkDownloadEnqueueListener() {
            @Override
            public void onStart(int id) {
                if (listener != null) {
                    listener.onStart(id);
                }
            }

            @Override
            public void onProgress(int progress, long cacheSize, long totalSize) {
                if (listener != null) {
                    listener.onProgress(progress, cacheSize, totalSize);
                }
            }

            @Override
            public void onRestart() {
                if (listener != null) {
                    listener.onRestart();
                }
            }

            @Override
            public void onPause() {
                if (listener != null) {
                    listener.onPause();
                }
            }

            @Override
            public void onCancel() {
                if (listener != null) {
                    listener.onCancel();
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onCancel();
                }
                mDownLoadList.remove(videosEntity);
            }

            @Override
            public void onError(OkDownloadError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
    }

    public class MyBinder extends Binder {

        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
