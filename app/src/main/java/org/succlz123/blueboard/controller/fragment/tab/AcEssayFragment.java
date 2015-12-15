package org.succlz123.blueboard.controller.fragment.tab;

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

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcEssay;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.AcEssayRvAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by succlz123 on 2015/5/2.
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
        mAdapter.setOnClickListener(new AcEssayRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                GlobalUtils.showToastShort(getActivity(), "TODO");
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {

                    getHttpResult("" + mPagerNoNum);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcString.PAGE_NO_NUM_1);
                mSwipeRefreshLayout.setEnabled(false);
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
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult(AcString.PAGE_NO_NUM_1);
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
        }
    }

    private void getHttpResult(final String pagerNoNum) {
        //文章
        Call<AcEssay> call = AcApi.getAcPartition()
                .onResult(AcApi.buildAcPartitionUrl(
                        mPartitionType,
                        AcString.POPULARITY,
                        AcString.ONE_WEEK,
                        AcString.PAGE_SIZE_NUM_20, pagerNoNum));
        call.enqueue(new Callback<AcEssay>() {
            @Override
            public void onResponse(Response<AcEssay> response, Retrofit retrofit) {
                AcEssay acEssay = response.body();
                if (acEssay != null &&
                        getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcEssayFragment.this.getUserVisibleHint()) {
                    if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                        mAdapter.addData(acEssay);
                    } else {
                        mAdapter.setEssayInfo(acEssay);
                    }
                    mPagerNoNum++;
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcEssayFragment.this.getUserVisibleHint()) {
                    GlobalUtils.showToastShort(MyApplication.getInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }
        });
    }
}

