package org.succlz123.blueboard.model.api.acfun;

import org.succlz123.blueboard.config.RetrofitManager;
import org.succlz123.blueboard.model.bean.acfun.AcBangumi;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.model.bean.acfun.AcContentReply;
import org.succlz123.blueboard.model.bean.acfun.AcContentVideo;
import org.succlz123.blueboard.model.bean.acfun.AcEssay;
import org.succlz123.blueboard.model.bean.acfun.AcGetH5ByVid;
import org.succlz123.blueboard.model.bean.acfun.AcReBanner;
import org.succlz123.blueboard.model.bean.acfun.AcReHot;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import retrofit.http.Streaming;
import rx.Observable;

/**
 * Created by succlz123 on 2015/7/19.
 */
public class AcApi {
    /**
     * @return 都有而且不用改变的url参数
     */
    public static HashMap buildBaseMap() {
        HashMap map = new HashMap();
        map.put(AcString.APP_VERSION, AcString.APP_NUM);
        map.put(AcString.SYS_NAME, AcString.SYS_NAME_ANDROID);
        map.put(AcString.SYS_VERSION, AcString.SYS_VERSION_ANDROID);
        map.put(AcString.RESOLUTION, AcString.RESOLUTION_WIDTH_HEIGHT);
        map.put(AcString.MARKET, AcString.MARKET_NAME);
        return map;
    }

    /**
     * 1
     * http://api.acfun.tv/apiserver
     * /recommend/list
     * ?app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 首页横幅
     */
    public static HashMap buildAcReBannerUrl() {
        HashMap map = buildBaseMap();
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
    public static HashMap buildAcReHotUrl(String pageNo) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcString.PAGE_SIZE, AcString.PAGE_SIZE_NUM_20);
        map.put(AcString.PAGE_NO, pageNo);
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
    public static HashMap buildAcReOtherUrl(String channelId, String orderBy, String range) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcString.CHANNEL_IDS, channelId);
        map.put(AcString.PAGE_SIZE, AcString.PAGE_SIZE_NUM_10);
        map.put(AcString.PAGE_NO, AcString.PAGE_NO_NUM_1);
        map.put(AcString.ORDER_BY, orderBy);
        map.put(AcString.RANGE, range);
        return map;
    }

    public interface onAcRecommend {

        @GET(AcString.RECOMMEND_LIST)
        Call<AcReBanner> onAcReBannerResult(@QueryMap() Map<String, String> options);

        @GET(AcString.RECOMMEND_PAGE)
        Call<AcReHot> onAcReHotResult(@QueryMap() Map<String, String> options);

        @GET(AcString.CONTENT_CHANNEL)
        Call<AcReOther> onAcReOtherResult(@QueryMap() Map<String, String> options);
    }

    public static onAcRecommend getAcRecommend() {
        return RetrofitManager.getAcFunApiServer().create(onAcRecommend.class);
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
     * @return 分区内不同板块数据
     */
    public static HashMap buildAcPartitionUrl(String channelIds, String orderBy, String range, String pageSize, String pagerNo) {
        HashMap map = buildBaseMap();
        map.put(AcString.CHANNEL_IDS, channelIds);
        map.put(AcString.PAGE_SIZE, pageSize);
        map.put(AcString.PAGE_NO, pagerNo);
        map.put(AcString.ORDER_BY, orderBy);
        map.put(AcString.RANGE, range);

        return map;
    }

    public interface onAcPartition {

        @GET(AcString.CONTENT_CHANNEL)
        Call<AcReOther> onResult(@QueryMap() Map<String, String> options);

        @GET(AcString.CONTENT_CHANNEL)
        Call<AcEssay> onEssayResult(@QueryMap() Map<String, String> options);
    }

    public static onAcPartition getAcPartition() {
        return RetrofitManager.getAcFunApiServer().create(onAcPartition.class);
    }

    /**
     * http://api.acfun.tv/apiserver
     * /content/info?contentId=2069095
     * &version=2
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频具体信息
     */
    public static HashMap buildAcContentInfoUrl(String contentId) {
        HashMap map = buildBaseMap();
        map.put(AcString.VERSION, AcString.VERSION_NUM_2);
        map.put(AcString.CONTENT_ID, contentId);

        return map;
    }

    public interface onAcContentInfo {
        @GET(AcString.CONTENT_INFO)
        Observable<AcContentInfo> onResult(@QueryMap() Map<String, String> options);
    }

    public static onAcContentInfo getAcContentInfo() {
        return RetrofitManager.getAcFunApiServer().create(onAcContentInfo.class);
    }

    /**
     * http://www.acfun.tv
     * /comment/content/list?version=4&contentId=2086956
     * &pageSize=20&pageNo=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频的评论信息
     */
    public static HashMap buildAcContentReplyUrl(String contentId, String pageSize, String pageNo) {
        HashMap map = buildBaseMap();
        map.put(AcString.VERSION, AcString.VERSION_NUM_4);
        map.put(AcString.CONTENT_ID, contentId);
        map.put(AcString.PAGE_SIZE, pageSize);
        map.put(AcString.PAGE_NO, pageNo);

        return map;
    }

    public interface onAcContentReply {
        @GET(AcString.CONTENT_REPLY)
        Call<AcContentReply> onResult(@QueryMap() Map<String, String> options);
    }

    public static onAcContentReply getAcContentReply() {
        return RetrofitManager.getAcFunTv().create(onAcContentReply.class);
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
     * @return 乐视视频的播放地址
     */
    public static HashMap buildAcContentVideoUrl(String sourceId) {
        HashMap map = buildBaseMap();
        map.put(AcString.UU, AcString.UU_STRING);
        map.put(AcString.CF, AcString.CF_TYPE);
        map.put(AcString.FORMAT, AcString.FORMAT_TYPE);
        map.put(AcString.LETV_VER, AcString.LETV_VER_NUM);
        map.put(AcString.SIGN, AcString.SIGNXXXXX);
        map.put(AcString.VU, sourceId);

        return map;
    }

    public interface onAcContentLeTvVideo {
        @GET(AcString.LETV_URL_GPC)
        Call<AcContentVideo> onContentResult(@QueryMap() Map<String, String> options);
    }

    public static onAcContentLeTvVideo getAcContentLeTvVideo() {
        return RetrofitManager.getLeTv().create(onAcContentLeTvVideo.class);
    }

    /**
     * http://www.acfun.tv
     * /video/getH5ByVid.aspx
     * ?vid=2268176
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 其他视频的播放地址 html5(优酷 土豆 渣浪 企鹅)
     */
    public static HashMap buildAcContentHtml5VideoUrl(String videoId) {
        HashMap map = buildBaseMap();
        map.put(AcString.VID, videoId);

        return map;
    }

    public interface onAcContentHtml5Video {
        @GET(AcString.VIDEO_GET_H5_BY_VID)
        Call<AcGetH5ByVid> onContentResult(@QueryMap() Map<String, String> options);
    }

    public static onAcContentHtml5Video getAcContentHtml5Video() {
        return RetrofitManager.getAcFunTv().create(onAcContentHtml5Video.class);
    }

    public interface onVideoDownloadUrl {
        @GET("/{url}")
        Call onResult(@Path(value = "url", encoded = false) String url);
    }

    /**
     * http://danmu.aixifan.com
     * /2522561
     * ?app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 弹幕信息地址
     */
    public static HashMap buildAcContentDanMuUrl() {
        HashMap map = buildBaseMap();

        return map;
    }

    public interface onAcContentDanMu {
        @GET("{sourceId}")
        Call onContentResult(@QueryMap() Map<String, String> options, @Path("sourceId") String sourceId);
    }

    /**
     * http://icao.acfun.tv
     * /bangumi/week
     * ?bangumiTypes=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 每周新番时间表
     */
    public static HashMap buildAcBangumiUrl(String types) {
        HashMap map = buildBaseMap();
        map.put(AcString.BANGUMI_TYPES, types);
        return map;
    }

    public interface onAcBangumi {
        @GET(AcString.BANGUMI_WEEK)
        Call<AcBangumi> onResult(@QueryMap() Map<String, String> options);
    }

    public static onAcBangumi getAcBangumi() {
        return RetrofitManager.getAcFunICao().create(onAcBangumi.class);
    }

    /**
     * http://api.acfun.tv/apiserver/
     * content/rank
     * ?pageSize=20&pageNo=1
     * &channelIds=96,97,98,99,100,93,94,95,101,102,103,104,105,86,87,88,89,121,106,107,108,109,67,120,90,91,92,122,83,84,85,82
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 每周新番时间表
     */
    public static HashMap buildAcRankingUrl(String channelIds, String pageNo) {
        HashMap map = buildBaseMap();
        map.put(AcString.CHANNEL_IDS, channelIds);
        map.put(AcString.PAGE_SIZE, AcString.PAGE_SIZE_NUM_20);
        map.put(AcString.PAGE_NO, pageNo);
        return map;
    }

    public interface onAcRanking {
        @GET(AcString.CONTENT_RANK)
        Call<AcReOther> onRankingResult(@QueryMap() Map<String, String> options);
    }

    public static onAcRanking getAcRanking() {
        return RetrofitManager.getAcFunApiServer().create(onAcRanking.class);
    }

    public interface onDownLoad {
        @GET(AcString.CONTENT_RANK)
        @Streaming
        Call<AcReOther> getData(@QueryMap() Map<String, String> options);
    }
}
