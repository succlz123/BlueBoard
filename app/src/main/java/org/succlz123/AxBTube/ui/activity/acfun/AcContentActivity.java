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
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContent;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;
import org.succlz123.AxBTube.ui.activity.VideoPlayActivity;
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
    private AcContentFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_activity_content);
        ButterKnife.bind(this);
        mContentId = getIntent().getStringExtra(AcString.CONTENT_ID);

        ViewUtils.setToolbar(AcContentActivity.this, mToolbar);

        if (mTabLayout != null && mViewPager != null) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            mAdapter = new AcContentFragmentAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        if (mContentId != null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_BASE).build();

            AcApi.getAcContent acContent = restAdapter.create(AcApi.getAcContent.class);

            acContent.onContentResult(AcApi.getAcContentUrl(mContentId), new Callback<AcContent>() {
                @Override
                public void success(final AcContent acContent, Response response) {
                    String url = acContent.getData().getFullContent().getCover();
                    int title = acContent.getData().getFullContent().getContentId();
                    Uri uri = Uri.parse(url);
                    mTitleImg.setImageURI(uri);

                    mTitleImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int videoId = acContent.getData().getFullContent().getVideos().get(0).getVideoId();
                            int danmakuId = acContent.getData().getFullContent().getVideos().get(0).getDanmakuId();
                            String sourceId = acContent.getData().getFullContent().getVideos().get(0).getSourceId();
                            String sourceType = acContent.getData().getFullContent().getVideos().get(0).getType();

                            VideoPlayActivity.startActivity(AcContentActivity.this,
                                    String.valueOf(videoId),
                                    String.valueOf(danmakuId),
                                    "f3f977bbf7", "letv");
                        }
                    });
                    mCollapsingToolbarLayout.setTitle("AC" + title);
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

    private class AcContentFragmentAdapter extends FragmentStatePagerAdapter {

        public AcContentFragmentAdapter(FragmentManager fm) {
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
