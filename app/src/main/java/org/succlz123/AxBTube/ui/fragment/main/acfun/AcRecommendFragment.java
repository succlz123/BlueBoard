package org.succlz123.AxBTube.ui.fragment.main.acfun;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;
import org.succlz123.AxBTube.ui.fragment.asynctask.GetAcRecommendBanner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
	@Bind(R.id.ac_re_recycler_view)
	RecyclerView mRecyclerView;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.ac_fragment_recommend, container, false);
		View view = inflater.inflate(R.layout.yy, container, false);
		ButterKnife.bind(this, view);
//		new GetHot().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//		mRecyclerView.setHasFixedSize(true);
//		GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
//		StaggeredGridLayoutManager manager =new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
 		LinearLayoutManager manager=new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(new AcRecommendRecyclerViewAdapter());
		return view;
	}


	public class AcRecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
		private static final int TYPE_VIEW_PAGER = 0;
		private static final int TYPE_NAVIGATION_TITLE = 1;
		private static final int TYPE_CARD_VIEW = 2;

		//首页横幅
		public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
			@Nullable
			@Bind(R.id.ac_viewpager_banner)
			ViewPager viewPager;
			@Nullable
			@Bind(R.id.ac_viewpager_dots)
			LinearLayout dotsLinearLayout;

			public ViewPagerViewHolder(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}
		}

		//分区标题
		public class NavigationTitleViewHolder extends RecyclerView.ViewHolder {
			@Nullable
			@Bind(R.id.ac_recommend_navigation_title)
			TextView navigationTitle;

			public NavigationTitleViewHolder(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}
		}

		//推荐的视图卡片
		public class CardViewViewHolder extends RecyclerView.ViewHolder {
//			@Nullable
//			@Bind(R.id.ac_card_view_tv_1_1)
//			public TextView textViewLeftOne;
//			@Nullable
//			@Bind(R.id.ac_card_view_tv_1_2)
//			public TextView textViewLeftTwo;
//			@Nullable
//			@Bind(R.id.ac_card_view_tv_2_1)
//			public TextView textViewRightOne;
//			@Nullable
//			@Bind(R.id.ac_card_view_tv_2_2)
//			public TextView textViewRightTwo;

			@Nullable
			@Bind(R.id.ac_card_view_img_1)
			public ImageView imageViewLeft;
//			@Nullable
//			@Bind(R.id.ac_card_view_img_2)
//			public ImageView imageViewRight;

			public CardViewViewHolder(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}
		}

		//根据position判断是否显示分区标题
		public boolean getTitleType(int position) {
			if (position == 1 | position == 4 | position == 6 | position == 8
					| position == 10 | position == 12 | position == 14 | position == 16) {
				return true;
			}
			return false;
		}

		//根据position来显示标题
		public String getTitleText(int position) {
			if (position == 1) {
				return "热门焦点";
			} else if (position == 4) {
				return "动画";
			} else if (position == 6) {
				return "娱乐";
			} else if (position == 8) {
				return "音乐";
			} else if (position == 10) {
				return "游戏";
			} else if (position == 12) {
				return "科技";
			} else if (position == 14) {
				return "体育";
			} else if (position == 16) {
				return "影视";
			}
			return null;
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
			View viewPage = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_fragment_recommend, parent, false);
			View navigationTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.yy_1, parent, false);
			View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.yy_2, parent, false);
			if (viewType == TYPE_VIEW_PAGER) {
				return new ViewPagerViewHolder(viewPage);
			} else if (viewType == TYPE_NAVIGATION_TITLE) {
 				return new NavigationTitleViewHolder(navigationTitle);
			} else if (viewType == TYPE_CARD_VIEW) {
				return new CardViewViewHolder(cardView);
			}
			return null;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			if (holder instanceof ViewPagerViewHolder) {
				//判断有没有attached到activity
				Fragment fragment = AcRecommendFragment.this;
				ViewPager ViewPager = ((ViewPagerViewHolder) holder).viewPager;
				//放置指示圆点
				LinearLayout dotsLinearLayout = ((ViewPagerViewHolder) holder).dotsLinearLayout;
				new GetAcRecommendBanner(fragment, ViewPager, dotsLinearLayout).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else if (holder instanceof NavigationTitleViewHolder) {
				TextView navigationTitle = ((NavigationTitleViewHolder) holder).navigationTitle;
				navigationTitle.setText(getTitleText(position));
 			} else if (holder instanceof CardViewViewHolder) {
				CardViewViewHolder viewHolder = (CardViewViewHolder) holder;
				viewHolder.imageViewLeft.setBackgroundResource(R.drawable.aa);
			}
		}

		@Override
		public int getItemCount() {
			return 18;
		}
	}


//
//	private class GetHot extends AsyncTask<Void, Void, AcRecommendHot> {
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected AcRecommendHot doInBackground(Void... params) {
//			String json = MyOkHttp.getInstance().getJson(AcApi.getAcRecommendHot());
//			return AcRecommendHot.parseJson(json);
//		}
//
//		@Override
//		protected void onPostExecute(AcRecommendHot aVoid) {
//			super.onPostExecute(aVoid);
//			if (aVoid != null) {
//				Uri uri = Uri.parse(aVoid.getData().getPage().getList().get(1).getCover());
//				cardView.setImageURI(uri);
//
//			}
//		}
//	}

}
