package org.succlz123.AxBTube.support.asynctask.acfun;

import android.os.AsyncTask;

import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;

/**
 * Created by chinausky on 2015/7/24.
 */
public class GetAcReBanner extends AsyncTask<Void, Void, AcReBanner> {

	public interface GetReBannerResult {
		void onReBannerResult(AcReBanner result);
	}

	private GetReBannerResult mGetReBannerResult;

	public GetAcReBanner(GetReBannerResult getReBannerResult) {
		super();
		this.mGetReBannerResult = getReBannerResult;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected AcReBanner doInBackground(Void... params) {
		String json = MyOkHttp.getInstance().getJson(AcApi.getAcReBanner());
		return AcReBanner.parseJson(json);
	}

	@Override
	protected void onPostExecute(AcReBanner aVoid) {
		super.onPostExecute(aVoid);
		if (aVoid != null) {
			mGetReBannerResult.onReBannerResult(aVoid);
		}
	}
}
