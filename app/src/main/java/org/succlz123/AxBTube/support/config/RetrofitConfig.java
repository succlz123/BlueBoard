package org.succlz123.AxBTube.support.config;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitConfig {

    private static RestAdapter restAdapterWithAcFunApiServer = new RestAdapter
            .Builder()
            .setClient(new OkClient(MyApplication.okHttpClient()))
            .setEndpoint(AcString.URL_ACFUN_API_SERVER)
            .build();

    private static RestAdapter restAdapterWithAcFunTV = new RestAdapter
            .Builder()
            .setClient(new OkClient(MyApplication.okHttpClient()))
            .setEndpoint(AcString.URL_ACFUN_TV)
            .build();

    private static RestAdapter restAdapterWithAcFunIcao
            = new RestAdapter
            .Builder()
            .setClient(new OkClient(MyApplication.okHttpClient()))
            .setEndpoint(AcString.URL_ACFUN_ICAO)
            .build();

    private static RestAdapter restAdapterWithLeTv
            = new RestAdapter
            .Builder()
            .setClient(new OkClient(MyApplication.okHttpClient()))
            .setEndpoint(AcString.LETV_URL_BASE)
            .build();

    private static RestAdapter restAdapterWithAiXiFanDanMu
            = new RestAdapter
            .Builder()
            .setClient(new OkClient(MyApplication.okHttpClient()))
            .setEndpoint(AcString.URL_AIXIFAN_DANMU)
            .build();

    public static AcApi.getAcPartition getAcPartition() {
        AcApi.getAcPartition acPartition = restAdapterWithAcFunApiServer.create(AcApi.getAcPartition.class);

        return acPartition;
    }

    public static AcApi.getAcRecommend getAcRecommend() {
        AcApi.getAcRecommend acRecommend = restAdapterWithAcFunApiServer.create(AcApi.getAcRecommend.class);

        return acRecommend;
    }

    public static AcApi.getAcContentInfo getAcContentInfo() {
        AcApi.getAcContentInfo acContent = restAdapterWithAcFunApiServer.create(AcApi.getAcContentInfo.class);

        return acContent;
    }

    public static AcApi.getAcContentReply getAcContentReply() {
        AcApi.getAcContentReply acContentReply = restAdapterWithAcFunTV.create(AcApi.getAcContentReply.class);

        return acContentReply;
    }

    public static AcApi.getAcRanking getAcRanking() {
        AcApi.getAcRanking acRanking = restAdapterWithAcFunApiServer.create(AcApi.getAcRanking.class);

        return acRanking;
    }

    public static AcApi.getAcBangumi getAcBangumi() {
        AcApi.getAcBangumi acBangumi = restAdapterWithAcFunIcao.create(AcApi.getAcBangumi.class);

        return acBangumi;
    }

    public static AcApi.getAcContentVideo getAcContentVideo() {
        AcApi.getAcContentVideo acContentVideo = restAdapterWithLeTv.create(AcApi.getAcContentVideo.class);

        return acContentVideo;
    }

    public static AcApi.getAcContentDanMu getAcContentDanMu() {
        AcApi.getAcContentDanMu acContentDanMu = restAdapterWithAiXiFanDanMu.create(AcApi.getAcContentDanMu.class);

        return acContentDanMu;
    }
}
