package org.succlz123.AxBTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcEssay;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcEssayRvAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by fashi on 2015/5/2.
 */
public class AcEssayFragment extends BaseFragment {

	public static AcEssayFragment newInstance(String channelType) {
		AcEssayFragment fragment = new AcEssayFragment();
		Bundle bundle = new Bundle();
		bundle.putString(AcString.CHANNEL_IDS, channelType);
		fragment.setArguments(bundle);

		return fragment;
	}

	private boolean mIsPrepared;
	private AcEssayRvAdapter mAdapter;
	private String mPartitionType;
	private int xx;

	@Bind(R.id.ac_fragment_essay_recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.swipe_fresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	LinearLayoutManager manager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_main_essay, container, false);
		ButterKnife.bind(this, view);
		mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

		manager = new LinearLayoutManager(getActivity());
		xx = manager.findLastVisibleItemPosition();

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new AcEssayRvAdapter();
//		mAdapter.setOnClickListener(new AcBangumiRvAdapter.OnClickListener() {
//			@Override
//			public void onClick(View view, int position, String contentId) {
//				GlobalUtils.showToastShort(getActivity(), "TODO");
//			}
//		});
		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_DRAGGING
						&& manager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {
					mSwipeRefreshLayout.setRefreshing(true);
					getHttpResult();
				}

			}
		});

		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getHttpResult();
			}
		});

		mIsPrepared = true;
		lazyLoad();

		return view;
	}

	@Override
	protected void lazyLoad() {
		if (!mIsPrepared || !isVisible) {
			return;
		} else {
			if (mAdapter.getmAcEssay() == null) {
				mSwipeRefreshLayout.setRefreshing(true);
				getHttpResult();
			}
		}
	}

	private void getHttpResult() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();
		AcApi.getAcPartition acPartition = restAdapter.create(AcApi.getAcPartition.class);

		acPartition.onEssayResult(AcApi.getAcPartitionUrl(mPartitionType, AcString.POPULARITY, AcString.ONE_WEEK), new Callback<AcEssay>() {
			@Override
			public void success(AcEssay acEssay, Response response) {
				mAdapter.setEssayInfo(acEssay);
				if (mSwipeRefreshLayout != null) {
					mSwipeRefreshLayout.setRefreshing(false);
				}
			}

			@Override
			public void failure(RetrofitError error) {

			}
		});
	}
}

