package org.succlz123.AxBTube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcPartitionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcPartitionActivity extends BaseActivity {

	public static void startActivity(Context activity, int position) {
		Intent intent = new Intent(activity, AcPartitionActivity.class);
		intent.putExtra(AcString.CHANNEL_IDS, position);
		activity.startActivity(intent);
	}

	@Bind(R.id.fragment_partition_tab_layout)
	TabLayout mTabLayout;

	@Bind(R.id.fragment_partition_viewpager)
	ViewPager mViewPager;

	@Bind(R.id.toolbar)
	Toolbar mToolbar;

	private int mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_activity_partition);
		ButterKnife.bind(this);
		mPosition = getIntent().getIntExtra(AcString.CHANNEL_IDS, 0);

		String title = AcString.getTitle(mPosition);
		mToolbar.setTitle(title);
		ViewUtils.setToolbar(AcPartitionActivity.this, mToolbar);

		if (mTabLayout != null && mViewPager != null) {
			AcPartitionFmAdapter adapter = new AcPartitionFmAdapter(getSupportFragmentManager(), mPosition);
 			mViewPager.setAdapter(adapter);
 			mViewPager.setOffscreenPageLimit(3);
			//关联TabLayout和ViewPager

			if (mPosition == 1) {
				mTabLayout.setVisibility(View.GONE);
			}
			//设置tab模式,可以滚动
			mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
			mTabLayout.setupWithViewPager(mViewPager);
		}
	}

	private class AcPartitionFmAdapter extends FragmentStatePagerAdapter {

		private int mPosition;

		public AcPartitionFmAdapter(FragmentManager fm, int position) {
			super(fm);
			this.mPosition = position;
		}

		@Override
		public Fragment getItem(int position) {
			switch (mPosition) {
				case 1:
					return AcPartitionFragment.newInstance("1");
				case 6:
					return getAnimationFragment(position);
				case 9:
					return getFunFragment(position);
				case 12:
					return getMusicFragment(position);
				case 15:
					return getGameFragment(position);
				case 18:
					return getScienceFragment(position);
				case 21:
					return getSportFragment(position);
				case 24:
					return getTvFragment(position);
			}
			return null;
		}

		@Override
		public int getCount() {
			switch (mPosition) {
				case 1:
					return 1;
				case 6:
					return AcString.ANIMATION_TITLES.length;
				case 9:
					return AcString.FUN_TITLES.length;
				case 12:
					return AcString.MUSIC_TITLES.length;
				case 15:
					return AcString.GAME_TITLES.length;
				case 18:
					return AcString.SCIENCE_TITLES.length;
				case 21:
					return AcString.SPORT_TITLES.length;
				case 24:
					return AcString.TV_TITLES.length;
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (mPosition) {
				case 1:
					return null;
				case 6:
					return AcString.ANIMATION_TITLES[position];
				case 9:
					return AcString.FUN_TITLES[position];
				case 12:
					return AcString.MUSIC_TITLES[position];
				case 15:
					return AcString.GAME_TITLES[position];
				case 18:
					return AcString.SCIENCE_TITLES[position];
				case 21:
					return AcString.SPORT_TITLES[position];
				case 24:
					return AcString.TV_TITLES[position];
			}
			return null;
		}

		public Fragment getAnimationFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.ANIMATION_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getFunFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.FUN_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getMusicFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.MUSIC_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getGameFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.GAME_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getScienceFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.SCIENCE_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getSportFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.SPORT_TITLES_ID[position]);

			return fragment;
		}

		public Fragment getTvFragment(int position) {
			Fragment fragment = AcPartitionFragment.newInstance(AcString.TV_TITLES_ID[position]);

			return fragment;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
}
