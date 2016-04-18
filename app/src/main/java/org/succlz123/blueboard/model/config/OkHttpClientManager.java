package org.succlz123.blueboard.model.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by succlz123 on 15/9/15.
 */
public class OkHttpClientManager {
    private static final String TAG = OkHttpClientManager.class.getName();
    protected OkHttpClient okHttpClient;

    private OkHttpClientManager() {
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
            okHttpClient = okHttpClientBuilder.build();
        }
    }

    public static OkHttpClientManager getInstance() {
        return HelpHolder.INSTANCE;
    }

    private static class HelpHolder {
        private static final OkHttpClientManager INSTANCE = new OkHttpClientManager();
    }
}
