package org.succlz123.nbdownload;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.URLUtil;

import org.succlz123.nbdownload.dao.RequestDao;

import java.util.List;

/**
 * Created by succlz123 on 15/9/11.
 */
public class NBDownloadManager {
    private Context mContext;
    private RequestDao mRequestDao;
    private static android.os.Handler sHandler;

    private NBDownloadManager() {

    }

    public NBDownloadManager(Context context) {
        this.mContext = context;
        if (context != null) {
            mRequestDao = RequestDao.newInstance(context);
        }
        sHandler = new android.os.Handler(Looper.getMainLooper());
    }

    public void start(NBDownloadRequest downloadRequest, NBDownloadListener listener) {
        String url = downloadRequest.getUrl();
        String filePath = downloadRequest.getFilePath();
        String name = downloadRequest.getName();

        if (!isComplete(url, filePath)) {
            sendError(NBDownloadException.DOWNLOAD_URL_OR_FILEPATH_IS_NULL, listener);
            return;
        }

        if (!isValid(url)) {
            sendError(NBDownloadException.DOWNLOAD_URL_IS_NOT_VALID, listener);
            return;
        }

//        List<NBDownloadRequest> requestList = mRequestDao.query("name", name);
//        if (requestList.size() > 0) {
//            sendError(NBDownloadException.DOWNLOAD_REQUEST_IS_READY_IN_THE_QUEUE, listener);
//            return;
////            NBDownloadRequest queryRequest = requestList.get(0);
////            switch (queryRequest.getStatus()) {
////                case NBDownloadStatus.STATUS_STOPPED:
////                    NBDownloadTask.getInstance(null).download(mRequestDao, request, listener);
////                    return;
////                default:
////                    sendError(NBDownloadException.DOWNLOAD_REQUEST_IS_READY_IN_THE_QUEUE, listener);
////                    return;
////            }
//        }

//        if (mRequestDao.add(request) == 1) {
//            NBDownloadTask.getInstance(null).download(mRequestDao, request, listener);
//        }

        NBDownloadTask.getInstance(null).download(mRequestDao, downloadRequest, listener);

    }

    public void stop(NBDownloadRequest request, NBDownloadListener listener) {
        if (TextUtils.isEmpty(request.getName())) {
            sendError(NBDownloadException.DOWNLOAD_URL_OR_FILEPATH_IS_NULL, listener);
            return;
        }

        NBDownloadTask.getInstance(null).stop(mRequestDao, request, listener);
    }

    public void cancel(NBDownloadRequest request, NBDownloadListener listener) {
        if (TextUtils.isEmpty(request.getName())) {
            sendError(NBDownloadException.DOWNLOAD_URL_OR_FILEPATH_IS_NULL, listener);
            return;
        }

        NBDownloadTask.getInstance(null).cancel(mRequestDao, request, listener);
    }

    public void resume(NBDownloadRequest request, NBDownloadListener listener) {

        NBDownloadTask.getInstance(null).download(mRequestDao, request, listener);
    }

    public void toggle(String name, NBDownloadListener listener) {
        if (TextUtils.isEmpty(name)) {
            sendError(NBDownloadException.DOWNLOAD_URL_OR_FILEPATH_IS_NULL, listener);
            return;
        }

        List<NBDownloadRequest> requestList = mRequestDao.query("name", name);

        if (requestList.size() > 0) {
            NBDownloadRequest request = requestList.get(0);
            switch (request.getStatus()) {
                case NBDownloadStatus.STATUS_STOPPED:
                    resume(request, listener);
                    break;
                case NBDownloadStatus.STATUS_RUNNING:
                    stop(request, listener);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 检查url,name,path是否为空
     *
     * @return
     */
    private boolean isComplete(String url, String filePath) {
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(filePath);
    }

    /**
     * 验证下载链接是否合法
     *
     * @return
     */
    private boolean isValid(String url) {
        return URLUtil.isNetworkUrl(url);
    }

    public static void sendStart(final NBDownloadRequest nbDownloadRequest, final NBDownloadListener listener) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onStart(nbDownloadRequest);
            }
        });
    }

    public static void sendError(final int code, final NBDownloadListener listener) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(new NBDownloadException(code));
            }
        });
    }

    public static void sendSuccess(final String filePath, final NBDownloadListener listener) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFinish(filePath);
            }
        });
    }
}
