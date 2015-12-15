package org.succlz123.blueboard.controller.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.view.adapter.recyclerview.AcHotRvAdapter;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcReHot;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.controller.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by succlz123 on 15/8/18.
 */
public class AcHotFragment extends BaseFragment {

    public static AcHotFragment newInstance(String channelType) {
        AcHotFragment fragment = new AcHotFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(AcString.CHANNEL_IDS, channelType);
//        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean mIsPrepared;
    private AcHotRvAdapter mAdapter;
    private GridLayoutManager mManager;
    private int mPagerNoNum = 1;

    @Bind(R.id.ac_fragment_partition_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);
        ButterKnife.bind(this, view);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcHotRvAdapter.MyDecoration());
        mAdapter = new AcHotRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AcHotRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                AcContentActivity.startActivity(getActivity(), contentId);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            if (mAdapter.getmAcReHot() == null) {
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
        //热门焦点
        Call<AcReHot> call = AcApi.getAcRecommend().onAcReHotResult(AcApi.buildAcReHotUrl(pagerNoNum));
        call.enqueue(new Callback<AcReHot>() {
            @Override
            public void onResponse(Response<AcReHot> response, Retrofit retrofit) {
                AcReHot acReHot = response.body();
                if (acReHot != null
                        && getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcHotFragment.this.getUserVisibleHint()) {
                    if (acReHot.getData() != null) {
                        if (acReHot.getData().getPage().getList().size() != 0) {
                            if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                mAdapter.addAcReHotDate(acReHot);
                            } else {
                                mAdapter.setmAcReHot(acReHot);
                            }
                            mPagerNoNum++;
                        } else {
                            GlobalUtils.showToastShort(getActivity(), "没有更多了 (´･ω･｀)");
                        }
                    }
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
                        && AcHotFragment.this.getUserVisibleHint()) {
                    GlobalUtils.showToastShort(MyApplication.getInstance().getApplicationContext(), "刷新太快或者网络连接异常");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }
        });
    }
}

