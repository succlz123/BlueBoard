package org.succlz123.blueboard.controller.fragment.other;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.other.AcPartitionRvAdapter;

import android.app.Activity;
import android.content.SharedPreferences;
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

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by succlz123 on 2015/7/27.
 */
public class AcPartitionFragment extends BaseFragment {

    public static AcPartitionFragment newInstance(String channelType) {
        AcPartitionFragment fragment = new AcPartitionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int TYPE_RECOMMEND_HOT = 0;
    private static final int TYPE_MOST_POPULAR = 1;
    private static final int TYPE_LAST_POST = 2;

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcPartitionRvAdapter mAdapter;
    private GridLayoutManager mManager;
    private int mPagerNoNum = 1;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_partition_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new GridLayoutManager(getActivity(), 2);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 1 | position == 2 | position == 3 | position == 4) {
                    return 1;
                }
                return 2;
            }
        });
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcPartitionRvAdapter.MyDecoration());
        mAdapter = new AcPartitionRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AcPartitionRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                AcContentActivity.startActivity(getActivity(), contentId);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {

                    getHttpResult(TYPE_LAST_POST, "" + mPagerNoNum);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(TYPE_MOST_POPULAR, null);
                getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
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
        if (mAdapter.getAcMostPopular() == null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(TYPE_MOST_POPULAR, null);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (mAdapter.getAcLastPost() == null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }

    public void setHttpOrder() {
        getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
    }

    private void getHttpResult(int type, final String pagerNoNum) {
        SharedPreferences settings
                = getActivity().getSharedPreferences(getString(R.string.app_name), Activity.MODE_PRIVATE);
        String order
                = settings.getString(AcString.ORDER_BY, AcString.TIME_ORDER);
        //人气最旺
        if (type == TYPE_MOST_POPULAR) {
            HashMap<String, String> httpParameterPop = AcApi.buildAcPartitionUrl(mPartitionType,
                    AcString.POPULARITY,
                    AcString.ONE_WEEK,
                    AcString.PAGE_SIZE_NUM_10,
                    AcString.PAGE_NO_NUM_1);

            Observable<AcReOther> observablePop = AcApi.getAcPartition().onResult(httpParameterPop);

            Subscription subscriptionPop = observablePop.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcPartitionFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    }).filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            boolean isSuccess = acReOther.getData() != null;
                            if (isSuccess) {
                                isSuccess = (acReOther.getData().getPage().getList().size()) > 0;
                            }
                            return isSuccess;
                        }
                    }).subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.setAcMostPopular(acReOther);
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

            mCompositeSubscription.add(subscriptionPop);
            //最新发布
            if (type == TYPE_LAST_POST) {
                HashMap<String, String> httpParameterPost = AcApi.buildAcPartitionUrl(mPartitionType,
                        order,
                        AcString.ONE_WEEK,
                        AcString.PAGE_SIZE_NUM_10,
                        pagerNoNum);

                Observable<AcReOther> observablePost = AcApi.getAcPartition().onResult(httpParameterPost);

                Subscription subscriptionPost = observablePost.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<AcReOther, Boolean>() {
                            @Override
                            public Boolean call(AcReOther acReOther) {
                                Boolean isFragmentLive = AcPartitionFragment.this.getUserVisibleHint()
                                        && GlobalUtils.isActivityLive(getActivity());
                                return isFragmentLive;
                            }
                        }).filter(new Func1<AcReOther, Boolean>() {
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
                                    mAdapter.addDate(acReOther);
                                } else {
                                    mAdapter.setAcLastPost(acReOther);
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
                mCompositeSubscription.add(subscriptionPost);
            }
        }
    }
}
