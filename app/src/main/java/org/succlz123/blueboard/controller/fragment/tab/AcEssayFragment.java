package org.succlz123.blueboard.controller.fragment.tab;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcEssay;
import org.succlz123.blueboard.model.utils.common.OkUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.tab.AcEssayRvAdapter;

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

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_essay, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_essay_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AcEssayRvAdapter();

        mAdapter.setOnClickListener(new AcEssayRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                OkUtils.showToastShort("TODO");
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
        if (mAdapter.getmAcEssay() != null) {
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
        super.onDestroy();
    }

    private void getHttpResult(final String pagerNoNum) {
        //文章
        HashMap<String, String> httpParameter = AcApi.buildAcPartitionUrl(mPartitionType,
                AcString.POPULARITY,
                AcString.ONE_WEEK,
                AcString.PAGE_SIZE_NUM_20, pagerNoNum);

        Observable<AcEssay> observable = AcApi.getAcPartition().onEssayResult(httpParameter);

        Subscription subscription= observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcEssay, Boolean>() {
                    @Override
                    public Boolean call(AcEssay acEssay) {
                        Boolean isFragmentLive = AcEssayFragment.this.getUserVisibleHint()
                                && OkUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .subscribe(new Action1<AcEssay>() {
                    @Override
                    public void call(AcEssay acEssay) {
                        if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                            mAdapter.addData(acEssay);
                        } else {
                            mAdapter.setEssayInfo(acEssay);
                        }
                        mPagerNoNum++;

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        OkUtils.showToastShort("刷新过快或者网络连接异常");

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}

