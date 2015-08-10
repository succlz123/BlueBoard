package org.succlz123.AxBTube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.adapter.acfun.fragment.AcPartitionFmAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcPartitionActivity extends BaseActivity {

    public static void startActivity(Context activity, int position) {
        Intent intent = new Intent(activity, AcPartitionActivity.class);
        intent.putExtra(AcString.CHANNEL_IDS, position);
        activity.startActivity(intent);
    }

    @Bind(R.id.fragment_partition_tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.fragment_partition_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_activity_partition);
        ButterKnife.bind(this);
        //根据position来判断什么分区
        int position = getIntent().getIntExtra(AcString.CHANNEL_IDS, 0);

        ViewUtils.setToolbar(AcPartitionActivity.this, mToolbar, true, AcString.getTitle(position));

        if (mTabLayout != null && mViewPager != null) {
            AcPartitionFmAdapter adapter = new AcPartitionFmAdapter(getSupportFragmentManager(), position);
            mViewPager.setAdapter(adapter);
            mViewPager.setOffscreenPageLimit(3);
            if (position == 1) {
                mTabLayout.setVisibility(View.GONE);
            }
            //设置tab模式,可以滚动
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
