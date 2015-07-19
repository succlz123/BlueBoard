package org.succlz123.AxBTube.support.helper.bilibili;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fashi on 2015/7/19.
 */
public class BiliUrl {
	private static final int MODULE_BANNER = 0;
	private static final int MODULE_BANGUMI = 0;
	private static final int MODULE_INDEX = 0;


	public static String get(int module) {
		String androidId = GlobalUtils.getAndroidId(MyApplication.getInstance().getApplicationContext());
		String ts = String.valueOf(System.currentTimeMillis());

		StringBuilder sb = new StringBuilder();

		sb.append(BiliApi.BASE);

		if (module == MODULE_BANNER) {
			sb.append(BiliApi.BANNER);
		} else if (module == MODULE_BANGUMI) {
			sb.append(BiliApi.BANGUMI);
		} else if (module == MODULE_INDEX) {
			sb.append(BiliApi.INDEX);
		}

		sb.append(BiliApi.TS + ts);
		if (androidId != null) {
			sb.append(BiliApi.HWID + androidId);
		}

		String xx = "85eb6835b0a1034e";
		String yy = "2ad42749773c441109bdc0191257a664";
		sb.append(BiliApi.SIGN);

		sb.append(BiliApi.APPKEY);
		sb.append(BiliApi.DEVICE);
		sb.append(BiliApi.ULV);
		sb.append(BiliApi.TYPE);
		sb.append(BiliApi.TEST);
		sb.append(BiliApi.SCREEN);
		sb.append(BiliApi.PLATFORM);

		String url = sb.toString();
		if (false) {
			String access_key = "";
			url = url + BiliApi.ACCESS_KEY + access_key;
		}

		return url;
	}


	public static String getSign(String urlBase) {
		byte[] hash;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			hash = md.digest(urlBase.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuffer sb = new StringBuffer(hash.length * 2);

		for (byte b : hash) {
			int i = (b & 0xFF);
			if (i < 0x10) sb.append('0');
			sb.append(Integer.toHexString(i));
		}

		return sb.toString();
	}
}
