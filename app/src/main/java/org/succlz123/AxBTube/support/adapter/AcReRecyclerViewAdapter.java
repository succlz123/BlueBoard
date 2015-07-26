package org.succlz123.AxBTube.support.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReAnimation;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.support.asynctask.GetAcReAnimation;
import org.succlz123.AxBTube.support.asynctask.GetAcReBanner;
import org.succlz123.AxBTube.support.asynctask.GetAcReHot;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/26.
 */
public class AcReRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
		implements GetAcReBanner.GetReBannerResult, GetAcReHot.GetReHotResult, GetAcReAnimation.GetReAnimationResult {
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

	//首页横幅
	public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
		@Nullable
		@Bind(R.id.ac_viewpager_banner)
		public ViewPager viewPager;
		@Nullable
		@Bind(R.id.ac_viewpager_dots)
		public LinearLayout dotsLinearLayout;

		public ViewPagerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	//分区标题
	public class NavigationTitleViewHolder extends RecyclerView.ViewHolder {
		@Nullable
		@Bind(R.id.ac_recommend_navigation_title)
		public TextView navigationTitle;

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
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_re_viewpager, parent, false);
		View title
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_re_title, parent, false);
		View cardView
				= LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_re_cardview, parent, false);
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
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ViewPagerViewHolder) {
			//放置指示圆点
			LinearLayout dotsLinearLayout = ((ViewPagerViewHolder) holder).dotsLinearLayout;
			ViewPager viewPager = ((ViewPagerViewHolder) holder).viewPager;
			if (acReBanner != null) {
				AcReBannerAdapter adapter = new AcReBannerAdapter(acReBanner, viewPager, dotsLinearLayout);
				viewPager.setAdapter(adapter);
			}
		} else if (holder instanceof NavigationTitleViewHolder) {
			TextView navigationTitle = ((NavigationTitleViewHolder) holder).navigationTitle;
			navigationTitle.setText(getTitleText(position));
		} else if (holder instanceof CardViewViewHolder) {
			ImageView imgCover = ((CardViewViewHolder) holder).imgCover;
			TextView tvTitle = ((CardViewViewHolder) holder).tvTitle;
			TextView tvSubTitle = ((CardViewViewHolder) holder).tvSubTitle;

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
						acReAnimation.getData().getPage().getList().get(position - 7);
				String url = entity.getCover();
				Uri uri = Uri.parse(url);
				imgCover.setImageURI(uri);
				tvTitle.setText(entity.getTitle());
//				LinearLayout.LayoutParams layoutParams
//						= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//				int margin = GlobalUtils.dip2pix(tvTitle.getContext(), 7);
//				layoutParams.setMargins(margin, margin, margin, margin);
//				tvTitle.setLayoutParams(layoutParams);
//				tvSubTitle.setVisibility(View.GONE);
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

	//根据position来显示标题
	public String getTitleText(int position) {
		if (position == 1) {
			return "热门焦点";
		} else if (position == 6) {
			return "动画";
		} else if (position == 9) {
			return "娱乐";
		} else if (position == 12) {
			return "音乐";
		} else if (position == 15) {
			return "游戏";
		} else if (position == 18) {
			return "科技";
		} else if (position == 21) {
			return "体育";
		} else if (position == 24) {
			return "影视";
		}
		return null;
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
