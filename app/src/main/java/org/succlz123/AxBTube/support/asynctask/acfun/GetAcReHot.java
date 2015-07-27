package org.succlz123.AxBTube.support.asynctask.acfun;

import android.os.AsyncTask;

import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.widget.MyOkHttp;

/**
 * Created by fashi on 2015/7/26.
 */
public class GetAcReHot extends AsyncTask<Void, Void, AcReHot> {

	public interface GetReHotResult {
		void onAcReHotResult(AcReHot result);
	}

	private GetReHotResult mGetReHotResult;

	public GetAcReHot(GetReHotResult getReHotResult) {
		super();
		this.mGetReHotResult = getReHotResult;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected AcReHot doInBackground(Void... params) {
		String json = MyOkHttp.getInstance().getJson(AcApi.getAcReHot());
		return AcReHot.parseJson(json);
	}

	@Override
	protected void onPostExecute(final AcReHot aVoid) {
		super.onPostExecute(aVoid);
		if (aVoid != null) {
			mGetReHotResult.onAcReHotResult(aVoid);
		}
	}
}