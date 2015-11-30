package org.succlz123.blueboard.model.api.bilibili;

/**
 * Created by succlz123 on 2015/7/19.
 */
public class BiliApi {
	//设备类型
	public static final String DEVICE = "?_device=android";
	//设备的device id
	public static final String HWID = "&_hwid=";
	//默认不知道什么意思
	public static final String ULV = "&_ulv=10000";
	//应用在用户申请登陆后获取到的access_key 可以此access_key访问需要用户权限的操作
	public static final String ACCESS_KEY = "&access_key=";
	//官方客户端appkey
	public static final String APPKEY = "appkey=c1b107428d337928";
	//应用校验密匙
	public static final String SIGN = "&sign=956410660f4c44de4de9d2bfc0418b6a";
	//当前时间截 (UNIX TIMESTAMP)
	public static final String TS = "&ts=1437286536270";
	//客户端平台适配及统计用
	public static final String PLATFORM = "&platform=android";
	//屏幕密度
	public static final String SCREEN = "&screen=xxhdpi";
	//返回数据类型 默认json
	public static final String TYPE = "&type=json";
	//需要请求的功能
	public static final String MODULE = "&module=";
	//默认不知道什么意思
	public static final String TEST = "&test=0";

	//官方客户端版本号
	public static final String BUILD = "&build=401002";
	//JSONp调用方式时回调函数名称
	public static final String CALLBACK = "&callback";


	public static final String USER_AGENT = "Mozilla/5.0 BiliDroid/4.1.2 (bbcallen@gmail.com)";

	//请求banner
	public static final String BANNER = "&module=banner";
	//请求bangumi
	public static final String BANGUMI = "&module=bangumi";
	//请求index
	public static final String INDEX = "&module=index";
	//请求基础url
	public static final String BASE = "http://app.bilibili.com/bangumi/operation_module";
}
