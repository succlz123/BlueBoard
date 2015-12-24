package org.succlz123.blueboard.controller.fragment.tab;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.controller.activity.acfun.AcPartitionActivity;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcReBanner;
import org.succlz123.blueboard.model.bean.acfun.AcReHot;
import org.succlz123.blueboard.model.bean.acfun.AcReOther;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.tab.AcRecommendRvAdapter;

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
import rx.subscriptions.CompositeSubscription;

/**
 * Created by succlz123 on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {

    public static AcRecommendFragment newInstance() {
        AcRecommendFragment fragment = new AcRecommendFragment();
        return fragment;
    }

    private boolean mIsPrepared;
    private AcRecommendRvAdapter mAdapter;
    private AcApi.onAcRecommend acRecommend;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_recommend, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_recommend_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 0 | position == 1 | position == 6 | position == 9 | position == 12
                        | position == 15 | position == 18 | position == 21 | position == 24) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new AcRecommendRvAdapter.MyDecoration());
        mAdapter = new AcRecommendRvAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //解决viewpager里滑动导致swipeReFreshLayout的出现
        mAdapter.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mAdapter.setOnClickListener(new AcRecommendRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, String partitionType, String contentId) {
                if (partitionType != null) {
                    //启动分区页面
                    AcPartitionActivity.startActivity(getActivity(), partitionType);
                } else if (contentId != null) {
                    //启动视频信息页面
                    AcContentActivity.startActivity(getActivity(), contentId);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(AcString.BANNER);
                getHttpResult(AcString.HOT);
                getHttpResult(AcString.ANIMATION);
                getHttpResult(AcString.FUN);
                getHttpResult(AcString.MUSIC);
                getHttpResult(AcString.GAME);
                getHttpResult(AcString.SCIENCE);
                getHttpResult(AcString.SPORT);
                getHttpResult(AcString.TV);
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
        if (mAdapter.getAcReBanner() == null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(AcString.BANNER);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (mAdapter.getAcReHot() == null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    getHttpResult(AcString.HOT);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
        if (mAdapter.getAcReAnimation() == null) {
            getHttpResult(AcString.ANIMATION);
        }
        if (mAdapter.getAcReFun() == null) {
            getHttpResult(AcString.FUN);
        }
        if (mAdapter.getAcReMusic() == null) {
            getHttpResult(AcString.MUSIC);
        }
        if (mAdapter.getAcReGame() == null) {
            getHttpResult(AcString.GAME);
        }
        if (mAdapter.getAcReScience() == null) {
            getHttpResult(AcString.SCIENCE);
        }
        if (mAdapter.getAcReSport() == null) {
            getHttpResult(AcString.SPORT);
        }
        if (mAdapter.getAcReTv() == null) {
            getHttpResult(AcString.TV);
        }
    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }

    private void getHttpResult(String httpGetType) {
        acRecommend = AcApi.getAcRecommend();

        if (TextUtils.equals(httpGetType, AcString.BANNER)) {
            //首页横幅
            HashMap<String, String> httpParameterBanner = AcApi.buildAcReBannerUrl();

            Observable<AcReBanner> observableBanner = acRecommend.onAcReBannerResult(httpParameterBanner);

            Subscription subscriptionBanner = observableBanner.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReBanner, Boolean>() {
                        @Override
                        public Boolean call(AcReBanner acReBanner) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReBanner>() {
                        @Override
                        public void call(AcReBanner acReBanner) {
                            mAdapter.onReBannerResult(acReBanner);

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
            mCompositeSubscription.add(subscriptionBanner);
        } else if (TextUtils.equals(httpGetType, AcString.HOT)) {
            //首页热门焦点
            HashMap<String, String> httpParameterHot = AcApi.buildAcReHotUrl(AcString.PAGE_NO_NUM_1);

            Observable<AcReHot> observableHot = acRecommend.onAcReHotResult(httpParameterHot);

            Subscription subscriptionHot = observableHot.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReHot, Boolean>() {
                        @Override
                        public Boolean call(AcReHot acReHot) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReHot>() {
                        @Override
                        public void call(AcReHot acReHot) {
                            mAdapter.onAcReHotResult(acReHot);

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
            mCompositeSubscription.add(subscriptionHot);
        } else if (TextUtils.equals(httpGetType, AcString.ANIMATION)) {
            //动画区
            HashMap<String, String> httpParameterAnimation = AcApi.buildAcReOtherUrl(AcString.ANIMATION,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableAnimation = acRecommend.onAcReOtherResult(httpParameterAnimation);

            Subscription subscriptionAnimation = observableAnimation.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReAnimationResult(acReOther);

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
            mCompositeSubscription.add(subscriptionAnimation);
        } else if (TextUtils.equals(httpGetType, AcString.FUN)) {
            //娱乐区
            HashMap<String, String> httpParameterFun = AcApi.buildAcReOtherUrl(AcString.FUN,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableFun = acRecommend.onAcReOtherResult(httpParameterFun);

            Subscription subscriptionFun = observableFun.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReFunResult(acReOther);

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
            mCompositeSubscription.add(subscriptionFun);
        } else if (TextUtils.equals(httpGetType, AcString.MUSIC)) {
            //音乐区
            HashMap<String, String> httpParameterMusic = AcApi.buildAcReOtherUrl(AcString.MUSIC,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableMusic = acRecommend.onAcReOtherResult(httpParameterMusic);

            Subscription subscriptionMusic = observableMusic.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReMusicResult(acReOther);

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
            mCompositeSubscription.add(subscriptionMusic);
        } else if (TextUtils.equals(httpGetType, AcString.GAME)) {
            //游戏区
            HashMap<String, String> httpParameterGame = AcApi.buildAcReOtherUrl(AcString.GAME,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableGame = acRecommend.onAcReOtherResult(httpParameterGame);

            Subscription subscriptionGame = observableGame.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReGameResult(acReOther);

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
            mCompositeSubscription.add(subscriptionGame);
        } else if (TextUtils.equals(httpGetType, AcString.SCIENCE)) {
            //科学区
            HashMap<String, String> httpParameterScience = AcApi.buildAcReOtherUrl(AcString.SCIENCE,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableScience = acRecommend.onAcReOtherResult(httpParameterScience);

            Subscription subscriptionScience = observableScience.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReScienceResult(acReOther);

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
            mCompositeSubscription.add(subscriptionScience);
        } else if (TextUtils.equals(httpGetType, AcString.SPORT)) {
            //体育区
            HashMap<String, String> httpParameterSport = AcApi.buildAcReOtherUrl(AcString.SPORT,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableSport = acRecommend.onAcReOtherResult(httpParameterSport);

            Subscription subscriptionSport = observableSport.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReSportResult(acReOther);

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
            mCompositeSubscription.add(subscriptionSport);
        } else if (TextUtils.equals(httpGetType, AcString.TV)) {
            //影视区
            HashMap<String, String> httpParameterTv = AcApi.buildAcReOtherUrl(AcString.TV,
                    AcString.LAST_POST, AcString.ONE_WEEK);

            Observable<AcReOther> observableTv = acRecommend.onAcReOtherResult(httpParameterTv);

            Subscription subscriptionTv = observableTv.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<AcReOther, Boolean>() {
                        @Override
                        public Boolean call(AcReOther acReOther) {
                            Boolean isFragmentLive = AcRecommendFragment.this.getUserVisibleHint()
                                    && GlobalUtils.isActivityLive(getActivity());
                            return isFragmentLive;
                        }
                    })
                    .subscribe(new Action1<AcReOther>() {
                        @Override
                        public void call(AcReOther acReOther) {
                            mAdapter.onAcReTvResult(acReOther);

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
            mCompositeSubscription.add(subscriptionTv);
        }
    }
}

