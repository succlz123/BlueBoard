package org.succlz123.AxBTube.ui.fragment.main.acfun;

import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcBanner;
import org.succlz123.AxBTube.bean.acfun.AcRecommendHot;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.widget.MyOkHttp;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
	@Bind(R.id.ac_viewpager_banner)
	ViewPager mVp;
	@Bind(R.id.ac_viewpager_dots)
	LinearLayout mDotsLinearLayout;
	@Bind(R.id.ac_cardview_pic)
	SimpleDraweeView cardView;


	private int mVpTotalNum;
	private List<ImageView> mDotsImageViews = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_recommend, container, false);
		ButterKnife.bind(this, view);
		new GetBanner().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		new GetHot().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		return view;
	}


	private class AcRecommendBannerAdapter extends PagerAdapter {
		private AcBanner acBanners;
		private List<View> viewItems = new ArrayList<>();
		private List<AcBanner.DataEntity.ListEntity> bannerInfo = new ArrayList<>();
		private Resources resources;

		public AcRecommendBannerAdapter(AcBanner acBanner) {
			super();
			this.acBanners = acBanner;
			//防止fragment在没有attached到activity之前调用getResources()出错
			//http://stackoverflow.com/questions/6870325/android-compatibility-package-fragment-not-attached-to-activity
			if (AcRecommendFragment.this.isAdded()) {
				resources = getResources();
			}
			bannerInfo = acBanners.getData().getList();
			mVpTotalNum = bannerInfo.size();

			int dotsPx = GlobalUtils.dip2pix(MyApplication.getInstance().getApplicationContext(), 7);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotsPx, dotsPx);
			layoutParams.setMargins(dotsPx / 2, dotsPx, dotsPx, dotsPx);

			if (mVpTotalNum != 0) {
				for (int i = 0; i < mVpTotalNum; i++) {
					String url = bannerInfo.get(i).getCover();
					Uri uri = Uri.parse(url);

					if (resources != null) {
						ImageView dotsImageView = new ImageView(
								MyApplication.getInstance().getApplicationContext());
						dotsImageView.setLayoutParams(layoutParams);
						dotsImageView.setTag(i);
						dotsImageView.setImageResource(R.drawable.banner_dots_selector);
						if (i == 0) {
							dotsImageView.setSelected(true);
						}
						mDotsImageViews.add(dotsImageView);
						mDotsLinearLayout.addView(dotsImageView);

						SimpleDraweeView simpleDraweeView = new SimpleDraweeView(
								MyApplication.getInstance().getApplicationContext());
						GenericDraweeHierarchy vpGdh = new GenericDraweeHierarchyBuilder(resources)
								.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
								.build();
						simpleDraweeView.setHierarchy(vpGdh);
						simpleDraweeView.setImageURI(uri);
						viewItems.add(simpleDraweeView);
					}
				}
			}
		}

		/**
		 * viewpager页数 Integer.MAX_VALUE无限循环
		 *
		 * @return
		 */
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		/**
		 * 复用对象 true 复用view false 复用的是Object
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		/**
		 * 初始化
		 *
		 * @param container
		 * @param position
		 * @return
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (viewItems.size() != 0 && mVpTotalNum != 0) {
				View view = viewItems.get(position % mVpTotalNum);
				container.addView(view);
				return view;
			}
			return null;
		}

		/**
		 * 销毁
		 *
		 * @param container
		 * @param position
		 * @param object
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewItems.get(position % mVpTotalNum));
		}
	}

	@OnPageChange(value = R.id.ac_viewpager_banner, callback = OnPageChange.Callback.PAGE_SELECTED)
	void onViewpagerSelected(int position) {
		int VpNum = (position % mVpTotalNum);
		for (ImageView iv : mDotsImageViews) {
			if (VpNum == (int) iv.getTag()) {
				iv.setSelected(true);
			} else {
				iv.setSelected(false);
			}
		}
	}

	private class GetBanner extends AsyncTask<Void, Void, AcBanner> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected AcBanner doInBackground(Void... params) {
			String json = MyOkHttp.getInstance().getJson(AcApi.getAcRecommendBanner());
			return AcBanner.parseJson(json);
		}

		@Override
		protected void onPostExecute(AcBanner aVoid) {
			super.onPostExecute(aVoid);
			if (aVoid != null) {
				AcRecommendBannerAdapter adapter = new AcRecommendBannerAdapter(aVoid);
				mVp.setAdapter(adapter);
			}
		}
	}

	private class GetHot extends AsyncTask<Void, Void, AcRecommendHot> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected AcRecommendHot doInBackground(Void... params) {
			String json = MyOkHttp.getInstance().getJson(AcApi.getAcRecommendHot());
			return AcRecommendHot.parseJson(json);
		}

		@Override
		protected void onPostExecute(AcRecommendHot aVoid) {
			super.onPostExecute(aVoid);
			if (aVoid != null) {
				Uri uri=Uri.parse(aVoid.getData().getPage().getList().get(1).getCover());
				cardView.setImageURI(uri);

			}
		}
	}

}
