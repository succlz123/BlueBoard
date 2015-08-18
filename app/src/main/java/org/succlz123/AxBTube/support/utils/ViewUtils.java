package org.succlz123.AxBTube.support.utils;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
     * 显示Toolbar 默认标题
     *
     * @param context
     * @param toolbar
     */
    public static void setToolbar(AppCompatActivity context, Toolbar toolbar, Boolean WithHomeButton) {
        context.setSupportActionBar(toolbar);
        if (WithHomeButton) {
            ActionBar actionBar = context.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * 显示Toolbar 添加自定义标题
     *
     * @param context
     * @param toolbar
     */
    public static void setToolbar(AppCompatActivity context, Toolbar toolbar, Boolean WithHomeButton, String title) {
        if (title != null) {
            toolbar.setTitle(title);
        }
        context.setSupportActionBar(toolbar);
        if (WithHomeButton) {
            ActionBar actionBar = context.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * 侧滑drawerToggle同步动画
     *
     * @param context
     * @param drawerLayout
     * @param toolbar
     */
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
        drawerLayout.setScrimColor(MyApplication.getsInstance()
                .getApplicationContext().getResources().getColor(R.color.shadow_white));
    }

    /**
     * 下拉刷新组件颜色设置
     *
     * @param swipeRefreshLayout
     */
    public static void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     * 下拉刷新 显示刷新动画
     *
     * @param swipeRefreshLayout
     * @param refreshing
     */
    public static void setSwipeRefreshLayoutRefreshing(final SwipeRefreshLayout swipeRefreshLayout, final boolean refreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }
}
