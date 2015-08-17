package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/17.
 */
public class AcHotRankingRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AcReOther mAcReOther;
    private AcReHot mAcReHot;

    private List<AcReOther.DataEntity.PageEntity.ListEntity> mEntityList = new ArrayList();

    public AcReOther getmAcReOther() {
        return mAcReOther;
    }

    public AcReHot getmAcReHot() {
        return mAcReHot;
    }

    private OnClickListener mOnClickListener;

    public void setmAcReOther(AcReOther acReOther) {
        this.mAcReOther = acReOther;
        mEntityList.addAll(acReOther.getData().getPage().getList());
        notifyDataSetChanged();
    }

//    public void setmAcLastPost(AcReOther acLastPost) {
//        this.mAcLastPost = acLastPost;
//        //下拉时保证重新填充
//        mEntityList.clear();
//        mEntityList.addAll(mAcLastPost.getData().getPage().getList());
//        notifyDataSetChanged();
//    }

    public void setmAcReHot(AcReHot acReHot) {
        this.mAcReHot = acReHot;
        notifyDataSetChanged();
    }

    public void addDate(AcReOther acMostPopular) {
        mEntityList.addAll(acMostPopular.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class HotRankingVH extends RecyclerView.ViewHolder {
        @Bind(R.id.cv_vertical_with_click_info_tv_title)
        TextView tvTitleHot;

        @Bind(R.id.cv_vertical_with_click_info_tv_click)
        TextView tvClickHot;

        @Bind(R.id.cv_vertical_with_click_info_tv_reply)
        TextView tvReplyHot;

        @Bind(R.id.cv_vertical_with_click_info_img)
        SimpleDraweeView imgCoverHot;

        @Bind(R.id.cv_vertical_with_click_info)
        CardView cvVerticalWithClickInfo;

        public HotRankingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View hot
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_vertical_with_click_info, parent, false);

        return new HotRankingVH(hot);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mEntityList.size() != 0 && holder instanceof HotRankingVH) {
            final AcReOther.DataEntity.PageEntity.ListEntity entity = mEntityList.get(position);

            ((HotRankingVH) holder).imgCoverHot
                    .setImageURI(Uri.parse(entity.getCover()));
            ((HotRankingVH) holder).tvTitleHot
                    .setText(entity.getTitle());
            ((HotRankingVH) holder).tvClickHot
                    .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
            ((HotRankingVH) holder).tvReplyHot
                    .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
            ((HotRankingVH) holder).cvVerticalWithClickInfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(v, position, entity.getContentId());
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        return mEntityList.size();
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
            int marginRight = GlobalUtils.dip2pix(parent.getContext(), 8);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
