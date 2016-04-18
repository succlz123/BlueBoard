package org.succlz123.blueboard.controller.activity.acfun;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseActivity;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.service.DownloadService;
import org.succlz123.blueboard.view.adapter.recyclerview.download.DownLoadRvAdapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by succlz123 on 15/8/21.
 */
public class DownLoadActivity extends BaseActivity {

    public static void newInstance(Context activity) {
        Intent intent = new Intent(activity, DownLoadActivity.class);
        activity.startActivity(intent);
    }

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mManager;
    private DownLoadRvAdapter mAdapter;
    private MyConnection mMyConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mToolbar = f(R.id.toolbar);
        mRecyclerView = f(R.id.download_recycler_view);

        ViewUtils.setToolbar(DownLoadActivity.this, mToolbar, true, "下载");

        mManager = new LinearLayoutManager(DownLoadActivity.this);
        mRecyclerView.setLayoutManager(mManager);

        mAdapter = new DownLoadRvAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mMyConnection = new MyConnection(mAdapter);
        DownloadService.bindService(DownLoadActivity.this, mMyConnection, Context.BIND_AUTO_CREATE);

        mAdapter.setDownLoadList(MyApplication.getInstance().quaryAllDonwload());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mMyConnection.isBindService()) {
            this.unbindService(mMyConnection);
            mMyConnection.setIsBindService(false);
        }
        mAdapter.cleanListener();
        super.onDestroy();
    }

    private static class MyConnection implements ServiceConnection {
        private DownloadService downloadService;
        private DownLoadRvAdapter adapter;
        private boolean isBindService;

        public boolean isBindService() {
            return isBindService;
        }

        public void setIsBindService(boolean isBindService) {
            this.isBindService = isBindService;
        }

        private MyConnection(DownLoadRvAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBindService = true;
            downloadService = ((DownloadService.MyBinder) service).getService();
            if (downloadService == null) {
                return;
            }
            adapter.setDownloadService(downloadService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            downloadService = null;
            isBindService = false;
        }
    }
}
