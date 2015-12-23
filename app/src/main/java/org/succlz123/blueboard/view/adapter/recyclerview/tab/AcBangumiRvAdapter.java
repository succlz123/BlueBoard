package org.succlz123.blueboard.view.adapter.recyclerview.tab;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.bean.acfun.AcBangumi;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.view.adapter.base.BaseRvViewHolder;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by succlz123 on 15/8/12.
 */
public class AcBangumiRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AcBangumi mAcBangumi;
    private OnClickListener mOnClickListener;

    public AcBangumi getmAcBangumi() {
        return mAcBangumi;
    }

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setBangumiInfo(AcBangumi acBangumi) {
        this.mAcBangumi = acBangumi;
        notifyDataSetChanged();
    }

    public class CardViewHolder extends BaseRvViewHolder {
        private SimpleDraweeView imgBangumi;
        private TextView tvTitle;
        private TextView tvNum;
        private CardView cvBangumi;

        public CardViewHolder(View itemView) {
            super(itemView);
            imgBangumi = f(itemView, R.id.ac_rv_bangumi_img);
            tvTitle = f(itemView, R.id.ac_rv_bangumi_title_tv);
            tvNum = f(itemView, R.id.ac_rv_bangumi_num_tv);
            cvBangumi = f(itemView, R.id.cv_bangumi);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_bangumi, parent, false);

        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcBangumi != null) {
            final AcBangumi.DataEntity.ListEntity entity = mAcBangumi.getData().getList().get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).imgBangumi.setImageURI(Uri.parse(entity.getCover()));
                ((CardViewHolder) holder).tvTitle.setText(entity.getTitle());
                ((CardViewHolder) holder).tvNum.setText("更新至 " + entity.getLastVideoName());
                ((CardViewHolder) holder).cvBangumi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onClick(v, position, entity.getId());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcBangumi != null) {
            return mAcBangumi.getData().getPageSize();
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
            int marginRight = GlobalUtils.dip2px(parent.getContext(), 8f);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            } else if (position == 1) {
                outRect.set(0, marginRight, 0, 0);
            }
        }
    }
}
