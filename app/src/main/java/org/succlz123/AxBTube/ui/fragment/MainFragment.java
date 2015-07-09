package org.succlz123.AxBTube.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.github.ksoichiro.android.observablescrollview.TouchInterceptionFrameLayout;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import org.succlz123.AxBTube.R;

/**
 * Created by fashi on 2015/7/6.
 */
public class MainFragment extends BaseFragment implements ObservableScrollViewCallbacks {
	public static final String FRAGMENT_TAG = "main_fragment";

	private TouchInterceptionFrameLayout mInterceptionLayout;
	private ViewPager mViewPager;
	private NavigationAdapter mViewPagerAdapter;
	private int mSlop;
	private boolean mScrolled;
	private ScrollState mLastScrollState;

	private View mView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_main, container, false);

		AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
		mViewPagerAdapter = new NavigationAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) mView.findViewById(R.id.pager);
		mViewPager.setAdapter(mViewPagerAdapter);

		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
		slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
		slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.white));
		slidingTabLayout.setDistributeEvenly(true);
		slidingTabLayout.setBackgroundResource(R.color.pink_base);
		slidingTabLayout.setViewPager(mViewPager);

		ViewConfiguration vc = ViewConfiguration.get(parentActivity);
		mSlop = vc.getScaledTouchSlop();
		mInterceptionLayout = (TouchInterceptionFrameLayout) mView.findViewById(R.id.container);
		mInterceptionLayout.setScrollInterceptionListener(mInterceptionListener);

		return mView;
	}

	/**
	 * ObservableScrollViewCallbacks
	 */
	@Override
	public void onScrollChanged(int i, boolean b, boolean b1) {

	}

	@Override
	public void onDownMotionEvent() {

	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
		if (!mScrolled) {
			// This event can be used only when TouchInterceptionFrameLayout
			// doesn't handle the consecutive events.
			adjustToolbar(scrollState);
		}
	}

	private TouchInterceptionFrameLayout.TouchInterceptionListener mInterceptionListener = new TouchInterceptionFrameLayout.TouchInterceptionListener() {
		@Override
		public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
			if (!mScrolled && mSlop < Math.abs(diffX) && Math.abs(diffY) < Math.abs(diffX)) {
				// Horizontal scroll is maybe handled by ViewPager
				return false;
			}

			Scrollable scrollable = getCurrentScrollable();
			if (scrollable == null) {
				mScrolled = false;
				return false;
			}

			// If interceptionLayout can move, it should intercept.
			// And once it begins to move, horizontal scroll shouldn't work any longer.
			View toolbarView = getActivity().findViewById(R.id.toolbar);
			int toolbarHeight = toolbarView.getHeight();
			int translationY = (int) ViewHelper.getTranslationY(mInterceptionLayout);
			boolean scrollingUp = 0 < diffY;
			boolean scrollingDown = diffY < 0;
			if (scrollingUp) {
				if (translationY < 0) {
					mScrolled = true;
					mLastScrollState = ScrollState.UP;
					return true;
				}
			} else if (scrollingDown) {
				if (-toolbarHeight < translationY) {
					mScrolled = true;
					mLastScrollState = ScrollState.DOWN;
					return true;
				}
			}
			mScrolled = false;
			return false;
		}

		@Override
		public void onDownMotionEvent(MotionEvent ev) {
		}

		@Override
		public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
			View toolbarView = getActivity().findViewById(R.id.toolbar);
			float translationY = ScrollUtils.getFloat(ViewHelper.getTranslationY(mInterceptionLayout) + diffY, -toolbarView.getHeight(), 0);
			ViewHelper.setTranslationY(mInterceptionLayout, translationY);
			ViewHelper.setTranslationY(toolbarView, translationY);
			if (translationY < 0) {
				FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
				lp.height = (int) (-translationY + getScreenHeight());
				mInterceptionLayout.requestLayout();
			}
		}

		@Override
		public void onUpOrCancelMotionEvent(MotionEvent ev) {
			mScrolled = false;
			adjustToolbar(mLastScrollState);
		}
	};

	private Scrollable getCurrentScrollable() {
		Fragment fragment = getCurrentFragment();
		if (fragment == null) {
			return null;
		}
		View view = fragment.getView();
		if (view == null) {
			return null;
		}
		return (Scrollable) view.findViewById(R.id.scroll);
	}

	private void adjustToolbar(ScrollState scrollState) {
		View toolbarView = getActivity().findViewById(R.id.toolbar);
		int toolbarHeight = toolbarView.getHeight();
		final Scrollable scrollable = getCurrentScrollable();
		if (scrollable == null) {
			return;
		}
		int scrollY = scrollable.getCurrentScrollY();
		if (scrollState == ScrollState.DOWN) {
			showToolbar();
		} else if (scrollState == ScrollState.UP) {
			if (toolbarHeight <= scrollY) {
				hideToolbar();
			} else {
				showToolbar();
			}
		} else if (!toolbarIsShown() && !toolbarIsHidden()) {
			// Toolbar is moving but doesn't know which to move:
			// you can change this to hideToolbar()
			showToolbar();
		}
	}

	private Fragment getCurrentFragment() {
		return mViewPagerAdapter.getItemAt(mViewPager.getCurrentItem());
	}

	private boolean toolbarIsShown() {
		return ViewHelper.getTranslationY(mInterceptionLayout) == 0;
	}

	private boolean toolbarIsHidden() {
		View view = getView();
		if (view == null) {
			return false;
		}
		View toolbarView = getActivity().findViewById(R.id.toolbar);
		return ViewHelper.getTranslationY(mInterceptionLayout) == -toolbarView.getHeight();
	}

	private void showToolbar() {
		animateToolbar(0);
	}

	private void hideToolbar() {
		View toolbarView = getActivity().findViewById(R.id.toolbar);
		animateToolbar(-toolbarView.getHeight());
	}

	private void animateToolbar(final float toY) {
		float layoutTranslationY = ViewHelper.getTranslationY(mInterceptionLayout);
		if (layoutTranslationY != toY) {
			ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mInterceptionLayout), toY).setDuration(200);
			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float translationY = (float) animation.getAnimatedValue();
					View toolbarView = getActivity().findViewById(R.id.toolbar);
					ViewHelper.setTranslationY(mInterceptionLayout, translationY);
					ViewHelper.setTranslationY(toolbarView, translationY);
					if (translationY < 0) {
						FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInterceptionLayout.getLayoutParams();
						lp.height = (int) (-translationY + getScreenHeight());
						mInterceptionLayout.requestLayout();
					}
				}
			});
			animator.start();
		}
	}

	/**
	 * This adapter provides two types of fragments as an example.
	 * {@linkplain #createItem(int)} should be modified if you use this example for your app.
	 */
	private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {
		private static final String[] TITLES = new String[]{
				"首页推荐", "分区导航", "新番专题", "放送时间"};

		public NavigationAdapter(FragmentManager fm) {
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
