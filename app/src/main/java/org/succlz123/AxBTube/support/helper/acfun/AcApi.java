package org.succlz123.AxBTube.support.helper.acfun;

import org.succlz123.AxBTube.bean.acfun.AcContent;
import org.succlz123.AxBTube.bean.acfun.AcContentVideo;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;

import java.util.HashMap;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcApi {
    /**
     * @return 都有而且不用改变的url参数
     */
    public static HashMap getBaseMap() {
        HashMap map = new HashMap();
        map.put(AcString.APP_VERSION, AcString.APP_NUM);
        map.put(AcString.SYS_NAME, AcString.SYS_NAME_ANDROID);
        map.put(AcString.SYS_VERSION, AcString.SYS_VERSION_ANDROID);
        map.put(AcString.RESOLUTION, AcString.RESOLUTION_WIDTH_HEIGHT);
        return map;
    }

    /**1
     * http://api.acfun.tv/apiserver
     * /recommend/list
     * ?app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 首页横幅
     */
    public static HashMap getAcReBannerUrl() {
        HashMap map = getBaseMap();
        return map;
    }

    /**
     * http://api.acfun.tv/apiserver
     * /recommend/page
     * ?pageSize=10&pageNo=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 热门焦点
     */
    public static HashMap getAcReHotUrl() {
        HashMap<String, String> map = getBaseMap();
        map.put(AcString.PAGE_SIZE, AcString.PAGE_SIZE_NUM);
        map.put(AcString.PAGE_NO, AcString.PAGE_NO_NUM);
        return map;
    }

    /**
     * http://api.acfun.tv/apiserver/
     * content/channel
     * ?channelIds=106,107,108,109,67,120
     * &pageSize=20&pageNo=1
     * &orderBy=5
     * &range=604800000
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @param channelId 根据不同的channelId
     * @param orderBy   按照什么来排序来还返回 最后回复 最新发布等
     * @param range     返回数据是多少时间内统计的 一周 一月 三月 总共
     * @return 返回不同的分区信息
     */
    public static HashMap getAcReOtherUrl(String channelId, String orderBy, String range) {
        HashMap<String, String> map = getBaseMap();
        map.put(AcString.CHANNEL_IDS, channelId);
        map.put(AcString.PAGE_SIZE, AcString.PAGE_SIZE_NUM);
        map.put(AcString.PAGE_NO, AcString.PAGE_NO_NUM);
        map.put(AcString.ORDER_BY, orderBy);
        map.put(AcString.RANGE, range);
        return map;
    }

    public interface getAcRecommend {

        @GET(AcString.RECOMMEND_LIST)
        void onAcReBannerResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcReBanner> cb);

        @GET(AcString.RECOMMEND_PAGE)
        void onAcReHotResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcReHot> cb);

        @GET(AcString.CONTENT_CHANNEL)
        void onAcReOtherResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcReOther> cb);
    }


    /**
     * http://api.acfun.tv/apiserver
     * /content/channel
     * ?channelIds=67
     * &pageSize=20&pageNo=1
     * &orderBy=7
     * &range=604800000
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 新番连载
     */
    public static HashMap getAcAnimationBangumiUrl(String orderBy, String range) {
        HashMap map = getBaseMap();
        map.put(AcString.CHANNEL_IDS, AcString.BANGUMI);
        map.put(AcString.PAGE_SIZE, AcString.PAGE_NO_NUM);
        map.put(AcString.PAGE_NO, AcString.PAGE_NO_NUM);
        map.put(AcString.ORDER_BY, orderBy);
        map.put(AcString.RANGE, range);

        return map;
    }

    public interface getAcAnimationBangumi {
        @GET(AcString.CONTENT_CHANNEL)
        void onResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcReHot> cb);
    }


    /**
     * http://api.acfun.tv/apiserver
     * /content/info?contentId=2069095
     * &version=2&app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频具体信息
     */
    public static HashMap getAcContentUrl(String contentId) {
        HashMap map = getBaseMap();
        map.put(AcString.VERSION, AcString.VERSION_NUM);
        map.put(AcString.CONTENT_ID, contentId);
        return map;
    }

    public interface getAcContent {
        @GET(AcString.CONTENT_INFO)
        void onContentResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcContent> cb);
    }

    /**
     * http://api.letvcloud.com/gpc.php
     * ?uu=2d8c027396
     * &vu=311043eae0
     * &cf=android
     * &format=json
     * &ver=2.0
     * &sign=signxxxxx
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频
     */
    public static HashMap getAcContentVideoUrl(String sourceId) {
        HashMap map = getBaseMap();
        map.put(AcString.UU, AcString.UU_STRING);
        map.put(AcString.CF, AcString.CF_TYPE);
        map.put(AcString.FORMAT, AcString.FORMAT_TYPE);
        map.put(AcString.LETV_VER, AcString.LETV_VER_NUM);
        map.put(AcString.SIGN, AcString.SIGNXXXXX);
        map.put(AcString.VU, sourceId);
        return map;
    }

    public interface getAcContentVideo {
        @GET(AcString.LETV_URL_GPC)
        void onContentResult(@QueryMap() Map<String, String> options, retrofit.Callback<AcContentVideo> cb);
    }
}
