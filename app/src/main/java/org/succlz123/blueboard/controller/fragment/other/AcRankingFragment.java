package org.succlz123.blueboard.controller.fragment.other;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.other.AcRankingRvAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by succlz123 on 2015/7/27.
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

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_partition_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
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

                    getHttpResult("" + mPagerNoNum);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

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
        if (!mIsPrepared || !mIsVisible) {
            return;
        }
        if (mAdapter.getmAcReOther() != null) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getHttpResult(AcString.PAGE_NO_NUM_1);
                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getHttpResult(final String pagerNoNum) {
        //排行榜
        HashMap<String, String> httpParameter = AcApi.buildAcRankingUrl(mPartitionType, pagerNoNum);

        Observable<AcReOther> observable = AcApi.getAcRanking().onRankingResult(httpParameter);

        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcReOther, Boolean>() {
                    @Override
                    public Boolean call(AcReOther acReOther) {
                        Boolean isFragmentLive = AcRankingFragment.this.getUserVisibleHint()
                                && GlobalUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .filter(new Func1<AcReOther, Boolean>() {
                    @Override
                    public Boolean call(AcReOther acReOther) {
                        boolean isSuccess = acReOther.getData() != null;
                        if (isSuccess) {
                            isSuccess = (acReOther.getData().getPage().getList().size()) > 0;
                        } else {
                            GlobalUtils.showToastShort("没有更多了 (´･ω･｀)");
                        }
                        return isSuccess;
                    }
                }).subscribe(new Action1<AcReOther>() {
                    @Override
                    public void call(AcReOther acReOther) {
                        if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                            mAdapter.addAcReOtherDate(acReOther);
                        } else {
                            mAdapter.setmAcReOther(acReOther);
                        }
                        mPagerNoNum++;

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        GlobalUtils.showToastShort("刷新过快或者网络连接异常");

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                });
    }
}
