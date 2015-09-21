package org.succlz123.bluetube.support.helper.acfun;

import org.succlz123.bluetube.bean.newacfun.NewAcContent;
import org.succlz123.bluetube.bean.newacfun.NewAcVideo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by succlz123 on 15/9/21.
 */
public class NewAcApi {

    public interface getNewAcContent {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_400,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_1
        })
        @GET("videos/{contentId}")
        Call<NewAcContent> onResult(@Path(value = "contentId") String contentId);
    }

    public interface getNewAcVideo {

        @Headers({
                NewAcString.APP_VERSION + ": " + NewAcString.APP_VERSION_400,
                NewAcString.DEVICETYPE + ": " + NewAcString.DEVICETYPE_1,
                NewAcString.MARKET + ": " + NewAcString.MARKET_PORTAL,
                NewAcString.PRODUCTID + ": " + NewAcString.PRODUCTID_2000,
                NewAcString.RESOLUTION + ": " + NewAcString.RESOLUTION_WIDTH_HEIGHT,
                NewAcString.UUID + ": " + NewAcString.UUID_X

        })
        @GET("plays/{contentId}/realSource")
        Call<NewAcVideo> onResult(@Path(value = "contentId") String contentId);
    }
}
