package org.succlz123.AxBTube.ui.fragment.main.acfun;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcBanner;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
	private View mView;
	private ViewPager mViewPager;
	private TextView mBannerTitle;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.ac_fragment_recommend, container, false);
		initViews();

		AcBanner acBanner = new AcBanner();
		new GetBanner().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


		return mView;
	}

	private void initViews() {
		mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_banner);
		mBannerTitle = (TextView) mView.findViewById(R.id.ac_banner_title);
	}

	private class AcRecommendBannerAdapter extends PagerAdapter {
		private AcBanner acBanners;
		private List<SimpleDraweeView> simpleDraweeViews = new ArrayList<>();
		private List<View> views = new ArrayList<>();
		private List<AcBanner.DataEntity.ListEntity> bannerInfo = new ArrayList<>();
		private int num;

		public AcRecommendBannerAdapter(AcBanner acBanner) {
			super();
			this.acBanners = acBanner;
			num = acBanners.getData().getList().size();
			if (num != 0) {
				for (int i = 0; i < num; i++) {
					bannerInfo = acBanners.getData().getList();

					String url = bannerInfo.get(i).getCover();
					String title = bannerInfo.get(i).getTitle();

					Uri uri = Uri.parse(url);

					LayoutInflater inflater = (LayoutInflater)
							getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					ViewGroup view = (ViewGroup) inflater.inflate(R.layout.ac_recommend_banner, null);

					for (int j = 0; j < view.getChildCount(); j++) {
						View child = view.getChildAt(j);
						if (child instanceof LinearLayout) {
							LinearLayout linearLayout = (LinearLayout) child;
							TextView bannerTitle = (TextView) linearLayout.findViewById(R.id.ac_banner_title);
							bannerTitle.setText(title);
						}

						if (child instanceof SimpleDraweeView) {
							SimpleDraweeView simpleDraweeView = (SimpleDraweeView) child;

							GenericDraweeHierarchyBuilder builder =
									new GenericDraweeHierarchyBuilder(getResources());
							GenericDraweeHierarchy hierarchy = builder
									.setFadeDuration(300)
									.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
									.build();
							simpleDraweeView.setHierarchy(hierarchy);
							simpleDraweeView.setImageURI(uri);
						}
					}

					views.add(view);

//					SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getActivity());

//					GenericDraweeHierarchyBuilder builder =
//							new GenericDraweeHierarchyBuilder(getResources());
//					GenericDraweeHierarchy hierarchy = builder
//							.setFadeDuration(300)
//							.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
//							.build();
//					simpleDraweeView.setHierarchy(hierarchy);
//					simpleDraweeView.setImageURI(uri);
//					DraweeController draweeController
//							= Fresco.newDraweeControllerBuilder()
//							.setUri(uri)
//							.build();
//
//					simpleDraweeView.setController(draweeController);
//					simpleDraweeView.setOnTouchListener(new View.OnTouchListener() {
//						@Override
//						public boolean onTouch(View v, MotionEvent event) {
//
//							return false;
//						}
//					});

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
			if (views.size() != 0 && num != 0) {
				View view = views.get(position % num);
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
			container.removeView(views.get(position % num));
		}
	}

	private class MyListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}

	private class GetBanner extends AsyncTask<Void, Void, AcBanner> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected AcBanner doInBackground(Void... params) {
			String xx = MyOkHttp.getInstance().getJson(AcApi.getAcRecommendBannerUrl());
			return AcBanner.parseJson(xx);
		}

		@Override
		protected void onPostExecute(AcBanner aVoid) {
			super.onPostExecute(aVoid);
			if (aVoid != null) {
				AcRecommendBannerAdapter adapter = new AcRecommendBannerAdapter(aVoid);
				mViewPager.setAdapter(adapter);
			}

		}
	}

}
