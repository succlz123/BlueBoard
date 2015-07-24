package org.succlz123.AxBTube.ui.fragment.asynctask;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import org.succlz123.AxBTube.bean.acfun.AcBanner;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;
import org.succlz123.AxBTube.ui.fragment.adapter.AcRecommendBannerAdapter;

/**
 * Created by chinausky on 2015/7/24.
 */
public class GetAcRecommendBanner extends AsyncTask<Void, Void, AcBanner> {
	private ViewPager viewPager;
	private LinearLayout dots;
	private Fragment mFragment;

	public GetAcRecommendBanner(Fragment fragment, ViewPager viewPager, LinearLayout dots) {
		super();
		this.mFragment = fragment;
		this.viewPager = viewPager;
		this.dots = dots;
	}

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
			//防止fragment在没有attached到activity之前调用getResources()出错
			//http://stackoverflow.com/questions/6870325/android-compatibility-package-fragment-not-attached-to-activity
			if (mFragment.isAdded()) {
				AcRecommendBannerAdapter adapter = new AcRecommendBannerAdapter(aVoid, viewPager, dots);
				viewPager.setAdapter(adapter);
			}
		}
	}
}
