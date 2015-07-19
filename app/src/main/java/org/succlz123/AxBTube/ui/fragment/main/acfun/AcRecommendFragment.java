package org.succlz123.AxBTube.ui.fragment.main.acfun;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_ac_recommend, container, false);
		initViews();

		final String[] json = {null};
		final AcBanner acBanner = new AcBanner();
 		new GetBanner().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//		Ion.with(getActivity())
//				.load(AcApi.getAcRecommendBannerUrl())
//				.as(new TypeToken<List<AcBanner>>() {
//				})
//				.setCallback(new FutureCallback<List<AcBanner>>() {
//					@Override
//					public void onCompleted(Exception e, List<AcBanner> result) {
//						if (e != null) {
//							GlobalUtils.showToast(getActivity(), "banner get error");
//							return;
//						}
//						if (result != null) {
//
//
//
//							for (int i = 0; i < result.size(); i++) {
//								adapter.add(result.get(i));
//							}
//						}
////						json[0] = result.toString();
////						acBanner[0] = AcBanner.parseJson(result);
//					}
//				});


		return mView;
	}

	private void initViews() {
		mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_banner);
	}

	private class AcRecommendBannerAdapter extends PagerAdapter {
		private AcBanner acBanners;
		private List<ImageView> imageViews =new ArrayList<>();

		public AcRecommendBannerAdapter(AcBanner acBanner) {
			super();
			this.acBanners = acBanner;
		}

		@Override
		public int getCount() {
			int num = acBanners.getData().getList().size();
			if (num != 0) {
				for (int i = 0; i < num; i++) {
					ImageView imageView = new ImageView(getActivity());
 					imageView.setBackground(getResources().getDrawable(R.drawable.bilibbili33,null));
					imageViews.add(imageView);
				}
				return acBanners.getData().getList().size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (imageViews.size() != 0) {
				container.addView(imageViews.get(position));
				return imageViews.get(position);
			}
			return null;
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
