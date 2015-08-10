package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/4.
 */
public class AcContentInfoRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CONTENT_INFO_TITLE_INFO = 0;
    private static final int CONTENT_INFO_VIDEO_ITEM = 1;

    private AcContent mAcContent;
    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setContentInfo(AcContent acContent) {
        this.mAcContent = acContent;
        notifyDataSetChanged();
    }

    public class TitleInfoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_content_info_title)
        TextView tvTitle;
        @Bind(R.id.ac_recycle_view_content_info_description)
        TextView tvDescription;
        @Bind(R.id.ac_recycle_view_content_info_click)
        TextView tvClick;
        @Bind(R.id.ac_recycle_view_content_info_stows)
        TextView tvStows;
        @Bind(R.id.ac_recycle_view_content_info_up_img)
        SimpleDraweeView simpleDraweeView;
        @Bind(R.id.ac_recycle_view_content_info_up_name)
        TextView tvName;
        @Bind(R.id.ac_recycle_view_content_info_up_level)
        TextView tvUid;
        @Bind(R.id.cv_content_title_info_frame_layout)
        FrameLayout frameLayout;

        public TitleInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VideoItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_content_info_video)
        TextView tvVideo;
        @Bind(R.id.cv_content_video_item)
        CardView cardView;

        public VideoItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CONTENT_INFO_TITLE_INFO;
        } else {
            return CONTENT_INFO_VIDEO_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View titleInfoView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_content_title_info, parent, false);
        View videoItemView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_content_video_item, parent, false);

        if (viewType == CONTENT_INFO_TITLE_INFO) {
            return new TitleInfoViewHolder(titleInfoView);
        } else if (viewType == CONTENT_INFO_VIDEO_ITEM) {
            return new VideoItemViewHolder(videoItemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcContent != null) {
            final AcContent.DataEntity.FullContentEntity contentEntity = mAcContent.getData().getFullContent();

            if (holder instanceof TitleInfoViewHolder) {
                ((TitleInfoViewHolder) holder).tvTitle
                        .setText(contentEntity.getTitle());
                ((TitleInfoViewHolder) holder).tvDescription
                        .setText(Html.fromHtml(contentEntity.getDescription()));
                ((TitleInfoViewHolder) holder).tvClick
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + contentEntity.getViews());
                ((TitleInfoViewHolder) holder).tvStows
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.stows) + " " + contentEntity.getStows());
                ((TitleInfoViewHolder) holder).simpleDraweeView
                        .setImageURI(Uri.parse(contentEntity.getUser().getUserImg()));
                ((TitleInfoViewHolder) holder).tvName
                        .setText(contentEntity.getUser().getUsername());
                ((TitleInfoViewHolder) holder).tvUid
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.uid) + " " + contentEntity.getUser().getUserId());

                if (mOnClickListener != null) {
                    ((TitleInfoViewHolder) holder).frameLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onClick(v,
                                    position,
                                    String.valueOf(contentEntity.getUser().getUserId()),
                                    null, null, null, null);
                        }
                    });
                }
            } else if (holder instanceof VideoItemViewHolder) {
                if (mAcContent != null) {
                    final AcContent.DataEntity.FullContentEntity.VideosEntity videosEntity
                            = contentEntity.getVideos().get(position - 1);

                    ((VideoItemViewHolder) holder).tvVideo
                            .setText((position) + ". " + videosEntity.getName());

                    if (mOnClickListener != null) {
                        ((VideoItemViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v,
                                        position,
                                        null,
                                        String.valueOf(videosEntity.getVideoId()),
                                        String.valueOf(videosEntity.getDanmakuId()),
                                        videosEntity.getSourceId(),
                                        videosEntity.getType());
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcContent != null) {
            return mAcContent.getData().getFullContent().getVideos().size() + 1;
        }
        return 0;
    }
}
