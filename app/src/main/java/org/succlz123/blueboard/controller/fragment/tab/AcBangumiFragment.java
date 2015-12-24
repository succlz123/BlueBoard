package org.succlz123.blueboard.controller.fragment.tab;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcBangumi;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.tab.AcBangumiRvAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
public class AcBangumiFragment extends BaseFragment {

    public static AcBangumiFragment newInstance() {
        AcBangumiFragment fragment = new AcBangumiFragment();
        return fragment;
    }

    private boolean mIsPrepared;
    private AcBangumiRvAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_bangumi, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_bangumi_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new AcBangumiRvAdapter.MyDecoration());
        mAdapter = new AcBangumiRvAdapter();

        mAdapter.setOnClickListener(new AcBangumiRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                GlobalUtils.showToastShort("TODO");
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult();
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
        if (mAdapter.getmAcBangumi() != null) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getHttpResult();
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

    private void getHttpResult() {
        //新番专题
        HashMap<String, String> httpParameter = AcApi.buildAcBangumiUrl(AcString.BANGUMI_TYPES_ANIMATION);

        Observable<AcBangumi> observable = AcApi.getAcBangumi().onResult(httpParameter);

        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcBangumi, Boolean>() {
                    @Override
                    public Boolean call(AcBangumi acBangumi) {
                        Boolean isFragmentLive = AcBangumiFragment.this.getUserVisibleHint()
                                && GlobalUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .subscribe(new Action1<AcBangumi>() {
                    @Override
                    public void call(AcBangumi acBangumi) {
                        mAdapter.setBangumiInfo(acBangumi);

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
