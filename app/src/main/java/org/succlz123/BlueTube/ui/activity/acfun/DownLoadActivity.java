package org.succlz123.bluetube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;

import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.bean.acfun.AcContentVideo;
import org.succlz123.bluetube.support.adapter.acfun.recyclerview.DownLoadRvAdapter;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.support.utils.LogUtils;
import org.succlz123.bluetube.support.utils.ViewUtils;
import org.succlz123.bluetube.ui.activity.BaseActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
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
    private DownloadManager mDownloadManager;
    private MyDownLoadListener mDownloadListener;

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

        mManager = new LinearLayoutManager(DownLoadActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DownLoadRvAdapter();

        mDownloadManager = new DownloadManager(DownLoadActivity.this);
        mDownloadListener = new MyDownLoadListener();

        mAdapter.setOnClickListener(new DownLoadRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, DownloadTask downloadTask,int type) {

                switch (downloadTask.getStatus()) {
                    case DownloadStatus.STATUS_PENDING:
                    case DownloadStatus.STATUS_FAILED:
                    case DownloadStatus.STATUS_STOPPED:
                    case DownloadStatus.STATUS_FINISHED:
                        mDownloadManager.start(downloadTask, mDownloadListener);
                        break;
                    case DownloadStatus.STATUS_RUNNING:
                        mDownloadManager.stop(downloadTask, mDownloadListener);
                        break;
                    default:
                        break;
                }
                if(type==0){
                    GlobalUtils.showToastShort(DownLoadActivity.this, "fadfsf");

                    mDownloadManager.deleteforever(downloadTask,mDownloadListener);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList
                = getIntent().getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);


        for (final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity : downLoadList) {

            String sourceType = videosEntity.getSourceType();
            final String sourceId = videosEntity.getSourceId();

            if (TextUtils.equals(sourceType, "letv")) {
                RetrofitConfig.getAcContentLeTvVideo().onContentResult(
                        AcApi.getAcContentVideoUrl(videosEntity.getSourceId()), new Callback<AcContentVideo>() {

                            @Override
                            public void success(AcContentVideo acContentVideo, Response response) {
                                String base64Url = acContentVideo.getData().getVideo_list().getVideo_4().getMain_url();
                                String path = GlobalUtils.decodeByBase64(base64Url);

                                String fileName = sourceId + ".mp4";
                                String filePathName = DownLoadActivity.this.getExternalFilesDir("video").getAbsolutePath() + File.separator + fileName;

                                DownloadTask task = new DownloadTask(DownLoadActivity.this);
                                task.setUrl(path);
                                task.setPath(filePathName);

                                mDownloadManager.add(task, mDownloadListener); //Add the task
                                mAdapter.setDownloadTask(task);
                                mAdapter.setVideosEntity(videosEntity);
                                //                                mDownloadManager.start(task, mDownloadListener); //Start the task
//                                mDownloadManager.stop(task, mDownloadListener); //Stop the task if you exit your APP.
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

            }


        }

    }


    private class MyDownLoadListener extends DownloadListener<Integer, DownloadTask> {
        /**
         * The download task has been added to the sqlite.
         * <p>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been added to the sqlite.
         */
        @Override
        public void onAdd(DownloadTask downloadTask) {
            super.onAdd(downloadTask);
            LogUtils.e("onAdd()");
        }

        /**
         * The download task has been delete from the sqlite
         * <p>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been deleted to the sqlite.
         */
        @Override
        public void onDelete(DownloadTask downloadTask) {
            super.onDelete(downloadTask);
            LogUtils.e("onDelete()");
        }

        /**
         * The download task is stop
         * <p>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been stopped.
         */
        @Override
        public void onStop(DownloadTask downloadTask) {
            super.onStop(downloadTask);
            mAdapter.setIsDownloadStart(false);
            GlobalUtils.showToastShort(DownLoadActivity.this, "暂停下载");
        }

        /**
         * Runs on the UI thread before doInBackground(Params...).
         */
        @Override
        public void onStart() {
            super.onStart();
            mAdapter.setIsDownloadStart(true);
            GlobalUtils.showToastShort(DownLoadActivity.this, "开始下载");
        }

        /**
         * Runs on the UI thread after publishProgress(Progress...) is invoked. The
         * specified values are the values passed to publishProgress(Progress...).
         *
         * @param values The values indicating progress.
         */
        @Override
        public void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mAdapter.notifyDataSetChanged();
//            mAdapter.setValues(values[0]);
        }

        /**
         * Runs on the UI thread after doInBackground(Params...). The specified
         * result is the value returned by doInBackground(Params...). This method
         * won't be invoked if the task was cancelled.
         *
         * @param downloadTask The result of the operation computed by
         *                     doInBackground(Params...).
         */
        @Override
        public void onSuccess(DownloadTask downloadTask) {
            super.onSuccess(downloadTask);
            LogUtils.e("onSuccess()");
        }

        /**
         * Applications should preferably override onCancelled(Object). This method
         * is invoked by the default implementation of onCancelled(Object). Runs on
         * the UI thread after cancel(boolean) is invoked and
         * doInBackground(Object[]) has finished.
         */
        @Override
        public void onCancelled() {
            super.onCancelled();
            LogUtils.e("onCancelled()");
        }

        @Override
        public void onError(Throwable thr) {
            super.onError(thr);
            GlobalUtils.showToastShort(DownLoadActivity.this, "下载错误 请重试");
        }

        /**
         * Runs on the UI thread after doInBackground(Params...) when the task is
         * finished or cancelled.
         */
        @Override
        public void onFinish() {
            super.onFinish();
            LogUtils.e("onFinish()");
        }
    }


    private class DownloadChangeObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

}
