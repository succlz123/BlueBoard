package org.succlz123.bluetube.support.widget;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.succlz123.bluetube.MyApplication;

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

    public void getHeader(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setFollowRedirects(false);
        Request request = new Request.Builder().url(url).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String url = response.header("Location");
//                String url = response.request().urlString();

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request1 = new Request.Builder().url(url).build();
                Call call = okHttpClient.newCall(request1);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String url = response.request().urlString();

                        final String fileName = "cc.mp4";
                        final String filePathName = MyApplication.getsInstance().getApplicationContext().getExternalFilesDir("video").getAbsolutePath();
//                        + File.separator + fileName;
                        DownloadManager downloadManager = (DownloadManager) MyApplication.getsInstance().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));


                         request.addRequestHeader("Connection", "Keep-Alive");


                    }
                });


            }
        });
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
