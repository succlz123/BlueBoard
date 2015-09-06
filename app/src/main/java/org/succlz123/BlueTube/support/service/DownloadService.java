package org.succlz123.bluetube.support.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.succlz123.bluetube.bean.FileInfo;

/**
 * Created by succlz123 on 15/9/2.
 */
public class DownloadService extends Service {
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (TextUtils.equals(ACTION_START, intent.getAction())) {
            FileInfo fileInfo = intent.getParcelableExtra("fileInfo");

        } else if (TextUtils.equals(ACTION_STOP, intent.getAction())) {
            FileInfo fileInfo = intent.getParcelableExtra("fileInfo");

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class InitThread extends Thread{

    }
}
