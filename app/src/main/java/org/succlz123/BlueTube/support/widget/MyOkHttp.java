package org.succlz123.BlueTube.support.widget;

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

    public InputStream downLoad(String downLoadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downLoadUrl)
//                .addHeader("X-CSRFToken", csrftoken)
                .addHeader("Content-Type", "application/json").build();

        Response response = null;
        InputStream is = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                is = response.body().byteStream();
                return is;
            } else {
                throw new IOException("Unexpected code " + response);
            }
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String result, line = reader.readLine();
//            result = line;
//            while ((line = reader.readLine()) != null) {
//                result += line;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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
