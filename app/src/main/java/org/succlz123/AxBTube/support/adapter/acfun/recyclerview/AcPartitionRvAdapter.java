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
import org.succlz123.AxBTube.support.callback.GetAcPartitionHttpResult;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/8/9.
 */
public class AcPartitionRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements GetAcPartitionHttpResult {
    private static final int TYPE_TITle = 0;
    private static final int TYPE_HOT = 1;
    private static final int TYPE_OTHER = 2;

    private AcReOther mAcHot;
    private AcReOther mAcLastPost;
    private AcReHot mAcReHot;

    public AcReOther getmAcHot() {
        return mAcHot;
    }

    public AcReOther getmAcLastPost() {
        return mAcLastPost;
    }

    public AcReHot getmAcReHot() {
        return mAcReHot;
    }

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onPartitionHotResult(AcReOther result) {
        this.mAcHot = result;
        notifyDataSetChanged();
    }

    @Override
    public void onPartitionLastPostResult(AcReOther result) {
        this.mAcLastPost = result;
        notifyDataSetChanged();
    }

    @Override
    public void onHotResult(AcReHot result) {
        this.mAcReHot = result;
        notifyDataSetChanged();
    }

    public class TitleVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_tv_title)
        TextView tvTitle;

        public TitleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HotVH extends RecyclerView.ViewHolder {
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

        public HotVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class OtherVH extends RecyclerView.ViewHolder {
        @Bind(R.id.cv_horizontal_tv_title)
        TextView tvTitleOther;

        @Bind(R.id.cv_horizontal_tv_up)
        TextView tvUpOther;

        @Bind(R.id.cv_horizontal_tv_click)
        TextView tvClickOther;

        @Bind(R.id.cv_horizontal_tv_reply)
        TextView tvReplyOther;

        @Bind(R.id.cv_horizontal_img_cover)
        SimpleDraweeView imgCoverOther;

        @Bind(R.id.cv_horizontal)
        CardView cvHorizontal;

        public OtherVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mAcReHot == null) {
            if (getTitleType(position)) {
                return TYPE_TITle;
            } else if (getHotType(position)) {
                return TYPE_HOT;
            } else {
                return TYPE_OTHER;
            }
        }
        return TYPE_HOT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View title
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_title, parent, false);
        View hot
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_vertical_with_click_info, parent, false);
        View other
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_horizontal_with_click_info, parent, false);

        if (viewType == TYPE_TITle) {
            return new TitleVH(title);
        } else if (viewType == TYPE_HOT) {
            return new HotVH(hot);
        } else if (viewType == TYPE_OTHER) {
            return new OtherVH(other);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleVH) {
            TextView tvTitle = ((TitleVH) holder).tvTitle;
            if (position == 0) {
                tvTitle.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.the_most_popular));
            } else {
                tvTitle.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.latest_release));
            }
        } else if (holder instanceof HotVH) {
            if (position == 1 | position == 2 | position == 3 | position == 4 && mAcHot != null) {
                final AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcHot.getData().getPage().getList().get(position - 1);

                ((HotVH) holder).imgCoverHot
                        .setImageURI(Uri.parse(entity.getCover()));
                ((HotVH) holder).tvTitleHot
                        .setText(entity.getTitle());
                ((HotVH) holder).tvClickHot
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((HotVH) holder).tvReplyHot
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
                ((HotVH) holder).cvVerticalWithClickInfo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onClick(v, position, String.valueOf(entity.getContentId()));
                    }
                });
            }
        } else if (holder instanceof OtherVH) {
            if (mAcLastPost != null) {
                final AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcLastPost.getData().getPage().getList().get(position - 6);

                ((OtherVH) holder).imgCoverOther.setImageURI(Uri.parse(entity.getCover()));
                ((OtherVH) holder).tvTitleOther.setText(entity.getTitle());
                ((OtherVH) holder).tvUpOther.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.up) + " " + entity.getUser().getUsername());
                ((OtherVH) holder).tvClickOther.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((OtherVH) holder).tvReplyOther.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());

                if (mOnClickListener != null) {
                    ((OtherVH) holder).cvHorizontal.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onClick(v, position, String.valueOf(entity.getContentId()));
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mAcLastPost != null) {
            if (mAcLastPost.getData().getPage().getList().size() < 1) {
                return 16;
            }
            return 6 + mAcLastPost.getData().getPage().getList().size();
        }
        return 16;
    }

    //根据position判断是否显示间隔标题
    public boolean getTitleType(int position) {
        if (position == 0 | position == 5) {
            return true;
        }
        return false;
    }

    public boolean getHotType(int position) {
        if (position == 1 | position == 2 | position == 3 | position == 4) {
            return true;
        }
        return false;
    }

    //处理cardView中间的margin
    public static class PartitionDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2pix(parent.getContext(), 7);
            if (position == 1 | position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
