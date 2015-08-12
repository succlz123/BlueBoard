package org.succlz123.AxBTube.ui.activity.acfun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

    public static void startActivity(Context activity, String partitionType) {
        Intent intent = new Intent(activity, AcPartitionActivity.class);
        intent.putExtra(AcString.CHANNEL_IDS, partitionType);
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
        String partitionType = getIntent().getStringExtra(AcString.CHANNEL_IDS);

        ViewUtils.setToolbar(AcPartitionActivity.this, mToolbar, true, partitionType);

        AcPartitionFmAdapter adapter = new AcPartitionFmAdapter(getSupportFragmentManager(), partitionType);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        if (TextUtils.equals(partitionType, AcString.TITLE_HOT)) {
            mTabLayout.setVisibility(View.GONE);
        }
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
