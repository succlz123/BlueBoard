package org.succlz123.nbdownload;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.succlz123.nbdownload.dao.RequestDao;
import org.succlz123.nbdownload.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by succlz123 on 15/9/11.
 */
public class NBDownloadTask {
    private static final String TAG = "NBDownloadTask";

    private static NBDownloadTask sInstance;
    private OkHttpClient mOkHttpClient;

    private NBDownloadTask(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            this.mOkHttpClient = okHttpClient;
        } else {
            mOkHttpClient = new OkHttpClient();
        }
    }

    public static NBDownloadTask getInstance(OkHttpClient okHttpClient) {
        if (sInstance == null) {
            synchronized (NBDownloadTask.class) {
                if (sInstance == null) {
                    sInstance = new NBDownloadTask(okHttpClient);
                }
            }
        }
        return sInstance;
    }

    public void stop(RequestDao requestDao, NBDownloadRequest nbDownloadRequest, NBDownloadListener listener) {
        String name = nbDownloadRequest.getName();

        mOkHttpClient.cancel(name);

        nbDownloadRequest.setStatus(NBDownloadStatus.STATUS_STOPPED);
        requestDao.update(nbDownloadRequest);
        listener.onStop(name);
    }

    public void cancel(RequestDao requestDao, NBDownloadRequest nbDownloadRequest, NBDownloadListener listener) {
        if (nbDownloadRequest != null) {
            String name = nbDownloadRequest.getName();
            List<NBDownloadRequest> requestList = requestDao.query("name", name);
            if (requestList.size() > 0) {
                NBDownloadRequest downloadRequest = requestList.get(0);
                mOkHttpClient.cancel(name);

                String filePath = downloadRequest.getFilePath();
                if (filePath != null) {
                    File file = new File(downloadRequest.getFilePath());
                    file.delete();
                }
                requestDao.delete(requestList.get(0));

                listener.onCancel(downloadRequest);
            }
        }
    }

    public void download(final RequestDao requestDao, final NBDownloadRequest nbDownloadRequest, final NBDownloadListener listener) {
        Request.Builder builder = new Request.Builder();

        final String url = nbDownloadRequest.getUrl();
        final String filePath = nbDownloadRequest.getFilePath();
        final String name = nbDownloadRequest.getName();

        //获得要写入本地的文件长度 如果不存在 长度为0 如果存在就是已经缓存了的文件大小
        final File file = new File(filePath);
        final long range = file.length();

        builder.url(url)
                .tag(name)
                .addHeader("User-Agent", "BlueTube")
                .addHeader("Connection", "Keep-Alive");

        if (range > 0) {
            builder.addHeader("Range", "bytes=" + range + "-");
        }

        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        //开始下载
        NBDownloadManager.sendStart(nbDownloadRequest, listener);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                NBDownloadManager.sendError(NBDownloadException.DOWNLOAD_TASK_IS_UNKNOWN_ERROR, listener);
            }

            @Override
            public void onResponse(Response response) {
                boolean isSuccessful = response.isSuccessful();
                //出现3xx是服务器转发地址
                boolean isRedirect = response.isRedirect();
                LogUtils.e(TAG, "HTTP STATUS CODE: " + response.code());
                //okHttp会自动处理转发
                String url = null;
                String cookies = null;
                //自己处理 没做
                if (isRedirect) {
                    // get redirect url from "location" header field
                    //具体的转发地址在头文件的location里
                    url = response.header("Location");
                    // get the cookie if need, for login
                    cookies = response.header("Set-Cookie");
                    LogUtils.i(TAG, "Redirect Url : " + url);
                } else if (isSuccessful) {
                }

                //                    if (contentType != connection.getContentType()) {
//                        contentType = connection.getContentType();
//                        task.setMimeType(contentType);
//                    }
//                    if (TextUtils.isEmpty(filePath)) {
//                        String disposition = response.header("Content-Disposition");
//                        if (disposition != null) {
//                            // extracts file name from header field
//                            //如果没有指定文件名可以从头文件里取
//                            final String FILENAME = "filename=";
//                            final int startIdx = disposition.indexOf(FILENAME);
//                            final int endIdx = disposition.indexOf(';', startIdx);
//                            filename = disposition.substring(startIdx + FILENAME.length(), endIdx > 0 ? endIdx : disposition.length());
//                        } else {
//                            // extracts file name from URL
//                            filename = url.substring(url.lastIndexOf("/") + 1, url.length());
//                        }
//                        task.setName(filename);
//                    }

                InputStream in = null;
                RandomAccessFile out = null;
                long fileLength = 0;

                long startTime = System.currentTimeMillis();

                nbDownloadRequest.setStatus(NBDownloadStatus.STATUS_RUNNING);
                nbDownloadRequest.setStartTime(startTime);

                if (response.header("Content-Length") != null) {
                    fileLength = Long.valueOf(response.header("Content-Length"));
                    nbDownloadRequest.setTotalSize(fileLength);
                }
                requestDao.update(nbDownloadRequest);

                byte[] bytes = new byte[2048];
                int len = 0;


                long curSize = 0;
                long preTime = System.currentTimeMillis();
                long preSize = 0;
                long averageSpeed = 0;
                long realTimeSpeed = 0;

                try {
                    in = new BufferedInputStream(response.body().byteStream());
                    out = new RandomAccessFile(filePath, "rwd");

                    out.seek(range);
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                        curSize += len;
                        if (fileLength != 0) {
                            long cacheSize = file.length();
                            //平均速度
                            long curTime = System.currentTimeMillis();
                            long startToNowTime = (curTime - startTime);
                            averageSpeed = (cacheSize / startToNowTime * 1000) / 1024;
//                                averageSpeed=  (cacheSize/1024)/(startToNowTime/1000);会除出0
//                                averageSpeed = cacheSize * 1000 / startToNowTime * 1024;
                            //实时速度 每2秒计算1次
                            long usedTime = (curTime - preTime);
                            if (TimeUnit.MILLISECONDS.toSeconds(usedTime) >= 2) {
                                long deltaSize = curSize - preSize;
                                realTimeSpeed = deltaSize / 1024 / (usedTime / 1000);
                                preTime = curTime;
                                preSize = curSize;
                            }
                            int progress = (int) (cacheSize * 100 / fileLength);
                            listener.onProgress(name, progress, cacheSize, fileLength, averageSpeed, realTimeSpeed);
                        }
                    }
                    // 如果下载文件成功，第一个参数为文件的绝对路径
                    if (fileLength != 0 && curSize == fileLength) {
                        long finishTime = System.currentTimeMillis();
                        nbDownloadRequest.setFinishTime(finishTime);
                        nbDownloadRequest.setStatus(NBDownloadStatus.STATUS_FINISHED);
                        requestDao.update(nbDownloadRequest);
                        NBDownloadManager.sendSuccess(filePath, listener);
                    }
                } catch (IOException e) {
                    //下载出错
                    NBDownloadManager.sendError(NBDownloadException.DOWNLOAD_TASK_IS_UNKNOWN_ERROR, listener);
                } finally {
                    try {
                        if (in != null) in.close();
                    } catch (IOException e) {

                    }
                    try {
                        if (out != null) out.close();
                    } catch (IOException e) {

                    }
                }
            }
        });
    }
}