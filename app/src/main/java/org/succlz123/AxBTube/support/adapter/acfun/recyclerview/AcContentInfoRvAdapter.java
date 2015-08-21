package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/4.
 */
public class AcContentInfoRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIDEO_INFO = 0;
    private static final int VIDEO_ITEM = 1;
    private static final int VIDEO_DOWNLOAD = 2;

    private AcContentInfo mAcContentInfo;

    private OnVideoPlayClickListener mOnVideoPlayClickListener;
    private OnDownLoadClickListener mOnDownLoadClickListener;

    private List<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> mVideoList = new ArrayList<>();

    private boolean mIsShowDlCheckBox;
    private boolean mIsDlCheckBoxSelectAll;
    private HashMap<Integer, Boolean> mCheckBoxCheckedArray = new HashMap();
    private List<Integer> mCheckedList = new ArrayList<>();
    private ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> mDownLoadList = new ArrayList<>();

    public void setIsShowDlCheckBox(boolean isShowDlCheckBox, boolean isDlCheckBoxSelectAll) {
        this.mIsShowDlCheckBox = isShowDlCheckBox;
        mIsDlCheckBoxSelectAll = isDlCheckBoxSelectAll;
        notifyDataSetChanged();
    }

    public boolean isShowDlCheckBox() {
        return mIsShowDlCheckBox;
    }

    public AcContentInfo getAcContentInfo() {
        return mAcContentInfo;
    }

    public interface OnVideoPlayClickListener {
        void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType);
    }

    public interface OnDownLoadClickListener {
        void onClick(View view, int position, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList);
    }

    public OnDownLoadClickListener getOnDownLoadClickListener() {
        return mOnDownLoadClickListener;
    }

    public void setOnDownLoadClickListener(OnDownLoadClickListener onDownLoadClickListener) {
        mOnDownLoadClickListener = onDownLoadClickListener;
    }

    public void setOnVideoPlayClickListener(OnVideoPlayClickListener onVideoPlayClickListener) {
        this.mOnVideoPlayClickListener = onVideoPlayClickListener;
    }

    public void setContentInfo(AcContentInfo acContentInfo) {
        this.mAcContentInfo = acContentInfo;
        mVideoList = acContentInfo.getData().getFullContent().getVideos();
        notifyDataSetChanged();
    }

    public class VideoInfoVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_rv_content_info_title)
        TextView tvTitle;
        @Bind(R.id.ac_rv_content_info_description)
        TextView tvDescription;
        @Bind(R.id.ac_rv_content_info_click)
        TextView tvClick;
        @Bind(R.id.ac_rv_content_info_stows)
        TextView tvStows;
        @Bind(R.id.ac_rv_content_info_up_img)
        SimpleDraweeView simpleDraweeView;
        @Bind(R.id.ac_rv_content_info_up_name)
        TextView tvName;
        @Bind(R.id.ac_rv_content_info_up_level)
        TextView tvUid;
        @Bind(R.id.cv_content_title_info_frame_layout)
        FrameLayout frameLayout;

        public VideoInfoVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VideoItemVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_rv_content_info_video_tv)
        TextView tvVideo;
        @Bind(R.id.ac_rv_content_info_video_cb)
        CheckBox cbVideo;
        @Bind(R.id.cv_content_video_item)
        CardView cardView;

        public VideoItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DownLoadItemVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_rv_content_download_cv)
        CardView cardView;

        public DownLoadItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIDEO_INFO;
        } else if (getItemCount() - position == 1 && mIsShowDlCheckBox) {
            return VIDEO_DOWNLOAD;
        } else {
            return VIDEO_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View titleInfoView = inflater.inflate(R.layout.ac_rv_cardview_content_title_info, parent, false);
        View videoItemView = inflater.inflate(R.layout.ac_rv_cardview_content_video_item, parent, false);
        View downLoadItemView = inflater.inflate(R.layout.ac_rv_content_download, parent, false);

        if (viewType == VIDEO_INFO) {
            return new VideoInfoVH(titleInfoView);
        } else if (viewType == VIDEO_ITEM) {
            return new VideoItemVH(videoItemView);
        } else if (viewType == VIDEO_DOWNLOAD) {
            return new DownLoadItemVH(downLoadItemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VideoInfoVH) {
            if (mAcContentInfo != null) {
                final AcContentInfo.DataEntity.FullContentEntity contentEntity
                        = mAcContentInfo.getData().getFullContent();

                ((VideoInfoVH) holder).tvTitle
                        .setText(contentEntity.getTitle());
                ((VideoInfoVH) holder).tvDescription
                        .setText(Html.fromHtml(contentEntity.getDescription()));
                ((VideoInfoVH) holder).tvClick
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + contentEntity.getViews());
                ((VideoInfoVH) holder).tvStows
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.stows) + " " + contentEntity.getStows());
                ((VideoInfoVH) holder).simpleDraweeView
                        .setImageURI(Uri.parse(contentEntity.getUser().getUserImg()));
                ((VideoInfoVH) holder).tvName
                        .setText(contentEntity.getUser().getUsername());
                ((VideoInfoVH) holder).tvUid
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.uid) + " " + contentEntity.getUser().getUserId());

                if (mOnVideoPlayClickListener != null) {
                    ((VideoInfoVH) holder).frameLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnVideoPlayClickListener.onClick(v,
                                    position,
                                    String.valueOf(contentEntity.getUser().getUserId()),
                                    null, null, null, null);
                        }
                    });
                }
            }
        } else if (holder instanceof VideoItemVH) {
            if (mVideoList.size() != 0) {
                final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity
                        = mVideoList.get(position - 1);

                ((VideoItemVH) holder).tvVideo
                        .setText((position) + ". " + videosEntity.getName());

                if (!mIsShowDlCheckBox && mOnVideoPlayClickListener != null) {
                    ((VideoItemVH) holder).cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnVideoPlayClickListener.onClick(v,
                                    position,
                                    null,
                                    videosEntity.getVideoId(),
                                    videosEntity.getDanmakuId(),
                                    videosEntity.getSourceId(),
                                    videosEntity.getType());
                        }
                    });
                    ((VideoItemVH) holder).cbVideo.setVisibility(View.GONE);
                    if (!mIsDlCheckBoxSelectAll) {
                        ((VideoItemVH) holder).cbVideo.setChecked(false);
                    }
                } else {
                    ((VideoItemVH) holder).cbVideo.setVisibility(View.VISIBLE);
                    ((VideoItemVH) holder).cbVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox cb = (CheckBox) v;
                            boolean checked = cb.isChecked();
                            mCheckBoxCheckedArray.put(position, checked);
                        }
                    });

                    if (mIsDlCheckBoxSelectAll) {
                        ((VideoItemVH) holder).cbVideo.setChecked(true);
                        mCheckBoxCheckedArray.put(position, ((VideoItemVH) holder).cbVideo.isChecked());
                    } else {
                        ((VideoItemVH) holder).cbVideo.setChecked(false);
                    }
                }
            }
        } else if (holder instanceof DownLoadItemVH) {
            if (mVideoList.size() != 0 && mCheckBoxCheckedArray != null) {

                ((DownLoadItemVH) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckedList.clear();
                        for (Object obj : mCheckBoxCheckedArray.entrySet()) {
                            Map.Entry entry = (Map.Entry) obj;
                            Integer key = (Integer) entry.getKey();
                            Boolean value = (Boolean) entry.getValue();
                            if (value == true) {
                                mCheckedList.add(key);
                            }
                        }
                        if (mCheckedList.size() != 0) {
                            mDownLoadList.clear();
                            for (int position : mCheckedList) {
                                final AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity
                                        = mVideoList.get(position - 1);
                                mDownLoadList.add(videosEntity);
                            }
                            if (mOnDownLoadClickListener != null && mDownLoadList.size() != 0) {
                                mOnDownLoadClickListener.onClick(v,
                                        position, mDownLoadList);
                            }
                        }
                    }

                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mIsShowDlCheckBox) {
            return mVideoList.size() + 2;
        } else if (mVideoList.size() != 0) {
            return mVideoList.size() + 1;
        }
        return 0;
    }
}