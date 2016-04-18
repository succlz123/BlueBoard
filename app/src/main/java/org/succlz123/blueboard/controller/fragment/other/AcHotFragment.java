package org.succlz123.blueboard.controller.fragment.other;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcReHot;
import org.succlz123.blueboard.model.utils.common.OkUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.other.AcHotRvAdapter;

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
 * Created by succlz123 on 15/8/18.
 */
public class AcHotFragment extends BaseFragment {

    public static AcHotFragment newInstance() {
        AcHotFragment fragment = new AcHotFragment();
        return fragment;
    }

    private boolean mIsPrepared;
    private AcHotRvAdapter mAdapter;
    private GridLayoutManager mManager;
    private int mPagerNoNum = 1;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_partition_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
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
        if (mAdapter.getmAcReHot() != null) {
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
        //热门焦点
        HashMap<String, String> httpParameter = AcApi.buildAcReHotUrl(pagerNoNum);

        Observable<AcReHot> observable = AcApi.getAcRecommend().onAcReHotResult(httpParameter);

        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcReHot, Boolean>() {
                    @Override
                    public Boolean call(AcReHot acReHot) {
                        Boolean isFragmentLive = AcHotFragment.this.getUserVisibleHint()
                                && OkUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .filter(new Func1<AcReHot, Boolean>() {
                    @Override
                    public Boolean call(AcReHot acReHot) {
                        boolean isSuccess = acReHot.getData() != null;
                        if (isSuccess) {
                            isSuccess = (acReHot.getData().getPage().getList().size()) > 0;
                        }
                        return isSuccess;
                    }
                }).subscribe(new Action1<AcReHot>() {
                    @Override
                    public void call(AcReHot acReHot) {
                        if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                            mAdapter.addAcReHotDate(acReHot);
                        } else {
                            mAdapter.setmAcReHot(acReHot);
                        }

                        mPagerNoNum++;

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}

