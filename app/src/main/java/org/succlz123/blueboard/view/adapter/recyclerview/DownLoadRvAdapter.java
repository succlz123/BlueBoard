package org.succlz123.blueboard.view.adapter.recyclerview;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.service.DownloadService;
import org.succlz123.blueboard.view.adapter.base.BaseRvViewHolder;
import org.succlz123.okdownload.OkDownloadEnqueueListener;
import org.succlz123.okdownload.OkDownloadError;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by succlz123 on 15/9/1.
 */
public class DownLoadRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap<String, Integer> downloadMap = new HashMap<>();

    private WeakReference<Activity> mActivity;
    private OkDownloadEnqueueListener mListener;
    private ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> mDownLoadList = new ArrayList<>();
    private DownloadService mDownloadService;
    private DecimalFormat df = new DecimalFormat("0.00");

    private long time = System.currentTimeMillis();

    public DownLoadRvAdapter(Activity activity) {
        super();
        this.mActivity = new WeakReference<Activity>(activity);
    }

    public OkDownloadEnqueueListener getListener() {
        return mListener;
    }

    public void setListener(OkDownloadEnqueueListener listener) {
        mListener = listener;
    }

    public DownloadService getDownloadService() {
        return mDownloadService;
    }

    public void setDownloadService(DownloadService downloadService) {
        mDownloadService = downloadService;
        ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity>
                downLoadList = mDownloadService.getDownLoadList();

        if (downLoadList == null) {
            return;
        }
        mDownLoadList = downLoadList;
        notifyDataSetChanged();
    }

    public class DownloadVH extends BaseRvViewHolder {
        private TextView tvTitle;
        private TextView tvName;
        private TextView tvFileSize;
        private ProgressBar pbDownload;
        private Button btnToggle;
        private Button btnCancel;

        public DownloadVH(View itemView) {
            super(itemView);
            tvTitle = f(itemView, R.id.tv_download_title);
            tvName = f(itemView, R.id.tv_download_info);
            tvFileSize = f(itemView, R.id.tv_download_file_size);
            pbDownload = f(itemView, R.id.pb_download);
            btnToggle = f(itemView, R.id.btn_download_toggle);
            btnCancel = f(itemView, R.id.btn_download_cancel);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View downloadView = inflater.inflate(R.layout.hahh, parent, false);

        return new DownloadVH(downloadView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DownloadVH) {
            if (mDownLoadList.size() <= 0) {
                return;
            }

            String title = mDownLoadList.get(position).getVideoTitle();
            String name = mDownLoadList.get(position).getName();

            ((DownloadVH) holder).tvTitle.setText(title);
            ((DownloadVH) holder).tvName.setText(name);
            ((DownloadVH) holder).pbDownload.setMax(100);

            mListener = new OkDownloadEnqueueListener() {
                @Override
                public void onStart(int id) {

                }

                @Override
                public void onProgress(int progress, long cacheSize, long totalSize) {
                    if (!GlobalUtils.isActivityLive(mActivity.get())) {
                        return;
                    }

                    ((DownloadVH) holder).pbDownload.setProgress(progress);

                    if (cacheSize != 0 && totalSize != 0) {
                        final float convertCacheSize = ((float) cacheSize) / 1024 / 1024;
                        final float convertTotalSize = ((float) totalSize) / 1024 / 1024;

                        ((DownloadVH) holder).tvFileSize.post(new Runnable() {
                            @Override
                            public void run() {
                                ((DownloadVH) holder).tvFileSize.setText(df.format(convertCacheSize) + "m" + "/" + df.format(convertTotalSize) + "m");
                            }
                        });
                    }
                }

                @Override
                public void onRestart() {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onError(OkDownloadError error) {

                }
            };

            mDownloadService.setListener(mListener, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDownLoadList.size();
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2px(parent.getContext(), 7);
            if (position == 1 | position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
