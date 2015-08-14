package org.succlz123.AxBTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcEssay;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcEssayRvAdapter;
import org.succlz123.AxBTube.support.config.RetrofitConfig;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
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
    private LinearLayoutManager mManager;
    private int mPagerNoNum = 1;

    @Bind(R.id.ac_fragment_essay_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_essay, container, false);
        ButterKnife.bind(this, view);
        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mManager);
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
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mPagerNoNum++;
                    getHttpResult("" + mPagerNoNum);
                }
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcString.PAGE_NO_NUM_1);
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
                getHttpResult(AcString.PAGE_NO_NUM_1);
            }
        }
    }

    private void getHttpResult(final String pagerNoNum) {
        RetrofitConfig.getAcPartition().onEssayResult(AcApi.getAcPartitionUrl(mPartitionType,
                AcString.POPULARITY,
                AcString.ONE_WEEK,
                AcString.PAGE_SIZE_NUM_20,
                pagerNoNum), new Callback<AcEssay>() {
            @Override
            public void success(AcEssay acEssay, Response response) {
                if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                    mAdapter.addData(acEssay);
                } else {
                    mAdapter.setEssayInfo(acEssay);
                }
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

