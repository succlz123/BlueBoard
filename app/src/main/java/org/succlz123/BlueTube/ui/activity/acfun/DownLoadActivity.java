package org.succlz123.bluetube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.bean.acfun.AcContentVideo;
import org.succlz123.bluetube.support.adapter.acfun.recyclerview.DownLoadRvAdapter;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.support.utils.ViewUtils;
import org.succlz123.bluetube.ui.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/8/21.
 */
public class DownLoadActivity extends BaseActivity {

    public static void startActivity(Context activity, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
        Intent intent = new Intent(activity, DownLoadActivity.class);
        intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);

        activity.startActivity(intent);
    }

    private LinearLayoutManager mManager;
    private DownLoadRvAdapter mAdapter;
    private android.app.DownloadManager mDownloadManager;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.download_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        ViewUtils.setToolbar(DownLoadActivity.this, mToolbar, true, "下载");
        mDownloadManager = (android.app.DownloadManager) MyApplication.getsInstance().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

        mManager = new LinearLayoutManager(DownLoadActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DownLoadRvAdapter();


        mRecyclerView.setAdapter(mAdapter);

        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList
                = getIntent().getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);

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

                request.setDestinationInExternalFilesDir(DownLoadActivity.this, "video", fileName);
                request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI);
                request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE);
                request.setTitle(title);
                request.setDescription(description);
                request.setAllowedOverRoaming(false);

                long downloadId = mDownloadManager.enqueue(request);
            }

            @Override
            public void failure(RetrofitError error) {
                error.getUrl();
            }
        });
    }

}
