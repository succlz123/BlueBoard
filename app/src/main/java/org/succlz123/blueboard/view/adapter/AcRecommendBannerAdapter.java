package org.succlz123.blueboard.view.adapter;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.bean.acfun.AcReBanner;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.tab.AcRecommendRvAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by succlz123 on 2015/7/24.
 */
public class AcRecommendBannerAdapter extends PagerAdapter {
    private Context mContext = org.succlz123.blueboard.MyApplication.getInstance().getApplicationContext();
    private Resources mResources = mContext.getResources();

    private List<View> mViewItems = new ArrayList<>();
    private List<View> mDotsIvs = new ArrayList<>();
    private int mVpTotalNum;
    private int mVpNum;


    public AcRecommendBannerAdapter(AcReBanner acReBanner, ViewPager viewPager, LinearLayout dots, final AcRecommendRvAdapter.OnClickListener onClickListener) {
        super();
        final List<AcReBanner.DataEntity.ListEntity> bannerInfo = acReBanner.getData().getList();
        mVpTotalNum = bannerInfo.size();

        int dotsPx = GlobalUtils.dip2px(mContext, 7f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotsPx, dotsPx);
        layoutParams.setMargins(dotsPx / 2, dotsPx, dotsPx, dotsPx);

        if (mVpTotalNum != 0) {
            /*viewPager的滚动监听 改变指示圆点状态*/
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mVpNum = (position % mVpTotalNum);
                    for (View view : mDotsIvs) {
                        if (mVpNum == (int) view.getTag()) {
                            view.setSelected(true);
                        } else {
                            view.setSelected(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            for (int i = 0; i < mVpTotalNum; i++) {
                String url = bannerInfo.get(i).getCover();
                Uri uri = Uri.parse(url);
                //新建圆点
                View dotsView = new View(mContext);
                dotsView.setLayoutParams(layoutParams);
                dotsView.setTag(i);
                dotsView.setBackgroundResource(R.drawable.banner_dots_selector);
                //默认第一个着色
                if (i == 0) {
                    dotsView.setSelected(true);
                }
                mDotsIvs.add(dotsView);
                //在linearLayout上添加圆点 防止每次回收时重新刷新导致添加过多的圆点
                if (dots.getChildCount() < mVpTotalNum) {
                    dots.addView(dotsView, i);
                }
                //viewPager的itemView
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
                GenericDraweeHierarchy vpGdh = new GenericDraweeHierarchyBuilder(mResources)
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                        .build();
                simpleDraweeView.setHierarchy(vpGdh);
                simpleDraweeView.setImageURI(uri);

                simpleDraweeView.setClickable(true);
                simpleDraweeView.setFocusable(true);
                mViewItems.add(simpleDraweeView);

                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            onClickListener.onClick(v, null, bannerInfo.get(mVpNum).getSpecialId());
                        }
                    }
                });
            }
        }
    }

    /*viewpager页数 Integer.MAX_VALUE无限循环*/
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /*复用对象 true 复用view false 复用的是Object*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*初始化*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViewItems.size() != 0 && mVpTotalNum != 0) {
            View view = mViewItems.get(position % mVpTotalNum);
            container.addView(view);
            return view;
        }

        return null;
    }

    /*销毁*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewItems.get(position % mVpTotalNum));
    }
}
