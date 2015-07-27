package org.succlz123.AxBTube.ui.fragment.main.bilili;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.helper.bilibili.BiliApi;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.LogUtils;


/**
 * Created by fashi on 2015/5/2.
 */
public class BiliRecommendFragment extends BaseFragment {
	private View mView;
	private ViewPager mViewPager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.ac_fragment_recommend_viewpager, container, false);
		initViews();

		String Url = BiliApi.BASE + BiliApi.DEVICE
				+ BiliApi.BANNER + BiliApi.ULV
				+ BiliApi.APPKEY + BiliApi.DEVICE
				+ BiliApi.DEVICE + BiliApi.DEVICE;

		String xx = GlobalUtils.getAndroidId(getActivity());
 		LogUtils.d(xx);


		return mView;
	}

	private void initViews() {
		mViewPager = (ViewPager) mView.findViewById(R.id.fragment_main_viewpager);
	}

	private class RecommendAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return false;
		}
	}


}
