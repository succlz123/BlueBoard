package org.succlz123.AxBTube.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.ui.fragment.BangumiFragment;
import org.succlz123.AxBTube.ui.fragment.NavigationFragment;
import org.succlz123.AxBTube.ui.fragment.RecommendFragment;
import org.succlz123.AxBTube.ui.fragment.TimeFragment;
import org.succlz123.AxBTube.utils.GlobalUtils;


public class MainActivity extends BaseActivity {
	private final static int DRAWER_LAYOUT_HEADER_VIEW_HEIGHT_DPI = 180;
	public DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private Toolbar mToolbar;
	private ListView mLvLeftMenu;
	private TabLayout tabLayout;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yy);
		initViews();
//		ViewUtils.setToolbar(this, mToolbar);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		final ActionBar ab = getSupportActionBar();
//		ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);

		setDrawerLayout();

//		tabLayout = (TabLayout) findViewById(R.id.tabs);
//		viewPager = (ViewPager) findViewById(R.id.viewpager);

		//设置tab模式,可以滚动
//		tabLayout.setTabMode(TabLayout.MODE_FIXED);
//		TableFragmentAdapter adapter = new TableFragmentAdapter(getSupportFragmentManager());
//		viewPager.setAdapter(adapter);//给ViewPager设置适配器
//		tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
//		tabLayout.setTabsFromPagerAdapter(adapter);

//		FragmentManager fm = getSupportFragmentManager();
//		if (fm.findFragmentByTag(xx.FRAGMENT_TAG) == null) {
//			FragmentTransaction ft = fm.beginTransaction();
//			ft.add(R.id.fragment_content, new xx(mDrawerLayout), xx.FRAGMENT_TAG);
//			ft.commitAllowingStateLoss();
//			fm.executePendingTransactions();
//		}
	}

	private void initViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mLvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	}

	private void setDrawerLayout() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View headerView = inflater.inflate(R.layout.drawer_header, mDrawerLayout, false);
		headerView.setLayoutParams(new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				GlobalUtils.dip2px(this, DRAWER_LAYOUT_HEADER_VIEW_HEIGHT_DPI)));
		mLvLeftMenu.addHeaderView(headerView);
		mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
	}


	private static class TableFragmentAdapter extends CacheFragmentStatePagerAdapter {
		private static final String[] TITLES = new String[]{
				"首页推荐", "分区导航", "新番专题", "放送时间"};

		public TableFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		protected Fragment createItem(int position) {
			switch (position) {
				case 0:
					return new RecommendFragment();
				case 1:
					return new NavigationFragment();
				case 2:
					return new BangumiFragment();
				case 3:
					return new TimeFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}
	}


//	private void setDrawerToggle() {
//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				super.onDrawerOpened(drawerView);
//			}
//
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				super.onDrawerClosed(drawerView);
//			}
//		};
//		mDrawerToggle.syncState();
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//		mDrawerLayout.setScrimColor(getResources().getColor(R.color.shadow_white));
//	}

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
