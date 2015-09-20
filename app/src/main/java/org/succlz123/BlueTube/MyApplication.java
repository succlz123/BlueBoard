package org.succlz123.bluetube;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.succlz123.bluetube.support.config.FrescoConfig;

import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/6.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    public static MyApplication getsInstance() {
        return sInstance;
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();

        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //由android系统帮你实例化的
        sInstance = this;

        refWatcher = LeakCanary.install(sInstance);

        ButterKnife.setDebug(BuildConfig.DEBUG);

        Fresco.initialize(sInstance, FrescoConfig.getImagePipelineConfig(sInstance));




    }


}
