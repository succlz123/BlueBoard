package org.succlz123.bluetube.ui.activity.acfun;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.support.adapter.acfun.recyclerview.DownLoadRvAdapter;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.service.DownloadService;
import org.succlz123.bluetube.support.utils.ViewUtils;
import org.succlz123.bluetube.ui.activity.BaseActivity;
import org.succlz123.nbdownload.NBDownloadRequest;
import org.succlz123.nbdownload.NBDownloadStatus;
import org.succlz123.nbdownload.dao.RequestDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private BroadcastReceiver mBroadcastReceiver;
    private MyConnection mMyConnection;
    private boolean mIsBindService;

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

        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity>
                downLoadList = getIntent().getParcelableArrayListExtra(AcString.DOWNLOAD_LIST);

        mManager = new LinearLayoutManager(DownLoadActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(null);
        mAdapter = new DownLoadRvAdapter();

        final RequestDao requestDao = RequestDao.newInstance(MyApplication.getsInstance().getApplicationContext());

        if (downLoadList != null) {

            for (final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity : downLoadList) {

                List<NBDownloadRequest> requestList = requestDao.query("name", videosEntity.getSourceId());
                if (requestList.size() > 0) {
                    return;
                }

                NBDownloadRequest downloadRequest = new NBDownloadRequest();
                String sourceId = videosEntity.getDanmakuId();
                String sourceType = videosEntity.getSourceType();
                String filePath = MyApplication.getsInstance().getApplicationContext()
                        .getExternalFilesDir("video")
                        .getAbsolutePath() + File.separator + sourceId + ".mp4";

                String title = videosEntity.getVideoTitle();
                String description = videosEntity.getName();

                downloadRequest.setName(sourceId);
                downloadRequest.setTitle(title);
                downloadRequest.setDescription(description);
                downloadRequest.setSourceId(sourceId);
                downloadRequest.setSourceType(sourceType);
                downloadRequest.setFilePath(filePath);
                downloadRequest.setStatus(NBDownloadStatus.STATUS_PENDING);

                if (requestDao.add(downloadRequest) == 1) {
                    Intent intent = new Intent(this, DownloadService.class);
//                    intent.putParcelableArrayListExtra(AcString.DOWNLOAD_LIST, downLoadList);
                    GetDownloadInfo getDownloadInfo = new GetDownloadInfo() {
                        @Override
                        public void onResult(final String name, final int progress, final long cacheSize, final long totalSize, final long averageSpeed, final long realTimeSpeed) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.setProgress(name, progress, cacheSize, totalSize, averageSpeed, realTimeSpeed);
                                }
                            });
                        }
                    };
                    mMyConnection = new MyConnection(getDownloadInfo);

                    this.startService(intent);
                    this.bindService(intent, mMyConnection, Context.BIND_AUTO_CREATE);
//                    mMyConnection.xx(new GetDownloadInfo() {
//                        @Override
//                        public void onResult(String name, int progress, long cacheSize, long totalSize, long averageSpeed, long realTimeSpeed) {
//                            mAdapter.setProgress(name, progress, cacheSize, totalSize, averageSpeed, realTimeSpeed);
//                        }
//                    });
                    mIsBindService = true;
                }
            }
        }

        List<NBDownloadRequest> downloadRequests = requestDao.queryForAll();
        if (downloadRequests != null && downloadRequests.size() > 0) {
            mAdapter.setNBDownloadRequests(downloadRequests);
        }

        mAdapter.setOnClickListener(new DownLoadRvAdapter.OnClickListener() {

            @Override
            public void onToggle(View view, int position, String name) {
                mMyConnection.toggle(name);
            }

            @Override
            public void onDelete(View view, int position, NBDownloadRequest downloadRequest) {
//                List<NBDownloadRequest> requestList = requestDao.query("name", name);
//                if (requestList.size() > 0) {
//                    NBDownloadRequest downloadRequest = requestList.get(0);
//
//                    String filePath = downloadRequest.getFilePath();
//                    if (filePath != null) {
//                        File file = new File(downloadRequest.getFilePath());
//                        file.delete();
//                    }
//                    requestDao.delete(requestList.get(0));
                mMyConnection.cancel(downloadRequest);
                mAdapter.deleteRequests(position);
//                 }
            }
        });

//        mBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getExtras();
//                int xx = bundle.getInt("xx");
////                LogUtils.e(String.valueOf(xx));
//            }
//        };
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("update_download_progress");
//        registerReceiver(mBroadcastReceiver, intentFilter);


        mRecyclerView.setAdapter(mAdapter);
    }

    public interface GetDownloadInfo {
        void onResult(String name, int progress, long cacheSize, long totalSize, long averageSpeed, long realTimeSpeed);
    }


    private class MyConnection implements ServiceConnection {
        private DownloadService mDownloadService;
        private GetDownloadInfo getDownloadInfo;

        private MyConnection(GetDownloadInfo getDownloadInfo) {
            this.getDownloadInfo = getDownloadInfo;
        }

        public void toggle(String name) {
            if (mDownloadService != null) {
                mDownloadService.toggleDownload(name);
            }
        }

        public void cancel(NBDownloadRequest downloadRequest) {
            if (mDownloadService != null) {
                mDownloadService.delete(downloadRequest);
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadService = ((DownloadService.MyBinder) service).getService();
            mDownloadService.xxx(getDownloadInfo);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadService = null;
        }
    }

    @Override
    protected void onDestroy() {
//        this.unregisterReceiver(mBroadcastReceiver);
        if (mIsBindService) {
            this.unbindService(mMyConnection);
            mIsBindService = false;
        }
        super.onDestroy();
    }
}
