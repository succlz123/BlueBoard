package org.succlz123.blueboard;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.succlz123.blueboard.model.config.FrescoConfig;
import org.succlz123.blueboard.service.DownloadService;
import org.succlz123.okdownload.OkDownloadManager;
import org.succlz123.okdownload.OkDownloadRequest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by succlz123 on 2015/7/6.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();

        return application.refWatcher;
    }

    private Handler mHandler = new Handler();
    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        refWatcher = LeakCanary.install(sInstance);

//        ButterKnife.setDebug(BuildConfig.DEBUG);
//        CrashReport.initCrashReport(this, "900012750", false);

        Fresco.initialize(sInstance, FrescoConfig.getImagePipelineConfig(sInstance));

//        Stetho.initializeWithDefaults(this);
//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(new StethoInterceptor());

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mArrayList.add("1");
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mArrayList.remove("1");
                if (mArrayList.size() == 0) {
                    DownloadService.stopService(MyApplication.this);
                }
            }
        });
    }

    public ArrayList<OkDownloadRequest> quaryAllDonwload() {
        ArrayList<OkDownloadRequest> request = OkDownloadManager.getInstance(this).queryAll();
        Collections.reverse(request);

        return request;
    }

    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }
}
