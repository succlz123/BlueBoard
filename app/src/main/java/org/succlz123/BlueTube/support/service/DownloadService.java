package org.succlz123.bluetube.support.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.bean.acfun.AcContentVideo;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/9/2.
 */
public class DownloadService extends Service {

    public static void startService(Context activity, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);

        activity.startService(intent);
    }

    private android.app.DownloadManager mDownloadManager;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList
                = intent.getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);

        mDownloadManager = (android.app.DownloadManager) MyApplication.getsInstance().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

        for (final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity : downLoadList) {

            String sourceType = videosEntity.getSourceType();

            if (TextUtils.equals(sourceType, "letv")) {
                RetrofitConfig.getAcContentLeTvVideo().onContentResult(
                        AcApi.getAcContentVideoUrl(videosEntity.getSourceId()), new Callback<AcContentVideo>() {

                            @Override
                            public void success(AcContentVideo acContentVideo, Response response) {
                                String base64Url = acContentVideo.getData().getVideo_list().getVideo_4().getMain_url();
                                String path = GlobalUtils.decodeByBase64(base64Url);

                                GlobalUtils.showToastShort(null, "耐心等待1-5分钟,也许会下载吧");
                                download(videosEntity, path);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }


    private void download(AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity, String path) {
        final String fileName = videosEntity.getSourceId() + ".mp4";
//        final String filePathName = DownLoadActivity.this.getExternalFilesDir("video").getAbsolutePath()
//                + File.separator + fileName;
        final String title = videosEntity.getVideoTitle();
        final String description = videosEntity.getName();

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setClient(new OkClient(MyApplication.okHttpClient()))
                .setEndpoint("http://g3.letv.cn")
                .build();
        AcApi.getVideoDownloadUrl videoDownloadUrl = restAdapter.create(AcApi.getVideoDownloadUrl.class);

        videoDownloadUrl.onResult(path.replace("http://g3.letv.cn/", ""), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String url = response.getUrl();

                android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Uri.parse(url));

                request.setDestinationInExternalFilesDir(MyApplication.getsInstance().getApplicationContext(), "video", fileName);
                request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI);
                request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setTitle(title);
                request.setDescription(description);
                request.setAllowedOverRoaming(false);

                long downloadId = mDownloadManager.enqueue(request);
                GlobalUtils.showToastShort(null, "开始下载 " + title);
            }

            @Override
            public void failure(RetrofitError error) {
                error.getUrl();
            }
        });
    }

}
