package org.succlz123.blueboard.view.adapter.recyclerview.other;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseRvViewHolder;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;
import org.succlz123.blueboard.model.utils.common.OkUtils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    public class RankingVH extends BaseRvViewHolder {
        private TextView tvTitleHot;
        private TextView tvClickHot;
        private TextView tvReplyHot;
        private SimpleDraweeView imgCoverHot;
        private CardView cvVerticalWithClickInfo;

        public RankingVH(View itemView) {
            super(itemView);
            tvTitleHot = f(itemView, R.id.cv_vertical_with_click_info_tv_title);
            tvClickHot = f(itemView, R.id.cv_vertical_with_click_info_tv_click);
            tvReplyHot = f(itemView, R.id.cv_vertical_with_click_info_tv_reply);
            imgCoverHot = f(itemView, R.id.cv_vertical_with_click_info_img);
            cvVerticalWithClickInfo = f(itemView, R.id.cv_vertical_with_click_info);
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
                    .setText(MyApplication.getInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
            ((RankingVH) holder).tvReplyHot
                    .setText(MyApplication.getInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
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
            int marginRight = OkUtils.dp2px(parent.getContext(), 8);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
