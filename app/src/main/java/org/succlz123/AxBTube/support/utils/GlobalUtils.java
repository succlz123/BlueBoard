package org.succlz123.AxBTube.support.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Toast;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fashi on 2015/7/6.
 */
public class GlobalUtils {

	/**
	 * Toast显示
	 *
	 * @param context
	 * @param tip
	 */
	public static void showToast(Context context, String tip) {
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 是否有SDCard
	 *
	 * @return
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();

		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 检查网络
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkNetState(Context context) {
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						netstate = true;
						break;
					}
				}
			}
		}

		return netstate;
	}

	/**
	 * 得到屏幕信息
	 *
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenDisplayMetrics(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);

		return metric;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 *
	 * @param context
	 * @param dpValue
	 */
	public static int dip2pix(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 *
	 * @param context
	 * @param pxValue
	 */
	public static int pix2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取版本号和版本次数
	 *
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context, int type) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			if (type == 1) {
				return String.valueOf(pi.versionCode);
			} else {
				return pi.versionName;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getScreenHeight(Activity context) {
		if (context == null) {
			return 0;
		}
		return context.findViewById(android.R.id.content).getHeight();
	}

	public static int getActionBarSize(Activity context) {
		if (context == null) {
			return 0;
		}
		TypedValue typedValue = new TypedValue();
		int[] textSizeAttr = new int[]{R.attr.actionBarSize};
		int indexOfAttrTextSize = 0;
		TypedArray a = context.obtainStyledAttributes(typedValue.data, textSizeAttr);
		int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
		a.recycle();

		return actionBarSize;
	}

	/**
	 * 获取设备信息
	 *
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
		sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
		sb.append("\nLine1Number = " + tm.getLine1Number());
		sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		sb.append("\nNetworkType = " + tm.getNetworkType());
		sb.append("\nPhoneType = " + tm.getPhoneType());
		sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		sb.append("\nSimOperator = " + tm.getSimOperator());
		sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());

		return sb.toString();
	}

	/**
	 * 获取设备android id
	 *
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String androidId = Settings.Secure.getString(context.getContentResolver()
				, Settings.Secure.ANDROID_ID);

		return androidId;
	}

	/**
	 * 获取系统时间 格式为："yyyy/MM/dd"
	 */
	public static String getCurrentDate() {
		Date d = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

		return sf.format(d);
	}

	/**
	 * 时间戳转换成字符窜
	 */
	public static String getDateToString(long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

		return sf.format(d);
	}

	/**
	 * 将字符串转为时间戳
	 */
	public static long getStringToDate(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date.getTime();
	}

	/**
	 * 获取layoutInflater
	 *
	 * @return
	 */
	public static LayoutInflater getLayoutInflater() {
		LayoutInflater inflater = (LayoutInflater) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		return inflater;
	}

	/**
	 * 字符串拼接 by StringBuilder
	 *
	 * @param strings
	 * @return
	 */
	public static String getStringByStringBuilder(String... strings) {
		StringBuilder builder = new StringBuilder();
		for (String string : strings) {
			builder.append(string);
		}

		return builder.toString();
	}

	/**
	 * 字符串拼接 by StringBuffer
	 *
	 * @param strings
	 * @return
	 */
	public static String getStringByStringBuffer(String... strings) {
		StringBuffer builder = new StringBuffer();
		for (String string : strings) {
			builder.append(string);
		}

		return builder.toString();
	}
}