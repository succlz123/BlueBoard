package org.succlz123.blueboard.controller.fragment.other;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcContentReply;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.view.adapter.recyclerview.content.AcContentReplyRvAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentReplyFragment extends BaseFragment {

    public static AcContentReplyFragment startFragment(String contentId) {
        AcContentReplyFragment fragment = new AcContentReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String CONTENT_ID = "contentId";
    private boolean mIsPrepared;
    private String mContentId;
    private AcContentReplyRvAdapter mAdapter;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_reply, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_content_reply_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mContentId = getArguments().getString(CONTENT_ID);

        if (mContentId == null) {
            GlobalUtils.showToastShort("网络异常,请重试");
            return null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AcContentReplyRvAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mIsPrepared = true;

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !mIsVisible || mContentId == null) {
            return;
        }
        if (mAdapter.getmAcContentReply() != null) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getHttpResult();
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
        //评论
        HashMap<String, String> httpParameter =
                AcApi.buildAcContentReplyUrl(mContentId, AcString.PAGE_SIZE_NUM_50, AcString.PAGE_NO_NUM_1);

        Observable<AcContentReply> observable = AcApi.getAcContentReply().onResult(httpParameter);

        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcContentReply, Boolean>() {
                    @Override
                    public Boolean call(AcContentReply acContentReply) {
                        Boolean isFragmentLive = AcContentReplyFragment.this.getUserVisibleHint()
                                && GlobalUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .filter(new Func1<AcContentReply, Boolean>() {
                    @Override
                    public Boolean call(AcContentReply acContentReply) {
                        boolean isReply = acContentReply.getData().getPage().getList().size() > 0;
                        if (!isReply) {
                            GlobalUtils.showToastShort("并没有评论");
                        }
                        return isReply;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<AcContentReply, ArrayList<AcContentReply.DataEntity.Entity>>() {
                    @Override
                    public ArrayList<AcContentReply.DataEntity.Entity> call(AcContentReply acContentReply) {
                        return sortListReply(acContentReply);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<AcContentReply.DataEntity.Entity>>() {
                    @Override
                    public void call(ArrayList<AcContentReply.DataEntity.Entity> acContentReplyList) {
                        mAdapter.setContentReply(acContentReplyList);

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        GlobalUtils.showToastShort("网络连接异常,请重试");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private ArrayList<AcContentReply.DataEntity.Entity> sortListReply(AcContentReply acContentReply) {
        ArrayList<AcContentReply.DataEntity.Entity> replyList = new ArrayList<>();
        ArrayList<Integer> replyIdList = acContentReply.getData().getPage().getList();
        HashMap<String, AcContentReply.DataEntity.Entity> replyIdMap = acContentReply.getData().getPage().getMap();

        for (Integer id : replyIdList) {
            replyList.add(replyIdMap.get("c" + String.valueOf(id)));
        }

        for (AcContentReply.DataEntity.Entity reply : replyList) {
            AcContentReply.DataEntity.Entity currentReply = reply;
            int quoteId = currentReply.getQuoteId();
            while (quoteId != 0 && currentReply.getQuoteReply() == null) {
                AcContentReply.DataEntity.Entity quoteReply = replyIdMap.get("c" + quoteId);
                currentReply.setQuoteReply(quoteReply);
                currentReply = quoteReply;
                quoteId = currentReply.getQuoteId();
            }
        }
        return replyList;
    }
}


