package org.succlz123.bluetube.support.adapter.acfun.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.ui.fragment.acfun.main.AcEssayFragment;
import org.succlz123.bluetube.ui.fragment.acfun.other.AcHotFragment;
import org.succlz123.bluetube.ui.fragment.acfun.other.AcPartitionFragment;
import org.succlz123.bluetube.ui.fragment.acfun.other.AcRankingFragment;

/**
 * Created by succlz123 on 15/8/10.
 */
public class AcPartitionFmAdapter extends FragmentStatePagerAdapter {

    private String mPosition;

    public AcPartitionFmAdapter(FragmentManager fm, String partitionType) {
        super(fm);
        this.mPosition = partitionType;
    }

    @Override
    public Fragment getItem(int position) {
        switch (mPosition) {
            case AcString.TITLE_HOT:
                return getHotFragment(position);
            case AcString.TITLE_RANKING:
                return getRankingFragment(position);
            case AcString.TITLE_ANIMATION:
                return getAnimationFragment(position);
            case AcString.TITLE_FUN:
                return getFunFragment(position);
            case AcString.TITLE_MUSIC:
                return getMusicFragment(position);
            case AcString.TITLE_GAME:
                return getGameFragment(position);
            case AcString.TITLE_SCIENCE:
                return getScienceFragment(position);
            case AcString.TITLE_SPORT:
                return getSportFragment(position);
            case AcString.TITLE_TV:
                return getTvFragment(position);
            case AcString.TITLE_ESSAY:
                return getEssayFragment(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        switch (mPosition) {
            case AcString.TITLE_HOT:
                return 1;
            case AcString.TITLE_RANKING:
                return AcString.RANKING_TITLES.length;
            case AcString.TITLE_ANIMATION:
                return AcString.ANIMATION_TITLES.length;
            case AcString.TITLE_FUN:
                return AcString.FUN_TITLES.length;
            case AcString.TITLE_MUSIC:
                return AcString.MUSIC_TITLES.length;
            case AcString.TITLE_GAME:
                return AcString.GAME_TITLES.length;
            case AcString.TITLE_SCIENCE:
                return AcString.SCIENCE_TITLES.length;
            case AcString.TITLE_SPORT:
                return AcString.SPORT_TITLES.length;
            case AcString.TITLE_TV:
                return AcString.TV_TITLES.length;
            case AcString.TITLE_ESSAY:
                return AcString.ESSAY_TITLES.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (mPosition) {
            case AcString.TITLE_HOT:
                return null;
            case AcString.TITLE_RANKING:
                return AcString.RANKING_TITLES[position];
            case AcString.TITLE_ANIMATION:
                return AcString.ANIMATION_TITLES[position];
            case AcString.TITLE_FUN:
                return AcString.FUN_TITLES[position];
            case AcString.TITLE_MUSIC:
                return AcString.MUSIC_TITLES[position];
            case AcString.TITLE_GAME:
                return AcString.GAME_TITLES[position];
            case AcString.TITLE_SCIENCE:
                return AcString.SCIENCE_TITLES[position];
            case AcString.TITLE_SPORT:
                return AcString.SPORT_TITLES[position];
            case AcString.TITLE_TV:
                return AcString.TV_TITLES[position];
            case AcString.TITLE_ESSAY:
                return AcString.ESSAY_TITLES[position];
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

    public Fragment getEssayFragment(int position) {
        Fragment fragment = AcEssayFragment.newInstance(AcString.ESSAY_TITLES_ID[position]);

        return fragment;
    }

    public Fragment getHotFragment(int position) {
        Fragment fragment = AcHotFragment.newInstance(AcString.TITLE_HOT);

        return fragment;
    }

    public Fragment getRankingFragment(int position) {
        Fragment fragment = AcRankingFragment.newInstance(AcString.RANKING_TITLES_ID[position]);

        return fragment;
    }
}
