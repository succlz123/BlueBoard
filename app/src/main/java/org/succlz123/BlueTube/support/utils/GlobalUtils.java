package org.succlz123.bluetube.support.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by fashi on 2015/7/6.
 */
public class GlobalUtils {

    /**
     * 显示Toast 时间为short
     *
     * @param context
     * @param tip
     */
    public static void showToastShort(Context context, String tip) {
        if (context == null) {
            Toast.makeText(MyApplication.getsInstance().getApplicationContext(), tip, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示Toast 时间为long
     *
     * @param context
     * @param tip
     */
    public static void showToastLong(Context context, String tip) {
        if (context == null) {
            Toast.makeText(MyApplication.getsInstance().getApplicationContext(), tip, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
        }
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
     * 通过activity得到屏幕信息
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
     * 通过WINDOW_SERVICE获取display对象
     *
     * @param context
     * @return
     */
    public static String getScreenWidthxHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        return width + "x" + height;
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
        String androidId
                = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

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
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        return sf.format(d);
    }

    public static String getDateToStringWithYDHM(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日 HH:mm");

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
    public static LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater inflater
                = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

    /**
     * 高效拼接url
     *
     * @param params
     * @return
     */
    public static String getUrl(HashMap<String, String> params) {
        String url = null;
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url = sb.toString();
        }
        return url;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
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
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wiFiNetworkInfo
                    = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wiFiNetworkInfo != null) {
                return wiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断移动网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileNetworkInfo
                    = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetworkInfo != null) {
                return mobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }

//	public static String getHandSetInfo() {
//		String handSetInfo =
//				"手机型号:" + android.os.Build.MODEL
//						+ ",系统版本:" + android.os.Build.VERSION.RELEASE;
//		return handSetInfo;
//
//	}

    /**
     * Base64 编码
     *
     * @param string
     * @return
     */
    public static String encodeByBase64(String string) {
        byte[] encode = Base64.encode(string.getBytes(), Base64.DEFAULT);
        String result = new String(encode);

        return result;
    }

    /**
     * Base64 解码
     *
     * @param string
     * @return
     */
    public static String decodeByBase64(String string) {
        byte[] decode = Base64.decode(string, Base64.DEFAULT);
        String result = new String(decode);

        return result;
    }

    /**
     * inputStream 转成 string
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int i; (i = in.read(buf)) != -1; ) {
            baos.write(buf, 0, i);
        }

        return baos.toString("UTF-8");
    }

}