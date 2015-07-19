package org.succlz123.AxBTube.support.utils;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;

/**
 * Created by fashi on 2015/7/7.
 */
public class ViewUtils {

	/**
	 * 显示Toolbar
	 *
	 * @param context
	 * @param toolbar
	 */
	public static void setToolbar(AppCompatActivity context, Toolbar toolbar) {
		context.setSupportActionBar(toolbar);
		ActionBar actionBar = context.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);

		}
	}


	public static void setDrawerToggle(Activity context, DrawerLayout drawerLayout, Toolbar toolbar) {
		ActionBarDrawerToggle drawerToggle =
				new ActionBarDrawerToggle(context, drawerLayout, toolbar, R.string.open, R.string.close) {
					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
					}

					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
					}
				};
		drawerToggle.syncState();
		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.setScrimColor(MyApplication.getInstance()
				.getApplicationContext().getResources().getColor(R.color.shadow_white));
	}
}
