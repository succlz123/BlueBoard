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

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;
import org.succlz123.AxBTube.ui.fragment.acfun.partition.AcPartitionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcPartitionActivity extends BaseActivity {

	public static void startActivity(Context activity, int position) {
		Intent intent = new Intent(activity, AcPartitionActivity.class);
		intent.putExtra(AcString.AC_PARTITION_URL, position);
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
		mPosition = getIntent().getIntExtra(AcString.AC_PARTITION_URL, 0);

		String title = AcString.getTitle(mPosition);
		mToolbar.setTitle(title);
		ViewUtils.setToolbar(AcPartitionActivity.this, mToolbar);

		if (mTabLayout != null && mViewPager != null) {
			//设置tab模式,可以滚动
			mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
			AcPartitionFmAdapter adapter = new AcPartitionFmAdapter(getSupportFragmentManager(), mPosition);
			//给ViewPager设置适配器
			mViewPager.setAdapter(adapter);
//			mViewPager.setOffscreenPageLimit(4);
			//关联TabLayout和ViewPager
			mTabLayout.setupWithViewPager(mViewPager);
		}
	}

	private static class AcPartitionFmAdapter extends FragmentStatePagerAdapter {
		private static final String[] ANIMATION_TITLES = new String[]{
				"新番连载", "旧番补档", "动画短片", "MAD.AMV", "MAD.3D", "国产动画"};

		private static final String[] ENTERTAINMENT_TITLES = new String[]{
				"生活娱乐", "鬼畜调教", "萌宠", "美食", "原创"};

		private int mPosition;

		public AcPartitionFmAdapter(FragmentManager fm, int position) {
			super(fm);
			this.mPosition = position;
		}

		@Override
		public Fragment getItem(int position) {
			switch (mPosition) {
				case 6:
					return getAnimationFragment(position);
				case 9:
					return getEntertainmentFragment(position);
			}
			return null;
		}

		@Override
		public int getCount() {
			switch (mPosition) {
				case 6:
					return ANIMATION_TITLES.length;
				case 9:
					return ENTERTAINMENT_TITLES.length;
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (mPosition) {
				case 6:
					return ANIMATION_TITLES[position];
				case 9:
					return ENTERTAINMENT_TITLES[position];
			}
			return null;
		}

		public Fragment getAnimationFragment(int position) {
			switch (position) {
				case 0:
					return AcPartitionFragment.newInstance("" + 67);
				case 1:
					return AcPartitionFragment.newInstance("" + 109);
				case 2:
					return AcPartitionFragment.newInstance("" + 106);
				case 3:
					return AcPartitionFragment.newInstance("" + 107);
				case 4:
					return AcPartitionFragment.newInstance("" + 108);
				case 5:
					return AcPartitionFragment.newInstance("" + 120);
			}
			return null;
		}

		public Fragment getEntertainmentFragment(int position) {
			switch (position) {
				case 0:
					return AcPartitionFragment.newInstance("" + 86);
				case 1:
					return AcPartitionFragment.newInstance("" + 87);
				case 2:
					return AcPartitionFragment.newInstance("" + 88);
				case 3:
					return AcPartitionFragment.newInstance("" + 89);
				case 4:
					return AcPartitionFragment.newInstance("" + 121);
			}
			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
}
