package org.succlz123.blueboard.model.api.acfun;

import com.squareup.okhttp.ResponseBody;

import org.succlz123.blueboard.model.bean.newacfun.NewAcContent;
import org.succlz123.blueboard.model.bean.newacfun.NewAcVideo;
import org.succlz123.blueboard.model.config.RetrofitManager;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by succlz123 on 15/9/21.
 */
public class NewAcApi {

    public interface GetNewAcContent {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_NUM,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_NUM
        })
        @GET("videos/{contentId}")
        Call<NewAcContent> onResult(@Path(value = "contentId") String contentId);
    }

    public static GetNewAcContent getNewAcContent() {
        return RetrofitManager.getAiXiFanApi().create(GetNewAcContent.class);
    }

    public interface GetNewAcVideo {

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

    public static GetNewAcVideo getNewAcVideo() {
        return RetrofitManager.getAiXiFanApi().create(GetNewAcVideo.class);
    }

    public interface GetNewAcDanmaku {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_NUM,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_NUM,
                NewAcString.MARKET + ": " + NewAcString.MARKET_NAME,
                NewAcString.PRODUCTID + ": " + NewAcString.PRODUCTID_2000,
                NewAcString.RESOLUTION + ": " + NewAcString.RESOLUTION_WIDTH_HEIGHT,
                NewAcString.UUID + ": " + NewAcString.UUID_NUM,
                NewAcString.UID + ": " + NewAcString.UID_NUM
        })
        @GET("{danmakuId}")
        Observable<Response<ResponseBody>> onResult(@Path(value = "danmakuId") String danmakuId);
    }

    public static GetNewAcDanmaku getNewAcDanmaku() {
        return RetrofitManager.getAiXiFanDanMu().create(GetNewAcDanmaku.class);
    }
}
