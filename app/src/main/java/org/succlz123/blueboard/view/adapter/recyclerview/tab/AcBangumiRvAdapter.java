package org.succlz123.blueboard.view.adapter.recyclerview.tab;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseRvViewHolder;
import org.succlz123.blueboard.model.bean.acfun.AcBangumi;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcBangumi != null) {
            final AcBangumi.DataEntity.ListEntity entity = mAcBangumi.getData().getList().get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).mImgBangumi.setImageURI(Uri.parse(entity.getCover()));
                ((CardViewHolder) holder).mTvTitle.setText(entity.getTitle());
                ((CardViewHolder) holder).mTvNum.setText("更新至 " + entity.getLastVideoName());
                ((CardViewHolder) holder).mCvBangumi.setOnClickListener(new View.OnClickListener() {
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
        return mAcBangumi != null ? mAcBangumi.getData().getPageSize() : 0;
    }

    private static class CardViewHolder extends BaseRvViewHolder {
        private SimpleDraweeView mImgBangumi;
        private TextView mTvTitle;
        private TextView mTvNum;
        private CardView mCvBangumi;

        private CardViewHolder(View itemView) {
            super(itemView);
            mImgBangumi = f(itemView, R.id.ac_rv_bangumi_img);
            mTvTitle = f(itemView, R.id.ac_rv_bangumi_title_tv);
            mTvNum = f(itemView, R.id.ac_rv_bangumi_num_tv);
            mCvBangumi = f(itemView, R.id.cv_bangumi);
        }

        private static CardViewHolder create(ViewGroup parent) {
            return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_bangumi, parent, false));
        }
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = OkUtils.dp2px(parent.getContext(), 8f);
            if (position % 2 == 0) {
                outRect.set(0, 0, marginRight, 0);
            } else if (position == 1) {
                outRect.set(0, marginRight, 0, 0);
            }
        }
    }
}
