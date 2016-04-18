package org.succlz123.blueboard.controller.activity.main;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.DownLoadActivity;
import org.succlz123.blueboard.base.BaseActivity;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.fragment.AcMainFmAdapter;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = f(R.id.toolbar);
        mViewPager = f(R.id.fragment_main_viewpager);
        mTabLayout = f(R.id.fragment_main_tab_layout);
        mNavigationView = f(R.id.nav_view);
        mDrawerLayout = f(R.id.drawer_layout);

        ViewUtils.setToolbar(this, mToolbar, true);
        ViewUtils.setDrawer(this, mDrawerLayout, mToolbar);
        setUpDrawerContent(mNavigationView);

        AcMainFmAdapter adapter = new AcMainFmAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                menuItem.setChecked(true);
                                break;
                            case R.id.nav_download:
                                DownLoadActivity.newInstance(MainActivity.this);
                                break;

                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
