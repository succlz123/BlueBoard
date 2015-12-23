package org.succlz123.okdownload;

import com.squareup.okhttp.OkHttpClient;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by succlz123 on 15/9/11.
 */
public class OkDownloadManager {
    private static final String TAG = "OkDownloadManager";

    private static Context sContext;
    private static OkDatabaseHelp sOkDatabaseHelp;

    private OkHttpClient mOkHttpClient;
    private OkDownloadRequest mOkDownloadRequest;
    private OkDownloadEnqueueListener mOkDownloadEnqueueListener;

    private HashMap<String, OkDownloadTask> mOkDownloadTasks = new HashMap<>();

    private OkDownloadManager() {
    }

    public static OkDownloadManager getInstance(Context context) {
        if (context == null) {
            return null;
        }
        OkDownloadManager instance = HelpHolder.INSTANCE;

        if (sContext == null) {
            sContext = context.getApplicationContext();
        }
        if (sOkDatabaseHelp == null) {
            sOkDatabaseHelp = OkDatabaseHelp.getInstance(sContext);
        }

        return instance;
    }

    private static class HelpHolder {
        private static final OkDownloadManager INSTANCE = new OkDownloadManager();
    }

    public void enqueue(OkDownloadRequest okDownloadRequest, OkDownloadEnqueueListener okDownloadEnqueueListener) {
        if (okDownloadRequest.getOkHttpClient() != null) {
            mOkHttpClient = okDownloadRequest.getOkHttpClient();
        }

        if (okDownloadRequest == null || okDownloadEnqueueListener == null) {
            return;
        }

        mOkDownloadRequest = okDownloadRequest;
        mOkDownloadEnqueueListener = okDownloadEnqueueListener;

        if (!isRequestValid()) {
            return;
        }

        ArrayList<OkDownloadRequest> requestList = sOkDatabaseHelp.execQuery("sign", mOkDownloadRequest.getSign());

        if (requestList.size() > 0) {
            OkDownloadRequest queryRequest = requestList.get(0);

            switch (queryRequest.getStatus()) {
                case OkDownloadStatus.START:
                    onPause(queryRequest, mOkDownloadEnqueueListener);
                    break;
                case OkDownloadStatus.PAUSE:
                    onStart(queryRequest, mOkDownloadEnqueueListener);
                    break;
                case OkDownloadStatus.FINISH:
                    mOkDownloadEnqueueListener.onError(new OkDownloadError(OkDownloadError.DOWNLOAD_REQUEST_IS_COMPLETE));
                    break;
                default:
                    break;
            }
        } else {
            onStart(mOkDownloadRequest, mOkDownloadEnqueueListener);
        }
    }

    public void onStart(OkDownloadRequest okDownloadRequest, OkDownloadEnqueueListener listener) {
        if (!isUrlValid(okDownloadRequest.getUrl())) {
            mOkDownloadEnqueueListener.onError(new OkDownloadError(OkDownloadError.DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID));
            return;
        }

        String sign = okDownloadRequest.getSign();
        if (sign == null) {
            Log.w(TAG, "sign = null");
            return;
        }

        OkDownloadTask downloadTask = mOkDownloadTasks.get(sign);

        if (downloadTask == null) {
            downloadTask = new OkDownloadTask(sContext, mOkHttpClient, sOkDatabaseHelp);
        }

        downloadTask.start(okDownloadRequest, mOkDownloadEnqueueListener);
    }

    public void onPause(OkDownloadRequest okDownloadRequest, OkDownloadEnqueueListener listener) {
        if (!isUrlValid(okDownloadRequest.getUrl())) {
            mOkDownloadEnqueueListener.onError(new OkDownloadError(OkDownloadError.DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID));
            return;
        }

        String sign = okDownloadRequest.getSign();
        if (sign == null) {
            Log.w(TAG, "sign = null");
            return;
        }

        OkDownloadTask downloadTask = mOkDownloadTasks.get(sign);

        if (downloadTask == null) {
            downloadTask = new OkDownloadTask(sContext, mOkHttpClient, sOkDatabaseHelp);
        }

        downloadTask.pause(okDownloadRequest, listener);
    }

    public void onCancel(String sign, OkDownloadCancelListener listener) {
//        if (!isUrlValid(url)) {
//            mOkDownloadEnqueueListener.onError(new OkDownloadError(OkDownloadError.DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID));
//            return;
//        }

         if (sign == null) {
            Log.w(TAG, "sign = null");
            return;
        }

        OkDownloadTask downloadTask = mOkDownloadTasks.get(sign);

        if (downloadTask == null) {
            downloadTask = new OkDownloadTask(sContext, mOkHttpClient, sOkDatabaseHelp);
        }

        downloadTask.cancel(sign, listener);
    }

//    public void onPauseAll() {
//        if (mOkDownloadTask == null) {
//            return;
//        }
//        mOkDownloadTask.pauseAll();
//    }

    public ArrayList<OkDownloadRequest> queryAll() {
        return sOkDatabaseHelp.execQueryAll();
    }

    public ArrayList<OkDownloadRequest> queryById(int id) {
        return sOkDatabaseHelp.execQuery("id", String.valueOf(id));
    }

    private boolean isRequestValid() {
        String url = mOkDownloadRequest.getUrl();
        String filePath = mOkDownloadRequest.getFilePath();

        if (!isRequestComplete(url, filePath) || !isUrlValid(url)) {
            mOkDownloadEnqueueListener.onError(new OkDownloadError(OkDownloadError.DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID));
            return false;
        }

        return true;
    }

    private boolean isRequestComplete(String url, String filePath) {
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(filePath);
    }

    private boolean isUrlValid(String url) {
        return URLUtil.isNetworkUrl(url);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableExternalMemorySize() {
        if (hasSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (hasSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }
}
