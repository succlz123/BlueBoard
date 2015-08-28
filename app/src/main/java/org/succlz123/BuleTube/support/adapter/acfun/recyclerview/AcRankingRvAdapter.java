package org.succlz123.BuleTube.support.adapter.acfun.recyclerview;

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

import org.succlz123.BuleTube.MyApplication;
import org.succlz123.BuleTube.R;
import org.succlz123.BuleTube.bean.acfun.AcReOther;
import org.succlz123.BuleTube.support.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/17.
 */
public class AcRankingRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AcReOther mAcReOther;
    private List<AcReOther.DataEntity.PageEntity.ListEntity> mAcReOtherList = new ArrayList();

    public AcReOther getmAcReOther() {
        return mAcReOther;
    }

    public void setmAcReOther(AcReOther acReOther) {
        this.mAcReOther = acReOther;
        mAcReOtherList.clear();
        mAcReOtherList.addAll(acReOther.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addAcReOtherDate(AcReOther acReOther) {
        mAcReOtherList.addAll(acReOther.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class RankingVH extends RecyclerView.ViewHolder {
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

        public RankingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_vertical_with_click_info, parent, false);

        return new RankingVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RankingVH && mAcReOtherList.size() != 0) {

            final AcReOther.DataEntity.PageEntity.ListEntity entity = mAcReOtherList.get(position);
            ((RankingVH) holder).imgCoverHot
                    .setImageURI(Uri.parse(entity.getCover()));
            ((RankingVH) holder).tvTitleHot
                    .setText(entity.getTitle());
            ((RankingVH) holder).tvClickHot
                    .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
            ((RankingVH) holder).tvReplyHot
                    .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
            ((RankingVH) holder).cvVerticalWithClickInfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(v, position, entity.getContentId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mAcReOtherList.size();
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
