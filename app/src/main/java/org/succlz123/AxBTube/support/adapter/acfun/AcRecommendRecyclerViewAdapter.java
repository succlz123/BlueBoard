package org.succlz123.AxBTube.support.adapter.acfun;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.callback.GetAcRecommendHttpResult;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/26.
 */
public class AcRecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
		implements GetAcRecommendHttpResult {
	private static final int TYPE_VIEW_PAGER = 0;
	private static final int TYPE_NAVIGATION_TITLE = 1;
	private static final int TYPE_CARD_VIEW = 2;
	private static final int TYPE_CARD_VIEW_OTHER = 3;

	private AcReBanner acReBanner;
	private AcReHot acReHot;

	private AcReOther acReAnimation;
	private AcReOther acReFun;
	private AcReOther acReMusic;
	private AcReOther acReGame;
	private AcReOther acReScience;
	private AcReOther acReSport;
	private AcReOther acReTv;

	@Override
	public void onReBannerResult(AcReBanner result) {
		this.acReBanner = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReHotResult(AcReHot result) {
		this.acReHot = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReAnimationResult(AcReOther result) {
		this.acReAnimation = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReFunResult(AcReOther result) {
		this.acReFun = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReMusicResult(AcReOther result) {
		this.acReMusic = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReGameResult(AcReOther result) {
		this.acReGame = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReScienceResult(AcReOther result) {
		this.acReScience = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReSportResult(AcReOther result) {
		this.acReSport = result;
		notifyDataSetChanged();
	}

	@Override
	public void onAcReTvResult(AcReOther result) {
		this.acReTv = result;
		notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position, String contentId);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	//首页横幅
	public class ViewPagerVH extends RecyclerView.ViewHolder {
		@Bind(R.id.ac_viewpager_banner)
		ViewPager vpBanner;

		@Bind(R.id.ac_viewpager_dots)
		LinearLayout llDots;

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
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_title_recommend, parent, false);
		View hot
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_vertical_with_subtitle, parent, false);
		View other
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_item_cardview_vertical_with_click_info, parent, false);

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
			if (acReBanner != null) {
				//放置指示圆点
				LinearLayout dotsLinearLayout = ((ViewPagerVH) holder).llDots;
				ViewPager viewPager = ((ViewPagerVH) holder).vpBanner;

				AcRecommendBannerAdapter adapter = new AcRecommendBannerAdapter(acReBanner, viewPager, dotsLinearLayout);
				viewPager.setAdapter(adapter);
			}
		} else if (holder instanceof NavigationTitleVH) {
			TextView tvPartitionTitle = ((NavigationTitleVH) holder).tvPartitionTitle;
			Button btnPartitionMore = ((NavigationTitleVH) holder).btnPartitionMore;

			tvPartitionTitle.setText(AcString.getTitle(position));
			if (mOnItemClickListener != null) {
				btnPartitionMore.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onItemClick(v, position, null);
					}
				});
			}
		} else if (holder instanceof HotVH) {
			SimpleDraweeView imgCoverHot = ((HotVH) holder).imgCoverHot;
			TextView tvTitleHot = ((HotVH) holder).tvTitleHot;
			TextView tvSubTitleHot = ((HotVH) holder).tvSubTitleHot;
			CardView cardViewHot = ((HotVH) holder).cardViewHot;

			if (position == 2 | position == 3 | position == 4 | position == 5 && acReHot != null) {
				final AcReHot.DataEntity.PageEntity.ListEntity entity
						= acReHot.getData().getPage().getList().get(position - 2);

				String url = entity.getCover();
				Uri uri = Uri.parse(url);
				imgCoverHot.setImageURI(uri);
				tvTitleHot.setText(entity.getTitle());
				tvSubTitleHot.setText(entity.getSubtitle());
				imgCoverHot.setTag(url);

				final String contentId = entity.getSpecialId();

				if (mOnItemClickListener != null) {
					cardViewHot.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mOnItemClickListener.onItemClick(v, position, contentId);
						}
					});
				}
			}
		} else if (holder instanceof OtherVH) {
			SimpleDraweeView imgCoverOther = ((OtherVH) holder).imgCoverOther;
			TextView tvTitleOther = ((OtherVH) holder).tvTitleOther;
			TextView tvSubTitleOtherLeft = ((OtherVH) holder).tvSubTitleOtherLeft;
			TextView tvSubTitleOtherRight = ((OtherVH) holder).tvSubTitleOtherRight;
			CardView cardViewOther = ((OtherVH) holder).cardViewOther;

			if (position == 7 | position == 8 && acReAnimation != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity =
						acReAnimation.getData().getPage().getList().get(position - 7);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 10 | position == 11 && acReFun != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity =
						acReFun.getData().getPage().getList().get(position - 10);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 13 | position == 14 && acReMusic != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity
						= acReMusic.getData().getPage().getList().get(position - 13);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 16 | position == 17 && acReGame != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity
						= acReGame.getData().getPage().getList().get(position - 16);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 19 | position == 20 && acReScience != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity
						= acReScience.getData().getPage().getList().get(position - 19);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 22 | position == 23 && acReSport != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity
						= acReSport.getData().getPage().getList().get(position - 22);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else if (position == 25 | position == 26 && acReTv != null) {
				AcReOther.DataEntity.PageEntity.ListEntity entity
						= acReTv.getData().getPage().getList().get(position - 25);

				setCardViewInfo(entity, imgCoverOther, tvTitleOther, tvSubTitleOtherLeft, tvSubTitleOtherRight);
				setCardViewOtherOnClickListener(cardViewOther, position, String.valueOf(entity.getContentId()));
			} else {
				imgCoverOther.setImageBitmap(null);
				tvTitleOther.setText("");
				tvSubTitleOtherLeft.setText("");
				tvSubTitleOtherRight.setText("");
			}
		}
		//			holder.setIsRecyclable(false);
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

	//统一处理
	public void setCardViewInfo(AcReOther.DataEntity.PageEntity.ListEntity entity,
								ImageView imgCoverOther,
								TextView tvTitleOther,
								TextView tvSubTitleOtherLeft,
								TextView tvSubTitleOtherRight) {
		String url = entity.getCover();
		Uri uri = Uri.parse(url);
		imgCoverOther.setImageURI(uri);
		tvTitleOther.setText(entity.getTitle());
		tvSubTitleOtherLeft.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
		tvSubTitleOtherRight.setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
	}

	//统一处理
	public void setCardViewOtherOnClickListener(CardView cardViewOther, final int position, final String contentId) {
		if (mOnItemClickListener != null) {
			cardViewOther.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(v, position, contentId);
				}
			});
		}
	}

	//处理cardview中间的margin
	public static class RecommendDecoration extends RecyclerView.ItemDecoration {

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
