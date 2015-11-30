package org.succlz123.blueboard.controller.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.bean.acfun.AcReHot;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 2015/8/9.
 */
public class AcPartitionRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITle = 0;
    private static final int TYPE_HOT = 1;
    private static final int TYPE_OTHER = 2;

    private AcReOther mAcMostPopular;
    private AcReOther mAcLastPost;
    private AcReHot mAcReHot;

    private List<AcReOther.DataEntity.PageEntity.ListEntity> mEntityList = new ArrayList();

    public AcReOther getAcMostPopular() {
        return mAcMostPopular;
    }

    public AcReOther getAcLastPost() {
        return mAcLastPost;
    }

    public AcReHot getAcReHot() {
        return mAcReHot;
    }

    private OnClickListener mOnClickListener;

    public void setAcMostPopular(AcReOther acMostPopular) {
        this.mAcMostPopular = acMostPopular;
        notifyDataSetChanged();
    }

    public void setAcLastPost(AcReOther acLastPost) {
        this.mAcLastPost = acLastPost;
        //下拉时保证重新填充
        mEntityList.clear();
        mEntityList.addAll(mAcLastPost.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void setAcReHot(AcReHot acReHot) {
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

    public class TitleVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_rv_tv_title)
        TextView tvTitle;

        public TitleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MostPopularVH extends RecyclerView.ViewHolder {
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

        public MostPopularVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LastPostVH extends RecyclerView.ViewHolder {
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

        public LastPostVH(View itemView) {
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
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_title, parent, false);
        View hot
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_vertical_with_click_info, parent, false);
        View other
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_horizontal_with_click_info, parent, false);

        if (viewType == TYPE_TITle) {
            return new TitleVH(title);
        } else if (viewType == TYPE_HOT) {
            return new MostPopularVH(hot);
        } else if (viewType == TYPE_OTHER) {
            return new LastPostVH(other);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleVH) {
            TextView tvTitle = ((TitleVH) holder).tvTitle;
            if (position == 0) {
                tvTitle.setText(AcString.TITLE_HOT);
            } else {
                Context context = MyApplication.getsInstance().getApplicationContext();
                SharedPreferences settings
                        = context.getSharedPreferences(context.getString(R.string.app_name), Activity.MODE_PRIVATE);
                String title = settings.getString(AcString.TITLE, AcString.TITLE_TIME_ORDER);
                tvTitle.setText(title);
            }
        } else if (holder instanceof MostPopularVH) {
            if (position == 1 | position == 2 | position == 3 | position == 4 && mAcMostPopular != null) {
                final AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcMostPopular.getData().getPage().getList().get(position - 1);

                ((MostPopularVH) holder).imgCoverHot
                        .setImageURI(Uri.parse(entity.getCover()));
                ((MostPopularVH) holder).tvTitleHot
                        .setText(entity.getTitle());
                ((MostPopularVH) holder).tvClickHot
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((MostPopularVH) holder).tvReplyHot
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
                ((MostPopularVH) holder).cvVerticalWithClickInfo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onClick(v, position, String.valueOf(entity.getContentId()));
                    }
                });
            }
        } else if (holder instanceof LastPostVH) {
            if (mEntityList.size() != 0) {
                final AcReOther.DataEntity.PageEntity.ListEntity entity = mEntityList.get(position - 6);

                ((LastPostVH) holder).imgCoverOther
                        .setImageURI(Uri.parse(entity.getCover()));
                ((LastPostVH) holder).tvTitleOther
                        .setText(entity.getTitle());
                ((LastPostVH) holder).tvUpOther
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.up) + " " + entity.getUser().getUsername());
                ((LastPostVH) holder).tvClickOther
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((LastPostVH) holder).tvReplyOther
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());

                if (mOnClickListener != null) {
                    ((LastPostVH) holder).cvHorizontal.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onClick(v, position, entity.getContentId());
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mEntityList.size() != 0) {
            return mEntityList.size() + 6;
        }
        return 0;
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
    public static class MyDecoration extends RecyclerView.ItemDecoration {

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
