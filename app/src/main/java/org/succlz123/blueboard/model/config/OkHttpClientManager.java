package org.succlz123.blueboard.model.config;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;

import org.succlz123.blueboard.model.api.acfun.NewAcString;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by succlz123 on 15/9/15.
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static com.squareup.okhttp.OkHttpClient sInstance;

    public static com.squareup.okhttp.OkHttpClient getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (sInstance == null) {
                    sInstance = new com.squareup.okhttp.OkHttpClient();
                    //cookie enabled
                    sInstance.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
                    //从主机读取数据超时
                    sInstance.setReadTimeout(15, TimeUnit.SECONDS);
                    //连接主机超时
                    sInstance.setConnectTimeout(20, TimeUnit.SECONDS);
                }
            }
        }
        return sInstance;
    }


    public void xx(String url) {

        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .addHeader(NewAcString.APP_VERSION, NewAcString.APP_VERSION_NUM)
                .addHeader(NewAcString.DEVICETYPE, NewAcString.DEVICETYPE_NUM)
                .addHeader(NewAcString.MARKET, NewAcString.MARKET_NAME)
                .addHeader(NewAcString.PRODUCTID, NewAcString.PRODUCTID_2000)
                .addHeader(NewAcString.RESOLUTION, NewAcString.RESOLUTION_WIDTH_HEIGHT)
                .addHeader(NewAcString.UUID, NewAcString.UUID_NUM);

        Request request = builder.build();

        Call call = OkHttpClientManager.getInstance().newCall(request);
        call.enqueue(new com.squareup.okhttp.Callback() {
                         @Override
                         public void onFailure(Request request, IOException e) {

                         }

                         @Override
                         public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                             response.toString();
                         }
                     }
        );
    }

}
