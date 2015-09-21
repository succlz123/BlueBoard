package org.succlz123.bluetube.support.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.bean.newacfun.NewAcVideo;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.ui.activity.acfun.DownLoadActivity;
import org.succlz123.nbdownload.NBDownloadException;
import org.succlz123.nbdownload.NBDownloadListener;
import org.succlz123.nbdownload.NBDownloadManager;
import org.succlz123.nbdownload.NBDownloadRequest;
import org.succlz123.nbdownload.NBDownloadStatus;
import org.succlz123.nbdownload.dao.RequestDao;
import org.succlz123.nbdownload.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by succlz123 on 15/9/2.
 */
public class DownloadService extends Service {

    public static void startService(Context context, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);

        context.startService(intent);
    }

    public static void bindService(Context context, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);

        context.startService(intent);
    }

    private NBDownloadManager mDownloadManager;
    private MyDownloadListener mMyDownloadListener;
    private Context mContext;
    private DownLoadActivity.GetDownloadInfo getDownloadInfo;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
        final RequestDao requestDao = RequestDao.newInstance(MyApplication.getsInstance().getApplicationContext());
        List<NBDownloadRequest> downloadRequests
                = requestDao.query("status", String.valueOf(NBDownloadStatus.STATUS_PENDING));

        if (downloadRequests != null && downloadRequests.size() > 0) {
            mDownloadManager = new NBDownloadManager(DownloadService.this);

            for (final NBDownloadRequest downloadRequest : downloadRequests) {
                String sourceType = downloadRequest.getSourceType();

                if (TextUtils.equals(sourceType, "letv")) {
                    Call<NewAcVideo> call = RetrofitConfig.getNewAcVideo().onResult(downloadRequest.getSourceId());

                    call.enqueue(new Callback<NewAcVideo>() {
                        @Override
                        public void onResponse(Response<NewAcVideo> response) {
                            NewAcVideo acVideo = response.body();


                            if (acVideo != null && acVideo.getData() != null) {
                                String url = acVideo.getData().getFiles().get(0).getUrl().get(0);

                                download(downloadRequest, url);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });


//                    Call<AcContentVideo> call = RetrofitConfig.getAcContentLeTvVideo().onContentResult
//                            (AcApi.getAcContentVideoUrl(downloadRequest.getSourceId()));
//                    call.enqueue(new Callback<AcContentVideo>() {
//
//                        @Override
//                        public void onResponse(Response<AcContentVideo> response) {
//                            AcContentVideo acContentVideo = response.body();
//                            if (acContentVideo != null && acContentVideo.getData().getVideo_list() != null) {
//                                AcContentVideo.DataEntity.VideoListEntity videoListEntity
//                                        = acContentVideo.getData().getVideo_list();
//
//                                String base64Url = response.body().getData().getVideo_list().getVideo_1().getMain_url();
//                                String url = GlobalUtils.decodeByBase64(base64Url);
//                                download(downloadRequest, url);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t) {
//
//                        }
//                    });
                }
            }
        }
//            ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList
//                    = intent.getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(NBDownloadRequest downloadRequest, String url) {

        mMyDownloadListener = new MyDownloadListener();
        downloadRequest.setUrl(url);

        mDownloadManager.start(downloadRequest, mMyDownloadListener);
    }

    public void toggleDownload(String name) {
        mDownloadManager.toggle(name, mMyDownloadListener);
    }

    public void delete(NBDownloadRequest downloadRequest) {
        mDownloadManager.cancel(downloadRequest, mMyDownloadListener);
    }

    public void xxx(DownLoadActivity.GetDownloadInfo getDownloadInfo) {
        this.getDownloadInfo = getDownloadInfo;
    }

    private class MyDownloadListener implements NBDownloadListener {
        @Override
        public void onStart(NBDownloadRequest nbDownloadRequest) {
            LogUtils.e("start");
        }

        @Override
        public void onProgress(String name, int progress, long cacheSize, long totalSize, long averageSpeed, long realTimeSpeed) {
            LogUtils.e(name + "  " + progress + "  " + averageSpeed + "kb/s" + realTimeSpeed + "kb/s");

            if (getDownloadInfo != null) {
                getDownloadInfo.onResult(name, progress, cacheSize, totalSize, averageSpeed, realTimeSpeed);
            }
        }

        @Override
        public void onStop(String name) {
            LogUtils.e("stop");
        }

        @Override
        public void onCancel(NBDownloadRequest nbDownloadRequest) {
            LogUtils.e("onCancel");
        }

        @Override
        public void onFinish(String filePath) {
            LogUtils.e("onFinish");
        }

        @Override
        public void onError(NBDownloadException error) {
            if (error.getCode() == NBDownloadException.DOWNLOAD_REQUEST_IS_READY_IN_THE_QUEUE) {
                GlobalUtils.showToastShort(DownloadService.this, "下载任务已经完成或者在队列中");
            }
        }
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
