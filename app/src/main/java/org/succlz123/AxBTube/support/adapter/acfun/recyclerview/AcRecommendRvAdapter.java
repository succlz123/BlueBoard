package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.AcRecommendBannerAdapter;
import org.succlz123.AxBTube.support.callback.GetAcRecommendHttpResult;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/26.
 */
public class AcRecommendRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements GetAcRecommendHttpResult {
    private static final int TYPE_VIEW_PAGER = 0;
    private static final int TYPE_NAVIGATION_TITLE = 1;
    private static final int TYPE_CARD_VIEW = 2;
    private static final int TYPE_CARD_VIEW_OTHER = 3;

    private AcReBanner mAcReBanner;
    private AcReHot mAcReHot;

    private AcReOther mAcReAnimation;
    private AcReOther mAcReFun;
    private AcReOther mAcReMusic;
    private AcReOther mAcReGame;
    private AcReOther mAcReScience;
    private AcReOther mAcReSport;
    private AcReOther mAcReTv;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public void setSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    public AcReBanner getAcReBanner() {
        return mAcReBanner;
    }

    public AcReHot getAcReHot() {
        return mAcReHot;
    }

    public AcReOther getAcReAnimation() {
        return mAcReAnimation;
    }

    public AcReOther getAcReFun() {
        return mAcReFun;
    }

    public AcReOther getAcReMusic() {
        return mAcReMusic;
    }

    public AcReOther getAcReGame() {
        return mAcReGame;
    }

    public AcReOther getAcReScience() {
        return mAcReScience;
    }

    public AcReOther getAcReSport() {
        return mAcReSport;
    }

    public AcReOther getAcReTv() {
        return mAcReTv;
    }

    @Override
    public void onReBannerResult(AcReBanner result) {
        this.mAcReBanner = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReHotResult(AcReHot result) {
        this.mAcReHot = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReAnimationResult(AcReOther result) {
        this.mAcReAnimation = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReFunResult(AcReOther result) {
        this.mAcReFun = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReMusicResult(AcReOther result) {
        this.mAcReMusic = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReGameResult(AcReOther result) {
        this.mAcReGame = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReScienceResult(AcReOther result) {
        this.mAcReScience = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReSportResult(AcReOther result) {
        this.mAcReSport = result;
        notifyDataSetChanged();
    }

    @Override
    public void onAcReTvResult(AcReOther result) {
        this.mAcReTv = result;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClick(View view, String partitionType, String contentId);
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    //首页横幅
    public class ViewPagerVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_viewpager_banner)
        ViewPager vpBanner;

        @Bind(R.id.ac_viewpager_dots)
        LinearLayout llDots;

        @Bind(R.id.ac_recommend_banner)
        CardView cardView;

        public ViewPagerVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //分区标题
    public class NavigationTitleVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_fragment_re_partition_title_tv)
        TextView tvPartitionTitle;

        @Bind(R.id.ac_fragment_re_partition_title_btn)
        Button btnPartitionMore;

        public NavigationTitleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //视图卡片 1
    public class HotVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_card_view_tv_1)
        TextView tvTitleHot;

        @Bind(R.id.ac_card_view_tv_2)
        TextView tvSubTitleHot;

        @Bind(R.id.ac_card_view_img)
        SimpleDraweeView imgCoverHot;

        @Bind(R.id.ac_fragment_re_card_view)
        CardView cardViewHot;

        public HotVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //视图卡片 2
    public class OtherVH extends RecyclerView.ViewHolder {
        @Bind(R.id.cv_vertical_with_click_info_tv_title)
        TextView tvTitleOther;

        @Bind(R.id.cv_vertical_with_click_info_tv_click)
        TextView tvSubTitleOtherLeft;

        @Bind(R.id.cv_vertical_with_click_info_tv_reply)
        TextView tvSubTitleOtherRight;

        @Bind(R.id.cv_vertical_with_click_info_img)
        SimpleDraweeView imgCoverOther;

        @Bind(R.id.cv_vertical_with_click_info)
        CardView cardViewOther;

        public OtherVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_VIEW_PAGER;
        } else if (getTitleType(position)) {
            return TYPE_NAVIGATION_TITLE;
        } else if (position == 2 | position == 3 | position == 4 | position == 5) {
            return TYPE_CARD_VIEW;
        }
        return TYPE_CARD_VIEW_OTHER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewPage
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_recommend_viewpager, parent, false);
        View title
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_title_with_button, parent, false);
        View hot
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_vertical_with_subtitle, parent, false);
        View other
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_vertical_with_click_info, parent, false);

        if (viewType == TYPE_VIEW_PAGER) {
            return new ViewPagerVH(viewPage);
        } else if (viewType == TYPE_NAVIGATION_TITLE) {
            return new NavigationTitleVH(title);
        } else if (viewType == TYPE_CARD_VIEW) {
            return new HotVH(hot);
        } else if (viewType == TYPE_CARD_VIEW_OTHER) {
            return new OtherVH(other);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewPagerVH) {
            if (mAcReBanner != null && mOnClickListener != null) {
                final ViewPager viewPager = ((ViewPagerVH) holder).vpBanner;
                AcRecommendBannerAdapter adapter = new AcRecommendBannerAdapter(mAcReBanner,
                        viewPager,
                        ((ViewPagerVH) holder).llDots,
                        mSwipeRefreshLayout,
                        mOnClickListener);
                viewPager.setAdapter(adapter);
                viewPager.setFocusable(true);
                viewPager.requestFocus();

                viewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_MOVE:
                                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                                break;
                        }
                        return false;
                    }
                });
            }
        } else if (holder instanceof NavigationTitleVH) {
            ((NavigationTitleVH) holder).tvPartitionTitle.setText(AcString.getTitle(position));
            if (mOnClickListener != null) {
                ((NavigationTitleVH) holder).btnPartitionMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onClick(v, AcString.getTitle(position), null);
                    }
                });
            }
        } else if (holder instanceof HotVH) {
            if (position == 2 | position == 3 | position == 4 | position == 5 && mAcReHot != null) {
                final AcReHot.DataEntity.PageEntity.ListEntity entity
                        = mAcReHot.getData().getPage().getList().get(position - 2);

                ((HotVH) holder).imgCoverHot.setImageURI(Uri.parse(entity.getCover()));
                ((HotVH) holder).tvTitleHot.setText(entity.getTitle());
                ((HotVH) holder).tvSubTitleHot.setText(entity.getSubtitle());

                if (mOnClickListener != null) {
                    ((HotVH) holder).cardViewHot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onClick(v, null, entity.getSpecialId());
                        }
                    });
                }
            } else {
                ((HotVH) holder).imgCoverHot.setImageURI(null);
                ((HotVH) holder).tvTitleHot.setText("");
                ((HotVH) holder).tvSubTitleHot.setText("");

                if (mOnClickListener != null) {
                    ((HotVH) holder).cardViewHot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        } else if (holder instanceof OtherVH) {
            if (position == 7 | position == 8 && mAcReAnimation != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity =
                        mAcReAnimation.getData().getPage().getList().get(position - 7);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 10 | position == 11 && mAcReFun != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity =
                        mAcReFun.getData().getPage().getList().get(position - 10);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 13 | position == 14 && mAcReMusic != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcReMusic.getData().getPage().getList().get(position - 13);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 16 | position == 17 && mAcReGame != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcReGame.getData().getPage().getList().get(position - 16);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 19 | position == 20 && mAcReScience != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcReScience.getData().getPage().getList().get(position - 19);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 22 | position == 23 && mAcReSport != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcReSport.getData().getPage().getList().get(position - 22);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else if (position == 25 | position == 26 && mAcReTv != null) {
                AcReOther.DataEntity.PageEntity.ListEntity entity
                        = mAcReTv.getData().getPage().getList().get(position - 25);

                setCardViewInfo(entity,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        entity.getContentId());
            } else {
                setCardViewInfo(null,
                        ((OtherVH) holder).imgCoverOther,
                        ((OtherVH) holder).tvTitleOther,
                        ((OtherVH) holder).tvSubTitleOtherLeft,
                        ((OtherVH) holder).tvSubTitleOtherRight);
                setCardViewOtherOnClickListener(((OtherVH) holder).cardViewOther,
                        null,
                        null);
            }
        }
//        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return 27;
    }

    //根据position判断是否显示分区标题
    public boolean getTitleType(int position) {
        if (position == 1 | position == 6 | position == 9 | position == 12
                | position == 15 | position == 18 | position == 21 | position == 24) {
            return true;
        }
        return false;
    }

    public void setCardViewInfo(AcReOther.DataEntity.PageEntity.ListEntity entity,
                                SimpleDraweeView imgCoverOther,
                                TextView tvTitleOther,
                                TextView tvSubTitleOtherLeft,
                                TextView tvSubTitleOtherRight) {
        if (entity != null) {
            imgCoverOther.setImageURI(Uri.parse(entity.getCover()));
            tvTitleOther.setText(entity.getTitle());
            tvSubTitleOtherLeft.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
            tvSubTitleOtherRight.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
        } else {
            imgCoverOther.setImageURI(null);
            tvTitleOther.setText("");
            tvSubTitleOtherLeft.setText("");
            tvSubTitleOtherRight.setText("");
        }
    }

    public void setCardViewOtherOnClickListener(CardView cardViewOther, final String partitionType, final String contentId) {
        if (mOnClickListener != null) {
            if (partitionType == null && contentId == null) {
                cardViewOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            cardViewOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(v, partitionType, contentId);
                }
            });
        }
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
            if (position == 2 | position == 4 | position == 7 | position == 10 | position == 13
                    | position == 16 | position == 19 | position == 22 | position == 25) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}
