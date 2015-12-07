package org.succlz123.blueboard.view.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.otto.Subscribe;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.config.BusProvider;
import org.succlz123.blueboard.controller.adapter.fragment.AcContentFmAdapter;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.utils.ViewUtils;
import org.succlz123.blueboard.view.activity.video.VideoPlayActivity;
import org.succlz123.blueboard.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        BusProvider.getInstance().register(this);

        mContentId = getIntent().getStringExtra(AcString.CONTENT_ID);

        ViewUtils.setToolbar(AcContentActivity.this, mToolbar, true);
        mCollapsingToolbarLayout.setTitle("AC" + mContentId);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitleText);

        mAdapter = new AcContentFmAdapter(getSupportFragmentManager(), mContentId);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Subscribe
    public void onFragmentBack(final AcContentInfo.DataEntity.FullContentEntity fullContentEntity) {
        //加载标题图片并点击播放默认第一个视频
        mTitleImg.setImageURI(Uri.parse(fullContentEntity.getCover()));
        mTitleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity
                        = fullContentEntity.getVideos().get(0);

                VideoPlayActivity.startActivity(AcContentActivity.this,
                        videosEntity.getVideoId(),
                        videosEntity.getSourceId(),
                        videosEntity.getType());
            }
        });
//        mCollapsingToolbarLayout.setTitle("AC" + fullContentEntity.getContentId());
    }

    private void setOnDlCheckBoxShow(boolean isDlCheckBoxShow, boolean isDlCheckBoxSelectAll) {
//        AcContentInfoFragment fragment = ((AcContentInfoFragment) getAcContentFragment(FRAGMENT_TPE_CONTENT_INFO));
        BusProvider.getInstance().post(new DlCheckBox(isDlCheckBoxShow, isDlCheckBoxSelectAll));
//        fragment.onIsDlCheckBoxShow(isDlCheckBoxShow, isDlCheckBoxSelectAll);
    }

    private boolean getIsDlCheckBoxShow() {
//        AcContentInfoFragment fragment = ((AcContentInfoFragment) getAcContentFragment(FRAGMENT_TPE_CONTENT_INFO));

//        return fragment.getIsDlCheckBoxShow();
        return false;
    }

    public static class DlCheckBox {
        private boolean isDlCheckBoxShow;
        private boolean isDlCheckBoxSelectAll;

        private DlCheckBox(boolean isDlCheckBoxShow, boolean isDlCheckBoxSelectAll) {
            this.isDlCheckBoxShow = isDlCheckBoxShow;
            this.isDlCheckBoxSelectAll = isDlCheckBoxSelectAll;
        }

        public boolean isDlCheckBoxShow() {
            return isDlCheckBoxShow;
        }

        public boolean isDlCheckBoxSelectAll() {
            return isDlCheckBoxSelectAll;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem downLoadSelectItem = menu.add(Menu.NONE, 1, 100, "选择下载");
        MenuItem downLoadAllItem = menu.add(Menu.NONE, 2, 100, "下载全部");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case 1:
                setOnDlCheckBoxShow(true, false);
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 300, 600, 0);
                MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 300, 100, 0);
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 300, 100, 0);
                mCollapsingToolbarLayout.onTouchEvent(event);
                break;
            case 2:
                setOnDlCheckBoxShow(true, true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getIsDlCheckBoxShow()) {
            setOnDlCheckBoxShow(false, false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
