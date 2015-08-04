package org.succlz123.AxBTube.support.adapter.acfun;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class AcContentInfoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CONTENT_INFO_TITLE = 0;
    private static final int CONTENT_INFO_UP = 1;
    private static final int CONTENT_INFO_VIDEO = 2;

    private AcContent mAcContent;

    public void setContentInfo(AcContent acContent) {
        this.mAcContent = acContent;
        notifyDataSetChanged();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_content_info_title)
        TextView tvTitle;
        @Bind(R.id.ac_recycle_view_content_info_description)
        TextView tvDescription;
        @Bind(R.id.ac_recycle_view_content_info_click)
        TextView tvClick;
        @Bind(R.id.ac_recycle_view_content_info_stows)
        TextView tvStows;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class UpViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_content_info_up_img)
        SimpleDraweeView simpleDraweeView;
        @Bind(R.id.ac_recycle_view_content_info_up_name)
        TextView tvName;
        @Bind(R.id.ac_recycle_view_content_info_up_level)
        TextView tvLevel;

        public UpViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_content_info_video)
        TextView tvVideo;
        @Bind(R.id.ac_recycle_view_content_info_video_ll)
        LinearLayout llVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return CONTENT_INFO_TITLE;
            case 1:
                return CONTENT_INFO_UP;
            default:
                return CONTENT_INFO_VIDEO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View titleView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_title, parent, false);
        View upView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_up, parent, false);
        View ViewView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_video, parent, false);

        if (viewType == CONTENT_INFO_TITLE) {
            return new TitleViewHolder(titleView);
        } else if (viewType == CONTENT_INFO_UP) {
            return new UpViewHolder(upView);
        } else {
            return new VideoViewHolder(ViewView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            TextView tvTitle = ((TitleViewHolder) holder).tvTitle;
            TextView tvDescription = ((TitleViewHolder) holder).tvDescription;
            TextView tvClick = ((TitleViewHolder) holder).tvClick;
            TextView tvStows = ((TitleViewHolder) holder).tvStows;
            if (mAcContent != null) {
                String title = mAcContent.getData().getFullContent().getTitle();
                String description = mAcContent.getData().getFullContent().getDescription();
                int click = mAcContent.getData().getFullContent().getViews();
                int stows = mAcContent.getData().getFullContent().getStows();

                tvTitle.setText(title);
                tvDescription.setText(Html.fromHtml(description));
                tvClick.setText(MyApplication.getInstance().getApplicationContext().getString(R.string.click) + " " + click);
                tvStows.setText(MyApplication.getInstance().getApplicationContext().getString(R.string.stows) + " " + stows);
            }
        } else if (holder instanceof UpViewHolder) {
            SimpleDraweeView simpleDraweeView = ((UpViewHolder) holder).simpleDraweeView;
            TextView tvName = ((UpViewHolder) holder).tvName;
            TextView tvLevel = ((UpViewHolder) holder).tvLevel;
            if (mAcContent != null) {
                String userImg = mAcContent.getData().getFullContent().getUser().getUserImg();
                String username = mAcContent.getData().getFullContent().getUser().getUsername();
                int userId = mAcContent.getData().getFullContent().getUser().getUserId();

                Uri uri = Uri.parse(userImg);
                simpleDraweeView.setImageURI(uri);
                tvName.setText(username);
                tvLevel.setText(MyApplication.getInstance().getApplicationContext().getString(R.string.uid) + " " + userId);
            }
        } else if (holder instanceof VideoViewHolder) {
            TextView tvVideo = ((VideoViewHolder) holder).tvVideo;
            LinearLayout llVideo = ((VideoViewHolder) holder).llVideo;
            if (mAcContent != null) {
                if (position == 2) {
                    tvVideo.setText(MyApplication.getInstance().getApplicationContext().getString(R.string.gongyou)
                            + mAcContent.getData().getFullContent().getVideos().size()
                            + MyApplication.getInstance().getApplicationContext().getString(R.string.geshipin));
                    llVideo.setBackgroundColor(
                            MyApplication.getInstance().getApplicationContext().getResources()
                                    .getColor(R.color.window_background));
                } else {
                    tvVideo.setText((position - 2)
                            + ". "
                            + mAcContent.getData().getFullContent().getVideos().get(position - 3).getName());
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mAcContent != null) {
            return mAcContent.getData().getFullContent().getVideos().size() + 3;
        }
        return 0;
    }
}
