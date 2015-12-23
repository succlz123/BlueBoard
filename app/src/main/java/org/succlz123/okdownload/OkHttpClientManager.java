package org.succlz123.okdownload;

import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by succlz123 on 15/11/18.
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClient sInstance;

    public static OkHttpClient getsInstance() {
        if (sInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (sInstance == null) {
                    sInstance = new com.squareup.okhttp.OkHttpClient();
                    // cookie enabled
                    sInstance.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
                    // timeout reading data from the host
                    sInstance.setReadTimeout(15, TimeUnit.SECONDS);
                    // timeout connection host
                    sInstance.setConnectTimeout(20, TimeUnit.SECONDS);
                }
            }
        }
        return sInstance;
    }
}
