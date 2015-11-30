package org.succlz123.blueboard.controller.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.view.fragment.main.AcBangumiFragment;
import org.succlz123.blueboard.view.fragment.main.AcEssayFragment;
import org.succlz123.blueboard.view.fragment.main.AcNavigationFragment;
import org.succlz123.blueboard.view.fragment.main.AcRecommendFragment;

/**
 * Created by succlz123 on 15/8/10.
 */
public class AcMainFmAdapter extends FragmentStatePagerAdapter {

    public AcMainFmAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AcRecommendFragment();
            case 1:
                return new AcNavigationFragment();
            case 2:
                return new AcBangumiFragment();
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
