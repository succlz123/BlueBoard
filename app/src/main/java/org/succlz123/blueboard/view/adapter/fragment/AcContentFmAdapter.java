package org.succlz123.blueboard.view.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.controller.fragment.other.AcContentInfoFragment;
import org.succlz123.blueboard.controller.fragment.other.AcContentReplyFragment;

/**
 * Created by succlz123 on 15/8/10.
 */
public class AcContentFmAdapter extends FragmentStatePagerAdapter {
    private String mContentId;

    public AcContentFmAdapter(FragmentManager fm, String contentId) {
        super(fm);
        this.mContentId = contentId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AcContentInfoFragment.startFragment(mContentId);
            case 1:
                return AcContentReplyFragment.startFragment(mContentId);
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
