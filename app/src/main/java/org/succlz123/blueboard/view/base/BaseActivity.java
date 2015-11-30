package org.succlz123.blueboard.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;

import org.succlz123.blueboard.MyApplication;

/**
 * Created by succlz123 on 2015/7/8.
 */
public class BaseActivity extends AppCompatActivity {

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
}
