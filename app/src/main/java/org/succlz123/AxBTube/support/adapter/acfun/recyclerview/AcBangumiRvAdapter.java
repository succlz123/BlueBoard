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

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcBangumi;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_bangumi_img)
        SimpleDraweeView imgBangumi;

        @Bind(R.id.ac_recycle_view_bangumi_title_tv)
        TextView tvTitle;

        @Bind(R.id.ac_recycle_view_bangumi_num_tv)
        TextView tvNum;

        @Bind(R.id.cv_bangumi)
        CardView cvBangumi;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_bangumi, parent, false);

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
            int marginRight = GlobalUtils.dip2pix(parent.getContext(), 8f);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            } else if (position == 1) {
                outRect.set(0, marginRight, 0, 0);
            }
        }
    }
}
