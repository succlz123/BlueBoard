package org.succlz123.AxBTube.support.config;

import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;

import retrofit.RestAdapter;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitConfig {

    private static RestAdapter restAdapterWithAcFunApiServer
            = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();


    private static RestAdapter restAdapterWithAcFunTV
            = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_TV).build();

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
}
