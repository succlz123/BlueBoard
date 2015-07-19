package org.succlz123.AxBTube.support.helper.acfun;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcApi {
	/*请求基础url*/
	public static final String URL_BASE = "http://api.acfun.tv/apiserver";
	/*请求首页banner*/
	public static final String RECOMMEND_BANNER = "/recommend/list";
	/*应用版本 ?app_version=118*/
	public static final String APP_VERSION = "?app_version=118";
	/*请求响应的设备系统 &sys_name=android*/
	public static final String SYS_NAME = "&sys_name=android";
	/*android版本 &sys_version=5.1.1*/
	public static final String SYS_VERSION = "&sys_version=";
	/*应用市场 &market=m360*/
	public static final String MARKET = "&market=github";
	/*请求响应的设备分辨率 &resolution=1080x1766*/
	public static final String RESOLUTION = "&resolution=";


	public static String getAcRecommendBannerUrl() {
		String url = URL_BASE + RECOMMEND_BANNER + APP_VERSION + SYS_NAME + SYS_VERSION + RESOLUTION;
		url = url.replace(SYS_VERSION, SYS_VERSION + "5.1.1");
		url = url.replace(RESOLUTION, RESOLUTION + "1080x1766");

		return url;
	}

}
