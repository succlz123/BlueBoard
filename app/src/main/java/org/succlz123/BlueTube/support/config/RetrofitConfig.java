package org.succlz123.bluetube.support.config;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitConfig {

    private static RestAdapter getRestAdapter(String baseUrl) {
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setClient(new OkClient(MyApplication.okHttpClient()))
                .setEndpoint(baseUrl)
                .build();

        return restAdapter;
    }

    public static AcApi.getAcPartition getAcPartition() {
        AcApi.getAcPartition acPartition
                = getRestAdapter(AcString.URL_ACFUN_API_SERVER).create(AcApi.getAcPartition.class);

        return acPartition;
    }

    public static AcApi.getAcRecommend getAcRecommend() {
        AcApi.getAcRecommend acRecommend
                = getRestAdapter(AcString.URL_ACFUN_API_SERVER).create(AcApi.getAcRecommend.class);

        return acRecommend;
    }

    public static AcApi.getAcContentInfo getAcContentInfo() {
        AcApi.getAcContentInfo acContent
                = getRestAdapter(AcString.URL_ACFUN_API_SERVER).create(AcApi.getAcContentInfo.class);

        return acContent;
    }

    public static AcApi.getAcContentReply getAcContentReply() {
        AcApi.getAcContentReply acContentReply
                = getRestAdapter(AcString.URL_ACFUN_TV).create(AcApi.getAcContentReply.class);

        return acContentReply;
    }

    public static AcApi.getAcRanking getAcRanking() {
        AcApi.getAcRanking acRanking
                = getRestAdapter(AcString.URL_ACFUN_API_SERVER).create(AcApi.getAcRanking.class);

        return acRanking;
    }

    public static AcApi.getAcBangumi getAcBangumi() {
        AcApi.getAcBangumi acBangumi
                = getRestAdapter(AcString.URL_ACFUN_ICAO).create(AcApi.getAcBangumi.class);

        return acBangumi;
    }

    public static AcApi.getAcContentLeTvVideo getAcContentLeTvVideo() {
        AcApi.getAcContentLeTvVideo acContentLeTvVideo
                = getRestAdapter(AcString.LETV_URL_BASE).create(AcApi.getAcContentLeTvVideo.class);

        return acContentLeTvVideo;
    }

    public static AcApi.getAcContentHtml5Video getAcContentHtml5Video() {
        AcApi.getAcContentHtml5Video acContentHtml5Video
                = getRestAdapter(AcString.URL_ACFUN_TV).create(AcApi.getAcContentHtml5Video.class);

        return acContentHtml5Video;
    }

    public static AcApi.getAcContentDanMu getAcContentDanMu() {
        AcApi.getAcContentDanMu acContentDanMu
                = getRestAdapter(AcString.URL_AIXIFAN_DANMU).create(AcApi.getAcContentDanMu.class);

        return acContentDanMu;
    }
}
