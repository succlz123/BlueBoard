package org.succlz123.blueboard.controller.base;

import com.squareup.leakcanary.RefWatcher;

import org.succlz123.blueboard.MyApplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by succlz123 on 2015/7/8.
 */
public class BaseActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    protected  <T extends View> T f(int resId) {
        return (T) super.findViewById(resId);
    }
}
