package org.succlz123.blueboard;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.succlz123.blueboard.model.config.FrescoConfig;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

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

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        refWatcher = LeakCanary.install(sInstance);

//        ButterKnife.setDebug(BuildConfig.DEBUG);
//        CrashReport.initCrashReport(this, "900012750", false);

        Fresco.initialize(sInstance, FrescoConfig.getImagePipelineConfig(sInstance));


    }



    public void postOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }
}
