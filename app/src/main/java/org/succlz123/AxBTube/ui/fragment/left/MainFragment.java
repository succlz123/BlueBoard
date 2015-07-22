package org.succlz123.AxBTube.ui.fragment.left;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.ui.activity.MainActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;
import org.succlz123.AxBTube.ui.fragment.main.acfun.AcRecommendFragment;
import org.succlz123.AxBTube.ui.fragment.main.bilili.BiliBangumiFragment;
import org.succlz123.AxBTube.ui.fragment.main.bilili.BiliNavigationFragment;
import org.succlz123.AxBTube.ui.fragment.main.bilili.BiliRecommendFragment;
import org.succlz123.AxBTube.ui.fragment.main.bilili.BiliTimeFragment;
import org.succlz123.AxBTube.support.utils.ViewUtils;

/**
 * Created by fashi on 2015/7/8.
 */
public class MainFragment extends BaseFragment {
	public static final String FRAGMENT_TAG = "main_fragment";
	private View mView;
	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private Toolbar mToolbar;
	private DrawerLayout mDrawerLayout;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_main, container, false);
		initViews();

		Activity activity = getActivity();
		if (activity instanceof AppCompatActivity) {
			ViewUtils.setToolbar((AppCompatActivity) activity, mToolbar);
			mDrawerLayout = ((MainActivity) activity).mDrawerLayout;
			ViewUtils.setDrawerToggle(activity, mDrawerLayout, mToolbar);
		}

		if (mTabLayout != null && mViewPager != null) {
			//设置tab模式,可以滚动
			mTabLayout.setTabMode(TabLayout.MODE_FIXED);
			AcMainFragmentAdapter adapter = new AcMainFragmentAdapter(getFragmentManager());
			//给ViewPager设置适配器
			mViewPager.setAdapter(adapter);
//			mViewPager.setOffscreenPageLimit(4);
			//关联TabLayout和ViewPager
			mTabLayout.setupWithViewPager(mViewPager);
		}

		return mView;
	}

	private void initViews() {
		mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
		mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
		mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
	}

	private static class AcMainFragmentAdapter extends FragmentStatePagerAdapter {
		private static final String[] TITLES = new String[]{
				"首页推荐", "分区导航", "新番专题", "文章专区"};

		public AcMainFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return new AcRecommendFragment();
				case 1:
					return new BiliNavigationFragment();
				case 2:
					return new BiliBangumiFragment();
				case 3:
					return new BiliTimeFragment();
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

	private static class BiliMainFragmentAdapter extends FragmentStatePagerAdapter {
		private static final String[] TITLES = new String[]{
				"首页推荐", "分区导航", "新番专题", "放送时间"};

		public BiliMainFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return new BiliRecommendFragment();
				case 1:
					return new BiliNavigationFragment();
				case 2:
					return new BiliBangumiFragment();
				case 3:
					return new BiliTimeFragment();
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
