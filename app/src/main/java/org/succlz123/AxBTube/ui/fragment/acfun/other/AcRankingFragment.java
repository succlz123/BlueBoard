package org.succlz123.AxBTube.ui.fragment.acfun.other;

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

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcRankingRvAdapter;
import org.succlz123.AxBTube.support.config.RetrofitConfig;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.acfun.AcContentActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcRankingFragment extends BaseFragment {

    public static AcRankingFragment newInstance(String channelType) {
        AcRankingFragment fragment = new AcRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcRankingRvAdapter mAdapter;
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
        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcRankingRvAdapter.MyDecoration());
        mAdapter = new AcRankingRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AcRankingRvAdapter.OnClickListener() {
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
                    mSwipeRefreshLayout.setRefreshing(true);
                    getHttpResult("" + mPagerNoNum);
                }
            }
        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
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
            if (mAdapter.getmAcReOther() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult(AcString.PAGE_NO_NUM_1);
                    }
                });
            }
        }
    }

    private void getHttpResult(final String pagerNoNum) {
        //排行榜
        RetrofitConfig.getAcRanking().onRankingResult(AcApi.getAcRankingUrl(mPartitionType, pagerNoNum), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReOther, Response response) {
                if (getActivity() != null && !getActivity().isDestroyed()) {
                    if (acReOther.getData() != null) {
                        if (acReOther.getData().getPage().getList().size() != 0) {
                            if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                mAdapter.addAcReOtherDate(acReOther);
                            } else {
                                mAdapter.setmAcReOther(acReOther);
                                mPagerNoNum++;
                            }
                        } else {
                            if (!getActivity().isDestroyed()) {
                                GlobalUtils.showToastShort(getActivity(), "没有更多了 (´･ω･｀)");
                            }
                        }
                    }
                    if (!getActivity().isDestroyed() && mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
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
