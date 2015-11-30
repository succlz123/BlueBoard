package org.succlz123.blueboard.model.api.acfun;

import org.succlz123.blueboard.config.RetrofitManager;
import org.succlz123.blueboard.model.bean.newacfun.NewAcContent;
import org.succlz123.blueboard.model.bean.newacfun.NewAcDanmuku;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by succlz123 on 15/9/21.
 */
public class NewAcApi {

    public interface getNewAcContent {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_NUM,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_NUM
        })
        @GET("videos/{contentId}")
        Call<NewAcContent> onResult(@Path(value = "contentId") String contentId);
    }

    public static NewAcApi.getNewAcContent getNewAcContent() {
        return RetrofitManager.getAiXiFanApi().create(NewAcApi.getNewAcContent.class);
    }

    public interface getNewAcVideo {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_NUM,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_NUM,
                NewAcString.MARKET + ": " + NewAcString.MARKET_NAME,
                NewAcString.PRODUCTID + ": " + NewAcString.PRODUCTID_2000,
                NewAcString.RESOLUTION + ": " + NewAcString.RESOLUTION_WIDTH_HEIGHT,
                NewAcString.UUID + ": " + NewAcString.UUID_NUM,
                NewAcString.UID + ": " + NewAcString.UID_NUM
        })
        @GET("plays/{contentId}/realSource")
        Observable<NewAcVideo> onResult(@Path(value = "contentId") String contentId);
    }

    public static NewAcApi.getNewAcVideo getNewAcVideo() {
        return RetrofitManager.getAiXiFanApi().create(NewAcApi.getNewAcVideo.class);
    }

    public interface getNewAcDanmaku {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_NUM,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_NUM,
                NewAcString.MARKET + ": " + NewAcString.MARKET_NAME,
                NewAcString.PRODUCTID + ": " + NewAcString.PRODUCTID_2000,
                NewAcString.RESOLUTION + ": " + NewAcString.RESOLUTION_WIDTH_HEIGHT,
                NewAcString.UUID + ": " + NewAcString.UUID_NUM

        })
        @GET("{danmakuId}")
        Call<NewAcDanmuku> onResult(@Path(value = "danmakuId") String danmakuId);
    }

    public static NewAcApi.getNewAcDanmaku getNewAcDanmaku() {
        return RetrofitManager.getAiXiFanDanMu().create(NewAcApi.getNewAcDanmaku.class);
    }
}
