package org.succlz123.AxBTube.support.asynctask;

import android.os.AsyncTask;

import org.succlz123.AxBTube.bean.acfun.AcBanner;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;

/**
 * Created by chinausky on 2015/7/20.
 */
public class AcRecommentHot extends AsyncTask<Void, Void, AcBanner> {

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
 		}

	}
}
