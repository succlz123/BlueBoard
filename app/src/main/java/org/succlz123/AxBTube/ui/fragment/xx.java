package org.succlz123.AxBTube.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.utils.ViewUtils;

/**
 * Created by fashi on 2015/7/8.
 */
public class xx extends Fragment {
	public static final String FRAGMENT_TAG = "main_fragment";
	private View mView;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private Toolbar mToolbar;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	public xx(DrawerLayout drawerLayout) {
		super();
		mDrawerLayout = drawerLayout;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_xx, container, false);

		viewPager = (ViewPager) mView.findViewById(R.id.pager);
		mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);

		Activity activity = getActivity();
		if (activity instanceof AppCompatActivity) {
			ViewUtils.setToolbar((AppCompatActivity) activity, mToolbar);
			ViewUtils.setDrawerToggle(getActivity(),mDrawerLayout,mToolbar);
		}



//		tabLayout = (TabLayout) mView.findViewById(R.id.tabs);

		//设置tab模式,可以滚动
//		tabLayout.setTabMode(TabLayout.MODE_FIXED);
 		TableFragmentAdapter adapter=new TableFragmentAdapter(getFragmentManager());
		viewPager.setAdapter(adapter);//给ViewPager设置适配器
//		tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
//		tabLayout.setTabsFromPagerAdapter(adapter);


		return mView;
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




}
