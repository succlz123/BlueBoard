package org.succlz123.AxBTube;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by fashi on 2015/7/6.
 */
public class MyApplication extends Application {

	private static MyApplication instance;

	public static MyApplication getInstance() {
		return instance;
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
		instance = this;
		refWatcher = LeakCanary.install(this);


	}


}
