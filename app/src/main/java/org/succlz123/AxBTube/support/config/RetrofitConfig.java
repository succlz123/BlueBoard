package org.succlz123.AxBTube.support.config;

import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;

import retrofit.RestAdapter;

/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitConfig {

    private static RestAdapter restAdapter
            = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();


    public static AcApi.getAcPartition getAcPartition() {
        AcApi.getAcPartition acPartition = restAdapter.create(AcApi.getAcPartition.class);

        return acPartition;
    }

    public static AcApi.getAcRecommend getAcRecommend() {
        AcApi.getAcRecommend acRecommend = restAdapter.create(AcApi.getAcRecommend.class);

        return acRecommend;
    }

}
