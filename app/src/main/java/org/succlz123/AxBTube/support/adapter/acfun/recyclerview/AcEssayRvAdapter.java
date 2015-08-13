package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcEssay;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/13.
 */
public class AcEssayRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AcEssay mAcEssay;

    public AcEssay getmAcEssay() {
        return mAcEssay;
    }

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setEssayInfo(AcEssay acEssay) {
        this.mAcEssay = acEssay;
        notifyDataSetChanged();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_essay_cv_title)
        TextView tvTitle;

        @Bind(R.id.ac_recycle_view_essay_cv_name)
        TextView tvName;

        @Bind(R.id.ac_recycle_view_essay_cv_time)
        TextView tvTime;

        @Bind(R.id.ac_recycle_view_essay_cv_click)
        TextView tvClick;

        @Bind(R.id.ac_recycle_view_essay_cv_reply)
        TextView tvReply;


        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_essay, parent, false);

        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcEssay != null) {
            AcEssay.DataEntity.PageEntity.ListEntity entity = mAcEssay.getData().getPage().getList().get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).tvTitle.setText(entity.getTitle());
                ((CardViewHolder) holder).tvName.setText( entity.getUser().getUsername());
                ((CardViewHolder) holder).tvTime.setText(GlobalUtils.getDateToStringWithYDHM(entity.getReleaseDate()));
                ((CardViewHolder) holder).tvClick.setText("点击 "+entity.getViews());
                ((CardViewHolder) holder).tvReply.setText("回复 "+entity.getComments());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcEssay != null) {
            return mAcEssay.getData().getPage().getPageSize();
        }
        return 0;
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2pix(parent.getContext(), 8f);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            } else if (position == 1) {
                outRect.set(0, marginRight, 0, 0);
            }
        }
    }
}
