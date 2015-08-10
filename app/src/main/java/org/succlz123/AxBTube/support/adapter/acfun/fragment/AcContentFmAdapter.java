package org.succlz123.AxBTube.support.adapter.acfun.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentInfoFragment;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentReplyFragment;

/**
 * Created by succlz123 on 15/8/10.
 */
public class AcContentFmAdapter extends FragmentStatePagerAdapter {

    public AcContentFmAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AcContentInfoFragment.newInstance();
            case 1:
                return AcContentReplyFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AcString.CONTENT_VIEW_PAGER_TITLE[position];
    }
}
