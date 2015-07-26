package org.succlz123.AxBTube.support.helper.acfun;

import org.succlz123.AxBTube.support.utils.GlobalUtils;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcApi {
	/*请求基础url*/
	public static final String URL_BASE = "http://api.acfun.tv/apiserver";
	/*请求首页banner*/
	public static final String RECOMMEND_BANNER = "/recommend/list";
	/*请求首页数据*/
	public static final String RECOMMEND_PAGE = "/recommend/page";
	/*请求分区数据*/
	public static final String CONTENT_CHANNEL = "/content/channel";
	/*动画区数据*/
	public static final String ANIMATION = "&channelIds=106,107,108,109,67,120";
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
	/*请求页数*/
	public static final String PAGE_SIZE = "&pageSize=10";

	public static final String PAGE_NO = "&pageNo=1";
	public static final String ORDER_BY = "&orderBy=5";
	public static final String RANGE = "&range=604800000";


	/**
	 * http://api.acfun.tv/apiserver/
	 * recommend/list
	 * ?app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
	 *
	 * @return
	 */
	public static String getAcReBanner() {
		String url = GlobalUtils.getStringByStringBuilder(
				URL_BASE, RECOMMEND_BANNER, APP_VERSION, SYS_NAME, SYS_VERSION, RESOLUTION);
		url = url.replace(SYS_VERSION, SYS_VERSION + "5.1.1");
		url = url.replace(RESOLUTION, RESOLUTION + "1080x1766");

		return url;
	}

	/**
	 * http://api.acfun.tv/apiserver/
	 * recommend/page
	 * ?pageSize=10&pageNo=1
	 * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
	 *
	 * @return
	 */
	public static String getAcReHot() {
		String url = GlobalUtils.getStringByStringBuilder(
				URL_BASE, RECOMMEND_PAGE, APP_VERSION, PAGE_SIZE, PAGE_NO, SYS_NAME, SYS_VERSION, RESOLUTION);
		url = url.replace(SYS_VERSION, SYS_VERSION + "5.1.1");
		url = url.replace(RESOLUTION, RESOLUTION + "1080x1766");

		return url;
	}

	/**
	 * http://api.acfun.tv/apiserver/
	 * content/channel
	 * ?channelIds=106,107,108,109,67,120
	 * &pageSize=20&pageNo=1
	 * &orderBy=5&range=604800000
	 * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
	 *
	 * @return
	 */
	public static String getAcReEntertainment() {
		String url = GlobalUtils.getStringByStringBuilder(
				URL_BASE, CONTENT_CHANNEL, APP_VERSION, ANIMATION, PAGE_SIZE, PAGE_NO, ORDER_BY, RANGE, SYS_NAME, SYS_VERSION, RESOLUTION);
		url = url.replace(SYS_VERSION, SYS_VERSION + "5.1.1");
		url = url.replace(RESOLUTION, RESOLUTION + "1080x1766");

		return url;
	}
}
