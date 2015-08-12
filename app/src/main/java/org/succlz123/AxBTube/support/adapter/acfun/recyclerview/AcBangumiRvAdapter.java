package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

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
import org.succlz123.AxBTube.support.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/12.
 */
public class AcBangumiRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CONTENT_INFO_TITLE_INFO = 0;
    private static final int CONTENT_INFO_VIDEO_ITEM = 1;

    private AcBangumi mAcBangumi;
    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType);
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
    public int getItemViewType(int position) {

        return CONTENT_INFO_VIDEO_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View titleInfoView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_content_title_info, parent, false);
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_bangumi, parent, false);

//        if (viewType == CONTENT_INFO_TITLE_INFO) {
//            return new CardViewHolder(titleInfoView);
//        } else if (viewType == CONTENT_INFO_VIDEO_ITEM) {
//            return new VideoItemViewHolder(videoItemView);
//        }
        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mAcBangumi != null) {
            AcBangumi.DataEntity.ListEntity entity = mAcBangumi.getData().getList().get(position);

            LogUtils.e(entity.getCover());

            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).imgBangumi.setImageURI(Uri.parse(entity.getCover()));
                ((CardViewHolder) holder).tvTitle.setText(entity.getTitle());
                ((CardViewHolder) holder).tvNum.setText(entity.getLastVideoName());

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
}
