package org.succlz123.AxBTube.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.adapter.acfun.fragment.AcMainFmAdapter;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/8.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.fragment_main_tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.fragment_main_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        //显示toolbar并绑定drawerToggle
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ViewUtils.setToolbar((AppCompatActivity) activity, mToolbar, true);
            DrawerLayout drawerLayout = ((MainActivity) activity).mDrawerLayout;
            ViewUtils.setDrawerToggle(activity, drawerLayout, mToolbar);
        }

        if (mTabLayout != null && mViewPager != null) {
            //设置tab模式,可以滚动
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            //mTabLayout.setTabTextColors();
            AcMainFmAdapter adapter = new AcMainFmAdapter(getFragmentManager());
            //给ViewPager设置适配器
            mViewPager.setAdapter(adapter);
            mViewPager.setOffscreenPageLimit(2);
            //关联TabLayout和ViewPager
            mTabLayout.setupWithViewPager(mViewPager);
        }

        return view;
    }

    @Override
    protected void lazyLoad() {

    }

}
