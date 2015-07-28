package org.succlz123.AxBTube.support.adapter.acfun;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.Nullable;
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

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReAnimation;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.support.callback.GetReResult;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/26.
 */
public class AcReRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
		implements GetReResult {
	private static final int TYPE_VIEW_PAGER = 0;
	private static final int TYPE_NAVIGATION_TITLE = 1;
	private static final int TYPE_CARD_VIEW = 2;

	private AcReBanner acReBanner;
	private AcReHot acReHot;
	private AcReAnimation acReAnimation;

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
	public void onAcReAnimationResult(AcReAnimation result) {
		this.acReAnimation = result;
		notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	//首页横幅
	public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
		@Nullable
		@Bind(R.id.ac_viewpager_banner)
		public ViewPager vpBanner;
		@Nullable
		@Bind(R.id.ac_viewpager_dots)
		public LinearLayout llDots;

		public ViewPagerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	//分区标题
	public class NavigationTitleViewHolder extends RecyclerView.ViewHolder {
		@Nullable
		@Bind(R.id.ac_fragment_re_partition_title_tv)
		public TextView tvPartitionTitle;

		@Nullable
		@Bind(R.id.ac_fragment_re_partition_title_btn)
		public Button btnPartitionMore;

		public NavigationTitleViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	//视图卡片
	public class CardViewViewHolder extends RecyclerView.ViewHolder {
		@Nullable
		@Bind(R.id.ac_card_view_tv_1)
		public TextView tvTitle;
		@Nullable
		@Bind(R.id.ac_card_view_tv_2)
		public TextView tvSubTitle;

		@Bind(R.id.ac_card_view_img)
		public SimpleDraweeView imgCover;

		@Bind(R.id.ac_fragment_re_card_view)
		public CardView cardView;

		public CardViewViewHolder(View itemView) {
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
		}
		return TYPE_CARD_VIEW;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View viewPage
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_recommend_viewpager, parent, false);
		View title
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_recommend_par_title, parent, false);
		View cardView
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_recommend_cardview, parent, false);
		if (viewType == TYPE_VIEW_PAGER) {
			return new ViewPagerViewHolder(viewPage);
		} else if (viewType == TYPE_NAVIGATION_TITLE) {
			return new NavigationTitleViewHolder(title);
		} else if (viewType == TYPE_CARD_VIEW) {
			return new CardViewViewHolder(cardView);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ViewPagerViewHolder) {
			if (acReBanner != null) {
				//放置指示圆点
				LinearLayout dotsLinearLayout = ((ViewPagerViewHolder) holder).llDots;
				ViewPager viewPager = ((ViewPagerViewHolder) holder).vpBanner;
				AcReBannerAdapter adapter = new AcReBannerAdapter(acReBanner, viewPager, dotsLinearLayout);
				viewPager.setAdapter(adapter);
			}
		} else if (holder instanceof NavigationTitleViewHolder) {
			TextView tvPartitionTitle = ((NavigationTitleViewHolder) holder).tvPartitionTitle;
			Button btnPartitionMore = ((NavigationTitleViewHolder) holder).btnPartitionMore;
			tvPartitionTitle.setText(AcString.getTitle(position));
			if (mOnItemClickListener != null) {
				btnPartitionMore.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onItemClick(v, position);
					}
				});
			}
		} else if (holder instanceof CardViewViewHolder) {
			ImageView imgCover = ((CardViewViewHolder) holder).imgCover;
			TextView tvTitle = ((CardViewViewHolder) holder).tvTitle;
			TextView tvSubTitle = ((CardViewViewHolder) holder).tvSubTitle;
			CardView cardView = ((CardViewViewHolder) holder).cardView;
			if (mOnItemClickListener != null) {
				cardView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onItemClick(v, position);
					}
				});
			}
			if (position == 2 | position == 3 | position == 4 | position == 5 && acReHot != null) {
				AcReHot.DataEntity.PageEntity.ListEntity entity
						= acReHot.getData().getPage().getList().get(position - 2);
				String url = entity.getCover();
				Uri uri = Uri.parse(url);
				imgCover.setImageURI(uri);
				tvTitle.setText(entity.getTitle());
				tvSubTitle.setText(entity.getSubtitle());
				imgCover.setTag(url);
			} else if (position == 7 | position == 8 && acReAnimation != null) {
				AcReAnimation.DataEntity.PageEntity.ListEntity entity =
						acReAnimation.getData().getPage().getList().get(position - 5);
				String url = entity.getCover();
				Uri uri = Uri.parse(url);
				imgCover.setImageURI(uri);
				tvTitle.setText(entity.getTitle());
			} else {
				imgCover.setImageDrawable(null);
				tvTitle.setText(null);
				tvSubTitle.setText(null);
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
