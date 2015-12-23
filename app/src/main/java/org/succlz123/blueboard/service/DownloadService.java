package org.succlz123.blueboard.service;

import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.api.acfun.NewAcApi;
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
import android.util.Log;

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

    public static void startService(Context context, ArrayList<OkDownloadRequest> downLoadList) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);
        context.startService(intent);
    }

    public static void bindService(Context context, ServiceConnection serviceConnection, int flags) {
        Intent intent = new Intent(context, DownloadService.class);
        context.bindService(intent, serviceConnection, flags);
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        context.stopService(intent);
    }

    private OkDownloadManager mOkDownloadManager;
    private HashMap<String, OkDownloadRequest> mDownloadRequests = new HashMap<>();
    private HashMap<String, OkDownloadEnqueueListener> mListeners = new HashMap<>();

    public HashMap<String, OkDownloadEnqueueListener> getListeners() {
        return mListeners;
    }

    public void clearListenerMap() {
        this.mListeners.clear();
    }

    public void addListener(String sign, OkDownloadEnqueueListener listener) {
        mListeners.put(sign, listener);
    }

    public HashMap<String, OkDownloadRequest> getDownloadRequests() {
        return mDownloadRequests;
    }

//    public void addDownloadRequest(String sign) {
//        mDownloadRequests.put(sign, "zhuzhan");
//    }

    public void toggleDownload(String sign, OkDownloadRequest request) {
        if (mDownloadRequests.get(sign) == null) {
            downloadFromZhuZhan(request);
            return;
        }
        download(sign, mDownloadRequests.get(sign));
    }

    public void cancelDownload(String sign, OkDownloadEnqueueListener listener) {
        if (mDownloadRequests.get(sign) != null) {
            mDownloadRequests.remove(sign);
        }
        mOkDownloadManager.onCancel(sign, listener);
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
        if (intent == null) {
            return Service.START_NOT_STICKY;
        }

        ArrayList<OkDownloadRequest> downLoadList = intent.getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);

        initDownload(downLoadList);

        return Service.START_NOT_STICKY;
    }

    private void initDownload(ArrayList<OkDownloadRequest> downLoadList) {
        for (OkDownloadRequest request : downLoadList) {
            downloadFromZhuZhan(request);
        }
    }

    private void downloadFromZhuZhan(final OkDownloadRequest request) {
        final String videoId = request.getSign();
        final String filePath = this.getExternalFilesDir("video").getAbsolutePath() + File.separator + videoId + ".mp4";

//        final int notificationId = Integer.valueOf(videoId);

        //查询是否已在下载队列
        if (mDownloadRequests.get(videoId) != null) {
            GlobalUtils.showToastLong("下载任务已经在队列中");
            return;
        }

        Observable<NewAcVideo> videoObservable = NewAcApi.getNewAcVideo().onResult(videoId);
        videoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewAcVideo>() {
                    @Override
                    public void call(NewAcVideo newAcVideo) {
                        List<NewAcVideo.DataEntity.FilesEntity> list = newAcVideo.getData().getFiles();
                        Collections.reverse(list);
                        String url = list.get(0).getUrl().get(0);

                        request.setUrl(url);
                        request.setFilePath(filePath);

                        mDownloadRequests.put(videoId, request);
                        download(videoId, request);
                    }
                });
    }

    public void download(final String sign, OkDownloadRequest request) {
        mOkDownloadManager.enqueue(request, new OkDownloadEnqueueListener() {
            @Override
            public void onStart(int id) {
                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onStart(id);
                }
            }

            @Override
            public void onProgress(int progress, long cacheSize, long totalSize) {
//                Log.w(TAG, "onProgress: " + sign + "/" + progress);

                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onProgress(progress, cacheSize, totalSize);
                }
            }

            @Override
            public void onRestart() {
                Log.w(TAG, "onRestart: " + sign);

                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onRestart();
                }
            }

            @Override
            public void onPause() {
                Log.w(TAG, "onPause: " + sign);

                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onPause();
                }
            }

            @Override
            public void onCancel(String sign) {
                Log.w(TAG, "onCancel: " + sign);

                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onCancel(sign);
                }
                mDownloadRequests.remove(sign);
            }

            @Override
            public void onFinish(int id) {
                Log.w(TAG, "onFinish: " + id);

                OkDownloadEnqueueListener listener = mListeners.get(sign);
                if (listener != null) {
                    listener.onFinish(id);
                }
                mDownloadRequests.remove(sign);
            }

            @Override
            public void onError(OkDownloadError error) {
                OkDownloadEnqueueListener listener = mListeners.get(sign);
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
//        mOkDownloadManager.onPauseAll();
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

}
