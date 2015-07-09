package org.succlz123.AxBTube;

import android.app.Application;

/**
 * Created by fashi on 2015/7/6.
 */
public class MyApplication extends Application {

	private static MyApplication instance;

	public static MyApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//由android系统帮你实例化的
		instance = this;

	}


}
