package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.graphics.Color;
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
import org.succlz123.AxBTube.bean.acfun.AcContentReply;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/4.
 */
public class AcContentReplyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CONTENT_INFO_TITLE_INFO = 0;
    private static final int CONTENT_INFO_VIDEO_ITEM = 1;

    private AcContentReply mAcContentReply;
    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String userId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setContentReply(AcContentReply acContentReply) {
        this.mAcContentReply = acContentReply;
        notifyDataSetChanged();
    }

    public class TitleInfoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ac_recycle_view_content_reply_name_tv)
        TextView tvName;
        @Bind(R.id.ac_recycle_view_content_reply_time_tv)
        TextView tvTime;
        @Bind(R.id.ac_recycle_view_content_reply_reply_tv)
        TextView tvReply;

        @Bind(R.id.ac_recycle_view_content_reply_user_img)
        SimpleDraweeView userImg;

        @Bind(R.id.llll)
        LinearLayout linearLayout;

//        @Bind(R.id.cv_content_title_info_frame_layout)
//        FrameLayout frameLayout;

        public TitleInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

//    public class VideoItemViewHolder extends RecyclerView.ViewHolder {
//        @Bind(R.id.ac_recycle_view_content_info_video)
//        TextView tvVideo;
//        @Bind(R.id.cv_content_video_item)
//        CardView cardView;
//
//        public VideoItemViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }

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
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_content_reply, parent, false);
//        View videoItemView
//                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_content_video_item, parent, false);


        return new TitleInfoViewHolder(titleInfoView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcContentReply != null) {

            if (holder instanceof TitleInfoViewHolder) {
                String userUid = String.valueOf(mAcContentReply.getData().getPage().getList().get(position));

                AcContentReply.DataEntity.Entity entity = mAcContentReply.getData().getPage().getMap().get("c" + userUid);

                ((TitleInfoViewHolder) holder).userImg
                        .setImageURI(Uri.parse(entity.getAvatar()));
                ((TitleInfoViewHolder) holder).tvName
                        .setText("#" + (entity.getFloor() + " " + entity.getUsername()));
                ((TitleInfoViewHolder) holder).tvTime
                        .setText(GlobalUtils.getDateToString(entity.getTime()));
                ((TitleInfoViewHolder) holder).tvReply
                        .setText(Html.fromHtml(entity.getContent()));


                for (int i = 0; i < entity.getDeep() - 1; i++) {

                    if (entity.getDeep() != 0) {
                        entity = mAcContentReply.getData().getPage().getMap().get("c" + entity.getQuoteId());

                        TextView textView = new TextView(MyApplication.getsInstance().getApplicationContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textView.setLayoutParams(layoutParams);
                        textView.setTextColor(Color.BLACK);
                        textView.setText(entity.getContent());
                        ((TitleInfoViewHolder) holder).linearLayout.addView(textView);


                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcContentReply != null) {
            return mAcContentReply.getData().getPage().getList().size();
        }
        return 0;
    }
}
