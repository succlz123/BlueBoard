package org.succlz123.bluetube.support.config;

import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.helper.acfun.NewAcApi;
import org.succlz123.bluetube.support.helper.acfun.NewAcString;
import org.succlz123.bluetube.support.utils.OkHttpClientManager;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitConfig {
    private static Retrofit.Builder sInstance;

    public static Retrofit.Builder getBuilder() {
        if (sInstance == null) {
            synchronized (RetrofitConfig.class) {
                if (sInstance == null) {
                    sInstance = new Retrofit.Builder();
                }
            }
        }
        return sInstance;
    }

    private static Retrofit getRetrofit(Retrofit.Builder builder, String url) {
        Retrofit retrofit = builder.client(OkHttpClientManager.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        return retrofit;
    }

    public static AcApi.getAcPartition getAcPartition() {
        AcApi.getAcPartition acPartition
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_API_SERVER))
                .create(AcApi.getAcPartition.class);

        return acPartition;
    }

    public static AcApi.getAcRecommend getAcRecommend() {
        AcApi.getAcRecommend acRecommend
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_API_SERVER))
                .create(AcApi.getAcRecommend.class);

        return acRecommend;
    }

    public static AcApi.getAcContentInfo getAcContentInfo() {
        AcApi.getAcContentInfo acContent
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_API_SERVER))
                .create(AcApi.getAcContentInfo.class);

        return acContent;
    }

    public static AcApi.getAcContentReply getAcContentReply() {
        AcApi.getAcContentReply acContentReply
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_TV))
                .create(AcApi.getAcContentReply.class);

        return acContentReply;
    }

    public static AcApi.getAcRanking getAcRanking() {
        AcApi.getAcRanking acRanking
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_API_SERVER))
                .create(AcApi.getAcRanking.class);

        return acRanking;
    }

    public static AcApi.getAcBangumi getAcBangumi() {
        AcApi.getAcBangumi acBangumi
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_ICAO))
                .create(AcApi.getAcBangumi.class);

        return acBangumi;
    }

    public static AcApi.getAcContentLeTvVideo getAcContentLeTvVideo() {
        AcApi.getAcContentLeTvVideo acContentLeTvVideo
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.LETV_URL_BASE))
                .create(AcApi.getAcContentLeTvVideo.class);

        return acContentLeTvVideo;
    }

    public static AcApi.getAcContentHtml5Video getAcContentHtml5Video() {
        AcApi.getAcContentHtml5Video acContentHtml5Video
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_ACFUN_TV))
                .create(AcApi.getAcContentHtml5Video.class);

        return acContentHtml5Video;
    }

    public static AcApi.getAcContentDanMu getAcContentDanMu() {
        AcApi.getAcContentDanMu acContentDanMu
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (AcString.URL_AIXIFAN_DANMU))
                .create(AcApi.getAcContentDanMu.class);

        return acContentDanMu;
    }

    public static NewAcApi.getNewAcContent getNewAcContent() {
        NewAcApi.getNewAcContent getNewAcContent
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (NewAcString.API_AIXIFAN_COM))
                .create(NewAcApi.getNewAcContent.class);

        return getNewAcContent;
    }

    public static NewAcApi.getNewAcVideo getNewAcVideo() {
        NewAcApi.getNewAcVideo getNewAcVideo
                = RetrofitConfig
                .getRetrofit(RetrofitConfig.getBuilder(), (NewAcString.API_AIXIFAN_COM))
                .create(NewAcApi.getNewAcVideo.class);

        return getNewAcVideo;
    }
}
