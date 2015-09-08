package org.succlz123.bluetube.support.adapter.acfun.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentInfo;
import org.succlz123.bluetube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/9/1.
 */
public class DownLoadRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DOWNLOADING = 0;
    private static final int TYPE_DOWNLOAD_COMPLETE = 1;

    private boolean isDownloadStart;

    private AcContentInfo.DataEntity.FullContentEntity.VideosEntity mVideosEntity;
    private Integer mValues;
    private OnClickListener mOnClickListener;

    public Integer getValues() {
        return mValues;
    }

    public void setValues(Integer values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public boolean isDownloadStart() {
        return isDownloadStart;
    }

    public void setIsDownloadStart(boolean isDownloadStart) {
        this.isDownloadStart = isDownloadStart;
        notifyDataSetChanged();
    }

    public AcContentInfo.DataEntity.FullContentEntity.VideosEntity getVideosEntity() {
        return mVideosEntity;
    }

    public void setVideosEntity(AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity) {
        mVideosEntity = videosEntity;
    }

    public interface OnClickListener {
        void onClick(View view, int position, int type);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class DownloadingVH extends RecyclerView.ViewHolder {
        @Bind(R.id.download_title)
        TextView tvTitle;

        @Bind(R.id.download_item_title)
        TextView tvItemTitle;

        @Bind(R.id.download_percent_tv)
        TextView tvPercent;

        @Bind(R.id.download_size_tv)
        TextView tvSize;

        @Bind(R.id.download_total_size_tv)
        TextView tvTotalSize;

        @Bind(R.id.download_pro_bar)
        ProgressBar parBarDownloading;

        @Bind(R.id.download_stop_btn)
        Button btnDownload;

        @Bind(R.id.download_delete_img)
        Button imgDownloadDelete;

        @Bind(R.id.download_downloading_cv)
        CardView cvDownloading;

        public DownloadingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DownloadCompleteVH extends RecyclerView.ViewHolder {
//        @Bind(R.id.cv_horizontal_tv_title)
//        TextView tvTitleOther;
//
//        @Bind(R.id.cv_horizontal_tv_up)
//        TextView tvUpOther;
//
//        @Bind(R.id.cv_horizontal_tv_click)
//        TextView tvClickOther;
//
//        @Bind(R.id.cv_horizontal_tv_reply)
//        TextView tvReplyOther;
//
//        @Bind(R.id.cv_horizontal_img_cover)
//        SimpleDraweeView imgCoverOther;
//
//        @Bind(R.id.cv_horizontal)
//        CardView cvHorizontal;

        public DownloadCompleteVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {


        return TYPE_DOWNLOADING;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View downloadingView = inflater.inflate(R.layout.activity_download_rv_downloading, parent, false);
        View downloadCompleteView = inflater.inflate(R.layout.activity_download_rv_download_complete, parent, false);

        if (viewType == TYPE_DOWNLOADING) {
            return new DownloadingVH(downloadingView);
        } else if (viewType == TYPE_DOWNLOAD_COMPLETE) {
            return new DownloadCompleteVH(downloadCompleteView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DownloadingVH) {

//            if (mVideosEntity != null) {
//
//                ((DownloadingVH) holder).tvTitle.setText(mVideosEntity.getVideoTitle());
//                ((DownloadingVH) holder).tvItemTitle.setText(mVideosEntity.getName());
//            }
//
//            if (mDownloadTask != null) {
//                DecimalFormat df = new DecimalFormat("0.00");
//
//                long fileSize = 0l;
//                File file = new File(mDownloadTask.getPath());
//                if (file.exists()) {
//                    fileSize = file.length();
//                    double showFileSize = ((double) fileSize / 1024 / 1024);
//                    ((DownloadingVH) holder).tvSize.setText(" " + String.valueOf(df.format(showFileSize)) + "M");
//                }
//
//                int progress = 0;
//                if (mDownloadTask.getSize() > 0) {
//                    progress = (int) (fileSize * 100 / mDownloadTask.getSize());
//                    double totalSize = (double) mDownloadTask.getSize() / 1024 / 1024;
//                    ((DownloadingVH) holder).tvTotalSize.setText("/" + String.valueOf(df.format(totalSize) + "M"));
//                    ((DownloadingVH) holder).tvPercent.setText(String.valueOf(progress) + "%");
//                    ((DownloadingVH) holder).parBarDownloading.setMax(100);
//                    ((DownloadingVH) holder).parBarDownloading.setProgress(progress);
//                }
//
////                if (isDownloadStart) {
////                    ((DownloadingVH) holder).btnDownload.setText("下载中");
////                } else {
////                    ((DownloadingVH) holder).btnDownload.setText("暂停中");
////                }
//
////                ((DownloadingVH) holder).btnDownload.setTin
//
//                ((DownloadingVH) holder).btnDownload.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        mOnClickListener.onClick(v, position, mDownloadTask, 1);
//                    }
//                });
//
//                ((DownloadingVH) holder).imgDownloadDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnClickListener.onClick(v, position, mDownloadTask, 0);
//                    }
//                });
//            }


        } else if (holder instanceof DownloadCompleteVH) {


        }
    }

    @Override
    public int getItemCount() {

        return 1;
    }

    //根据position判断是否显示间隔标题
    public boolean getTitleType(int position) {
        if (position == 0 | position == 5) {
            return true;
        }
        return false;
    }

    //处理cardView中间的margin
    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2pix(parent.getContext(), 7);
            if (position == 1 | position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
