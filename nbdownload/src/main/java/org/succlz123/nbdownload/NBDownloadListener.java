package org.succlz123.nbdownload;

/**
 * Created by succlz123 on 15/9/11.
 */
public interface NBDownloadListener {

    void onStart(NBDownloadRequest nbDownloadRequest);

    void onProgress(String name,int progress, long cacheSize, long totalSize, long averageSpeed,long realTimeSpeed);

    void onStop(String name);

    void onCancel(NBDownloadRequest nbDownloadRequest);

    void onFinish(String filePath);

    void onError(NBDownloadException error);
}
