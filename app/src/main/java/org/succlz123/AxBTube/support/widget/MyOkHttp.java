package org.succlz123.AxBTube.support.widget;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fashi on 2015/7/19.
 */
public class MyOkHttp {
	private static MyOkHttp instance;

	public synchronized static MyOkHttp getInstance() {
		if (instance == null) {
			instance = new MyOkHttp();
		}
		return instance;
	}

	private MyOkHttp() {

	}

	public String getJson(String url) {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				response.body().charStream();
				String json = response.body().string();
				return json;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*图片下载*/
	public InputStream doImageGet(String url) {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().byteStream();
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
}
