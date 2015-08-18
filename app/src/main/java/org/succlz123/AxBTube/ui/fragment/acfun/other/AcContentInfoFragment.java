package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentInfo;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcContentInfoRvAdapter;
import org.succlz123.AxBTube.support.config.RetrofitConfig;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.LogUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.VideoPlayActivity;
import org.succlz123.AxBTube.ui.activity.acfun.AcContentActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentInfoFragment extends BaseFragment {

	public static AcContentInfoFragment newInstance() {
		AcContentInfoFragment fragment = new AcContentInfoFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
		return fragment;
	}

	@Bind(R.id.ac_fragment_content_reply_recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.swipe_fresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	private boolean mIsPrepared;
	private AcContentInfoRvAdapter mAdapter;
	private String mContentId;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);
		ButterKnife.bind(this, view);

		LinearLayoutManager manager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new AcContentInfoRvAdapter();
		mAdapter.setOnClickListener(new AcContentInfoRvAdapter.OnClickListener() {
			@Override
			public void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType) {
				if (position == 0) {
					GlobalUtils.showToastShort(getActivity(), "哇哈哈 " + userId);
				} else {
					VideoPlayActivity.startActivity(getActivity(),
							videoId,
							danmakuId,
							sourceId,
							sourceType);
				}
			}
		});
		mRecyclerView.setAdapter(mAdapter);

		ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

		mIsPrepared = true;
		lazyLoad();

		return view;
	}

	public void onAcContentResult(String contentId) {
		this.mContentId = contentId;
	}

	@Override
	protected void lazyLoad() {
		if (!mIsPrepared || !isVisible || mContentId == null) {
			return;
		} else {
			if (mAdapter.getmAcContentInfo() == null) {
				getHttpResult();
			}
		}
	}

	private void getHttpResult() {
		ViewUtils.setSwipeRefreshLayoutRefreshing(mSwipeRefreshLayout, true);
		AlarmManager am = (AlarmManager) MyApplication.getsInstance().getApplicationContext().getSystemService(Activity.ALARM_SERVICE);

//		final Handler handler = new Handler();
//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// handler自带方法实现定时器
//				try {
//					if (mAdapter.getmAcContentInfo() != null) {
//						mSwipeRefreshLayout.setRefreshing(false);
//					}
//					LogUtils.d("fadf");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};
//
//		handler.postDelayed(runnable, 100);

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mAdapter.getmAcContentInfo() == null) {
					try {
						Thread.sleep(100);
						if(mAdapter.getmAcContentInfo() != null){
							mSwipeRefreshLayout.setRefreshing(false);
							LogUtils.e("123");
 						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				LogUtils.e("321");
			}
		}).start();
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                if (mAdapter.getmAcContentInfo() == null) {
//                    mSwipeRefreshLayout.setRefreshing(true);
//                }
//            }
//        });
//
		RetrofitConfig.getAcContentInfo().onContentInfoResult(AcApi.getAcContentInfoUrl(mContentId), new Callback<AcContentInfo>() {
			@Override
			public void success(final AcContentInfo acContentInfo, Response response) {
				if (getActivity() != null && !getActivity().isDestroyed()) {
					//如果请求的视频被删除 未被审核 或者其他
					if (!acContentInfo.isSuccess()
							|| acContentInfo.getStatus() == 404
							|| acContentInfo.getStatus() == 403) {
						GlobalUtils.showToastShort(getActivity(), acContentInfo.getMsg());
					} else if (acContentInfo.getData() != null) {

						mAdapter.setContentInfo(acContentInfo);

						Context activity = getActivity();
						if (activity instanceof AcContentActivity) {
							((AcContentActivity) activity).onFragmentBack(acContentInfo.getData().getFullContent());
						}

						if (mSwipeRefreshLayout != null) {
							mSwipeRefreshLayout.setRefreshing(false);
							mSwipeRefreshLayout.setEnabled(false);
						}
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (getActivity() != null && !getActivity().isDestroyed()) {
					GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络连接异常");
					if (mSwipeRefreshLayout != null) {
						mSwipeRefreshLayout.setRefreshing(false);
					}
				}
			}
		});
	}
}
