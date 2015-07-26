package org.succlz123.AxBTube.support.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinausky on 2015/7/24.
 */
public class AcReBannerAdapter extends PagerAdapter {
	private List<View> mViewItems = new ArrayList<>();
	private int mVpTotalNum;
	private List<ImageView> mDotsIvs = new ArrayList<>();

	public AcReBannerAdapter(AcReBanner acReBanner, ViewPager viewPager, LinearLayout dots) {
		super();
		Context context = MyApplication.getInstance().getApplicationContext();
		Resources resources = context.getResources();
		List<AcReBanner.DataEntity.ListEntity> bannerInfo = acReBanner.getData().getList();
		mVpTotalNum = bannerInfo.size();

		int dotsPx = GlobalUtils.dip2pix(context, 7);
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
					int VpNum = (position % mVpTotalNum);
					for (ImageView iv : mDotsIvs) {
						if (VpNum == (int) iv.getTag()) {
							iv.setSelected(true);
						} else {
							iv.setSelected(false);
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
				ImageView dotsIv = new ImageView(context);
				dotsIv.setLayoutParams(layoutParams);
				dotsIv.setTag(i);
				dotsIv.setImageResource(R.drawable.banner_dots_selector);
				//默认第一个着色
				if (i == 0) {
					dotsIv.setSelected(true);
				}
				mDotsIvs.add(dotsIv);
				//在linearLayout上添加圆点 防止每次回收时重新刷新导致添加过多的圆点
				if (dots.getChildCount() < mVpTotalNum) {
					dots.addView(dotsIv, i);
				}
				//viewPager的itemView
				SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
				GenericDraweeHierarchy vpGdh = new GenericDraweeHierarchyBuilder(resources)
						.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
						.build();
				simpleDraweeView.setHierarchy(vpGdh);
				simpleDraweeView.setImageURI(uri);
				mViewItems.add(simpleDraweeView);
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
