package org.succlz123.AxBTube.support.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import io.vov.vitamio.Vitamio;

/**
 * Created by succlz123 on 15/8/5.
 */
public class InitVitamioAsyncTask extends AsyncTask<Object, Object, Boolean> {
    private Context mContext;

    public InitVitamioAsyncTask(Context ctx) {
        super();
        mContext = ctx;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Boolean doInBackground(Object... params) {
        return Vitamio.initialize(mContext, mContext.getResources().getIdentifier("libarm", "raw", mContext.getPackageName()));
    }

    @Override
    public void onPostExecute(Boolean inited) {

    }


}
