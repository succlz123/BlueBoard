package org.succlz123.AxBTube.support.asynctask;

import android.os.AsyncTask;

import org.succlz123.AxBTube.bean.acfun.AcReAnimation;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;

/**
 * Created by fashi on 2015/7/26.
 */
public class GetAcReAnimation extends AsyncTask<Void, Void, AcReAnimation> {

	public interface GetReAnimationResult {
		void onAcReAnimationResult(AcReAnimation result);
	}

	private GetReAnimationResult mGetReAnimationResult;

	public GetAcReAnimation(GetReAnimationResult getReAnimationResult) {
		super();
		this.mGetReAnimationResult = getReAnimationResult;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected AcReAnimation doInBackground(Void... params) {
		String json = MyOkHttp.getInstance().getJson(AcApi.getAcReEntertainment());
		return AcReAnimation.parseJson(json);
	}

	@Override
	protected void onPostExecute(AcReAnimation aVoid) {
		super.onPostExecute(aVoid);
		if (aVoid != null) {
			mGetReAnimationResult.onAcReAnimationResult(aVoid);
		}
	}
}
