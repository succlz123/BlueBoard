package org.succlz123.AxBTube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContent;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentCommentFragment;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/8/1.
 */
public class AcContentActivity extends BaseActivity {

    public static void startActivity(Context activity, String contentId) {
        Intent intent = new Intent(activity, AcContentActivity.class);
        intent.putExtra(AcString.CONTENT_ID, contentId);
        activity.startActivity(intent);
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar_content)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.img_content_title)
    SimpleDraweeView mTitleImg;

    @Bind(R.id.tab_layout_content)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager_content_info)
    ViewPager mViewPager;

    private String mContentId;
    private AcContentFmAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_activity_content);
        ButterKnife.bind(this);
        mContentId = getIntent().getStringExtra(AcString.CONTENT_ID);

        ViewUtils.setToolbar(AcContentActivity.this, mToolbar);

        if (mTabLayout != null && mViewPager != null) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            mAdapter = new AcContentFmAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        if (mContentId != null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_BASE).build();

            AcApi.getAcContent acContent = restAdapter.create(AcApi.getAcContent.class);

            acContent.onContentResult(AcApi.getAcContentUrl(mContentId), new Callback<AcContent>() {
                @Override
                public void success(AcContent acContent, Response response) {
                    String url = acContent.getData().getFullContent().getCover();
                    String title = acContent.getData().getFullContent().getTitle();
                    Uri uri = Uri.parse(url);
                    mTitleImg.setImageURI(uri);
                    mCollapsingToolbarLayout.setTitle(title);
                    mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitleText);
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        if (fragment instanceof AcContentInfoFragment) {
                            ((AcContentInfoFragment) fragment).onAcContentResult(acContent);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    private class AcContentFmAdapter extends FragmentStatePagerAdapter {
//        private AcContent acContent;
//
//        private void setContentInfo(AcContent acContent) {
//            this.acContent = acContent;
//            notifyDataSetChanged();
//        }

        public AcContentFmAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AcContentInfoFragment.newInstance();
                case 1:
                    return AcContentCommentFragment.newInstance();
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


}
