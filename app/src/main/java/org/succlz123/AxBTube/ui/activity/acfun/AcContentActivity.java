package org.succlz123.AxBTube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentInfo;
import org.succlz123.AxBTube.support.adapter.acfun.fragment.AcContentFmAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;
import org.succlz123.AxBTube.ui.activity.VideoPlayActivity;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentInfoFragment;
import org.succlz123.AxBTube.ui.fragment.acfun.other.AcContentReplyFragment;

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

        ViewUtils.setToolbar(AcContentActivity.this, mToolbar, true);

        mAdapter = new AcContentFmAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

        if (mContentId != null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();
            AcApi.getAcContentInfo acContent = restAdapter.create(AcApi.getAcContentInfo.class);

            acContent.onContentInfoResult(AcApi.getAcContentInfoUrl(mContentId), new Callback<AcContentInfo>() {
                @Override
                public void success(final AcContentInfo acContentInfo, Response response) {
                    //如果请求的视频被删除
                    if (acContentInfo.getStatus() == 404) {
                        GlobalUtils.showToastShort(AcContentActivity.this, acContentInfo.getMsg());
                    } else {
                        String url = acContentInfo.getData().getFullContent().getCover();
                        int title = acContentInfo.getData().getFullContent().getContentId();
                        //加载标题图片并点击播放默认第一个视频
                        mTitleImg.setImageURI(Uri.parse(url));
                        mTitleImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity
                                        = acContentInfo.getData().getFullContent().getVideos().get(0);

                                VideoPlayActivity.startActivity(AcContentActivity.this,
                                        String.valueOf(videosEntity.getVideoId()),
                                        String.valueOf(videosEntity.getDanmakuId()),
                                        videosEntity.getSourceId(),
                                        videosEntity.getType());
                            }
                        });
                        mCollapsingToolbarLayout.setTitle("AC" + title);
                        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitleText);

                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            if (fragment instanceof AcContentInfoFragment) {
                                ((AcContentInfoFragment) fragment)
                                        .onAcContentResult(acContentInfo);
                            } else if (fragment instanceof AcContentReplyFragment) {
                                ((AcContentReplyFragment) fragment)
                                        .onAcContentResult(String.valueOf(acContentInfo.getData().getFullContent().getContentId()));
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
}
