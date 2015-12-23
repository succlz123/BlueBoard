package org.succlz123.okdownload;

/**
 * Created by succlz123 on 15/9/11.
 */
public interface OkDownloadEnqueueListener extends OkDownloadCancelListener {

    void onStart(int id);

    void onProgress(int progress, long cacheSize, long totalSize);

    void onRestart();

    void onPause();

    void onFinish(int id);
}
