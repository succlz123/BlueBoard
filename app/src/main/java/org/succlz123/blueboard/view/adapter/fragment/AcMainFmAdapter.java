package org.succlz123.blueboard.view.adapter.fragment;

import org.succlz123.blueboard.controller.fragment.tab.AcBangumiFragment;
import org.succlz123.blueboard.controller.fragment.tab.AcEssayFragment;
import org.succlz123.blueboard.controller.fragment.tab.AcNavigationFragment;
import org.succlz123.blueboard.controller.fragment.tab.AcRecommendFragment;
import org.succlz123.blueboard.model.api.acfun.AcString;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by succlz123 on 15/8/10.
 */
public class AcMainFmAdapter extends FragmentPagerAdapter {

    public AcMainFmAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AcRecommendFragment.newInstance();
            case 1:
                return AcNavigationFragment.newInstance();
            case 2:
                return AcBangumiFragment.newInstance();
            case 3:
                return AcEssayFragment.newInstance(AcString.ESSAY);
        }
        return null;
    }

    @Override
    public int getCount() {
        return AcString.MAIN_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcString.MAIN_TITLES[position];
    }
}
