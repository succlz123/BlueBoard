package org.succlz123.blueboard.controller.fragment.other;

import com.squareup.otto.Subscribe;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.controller.activity.video.VideoPlayActivity;
import org.succlz123.blueboard.controller.base.BaseFragment;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.model.config.BusProvider;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.service.DownloadService;
import org.succlz123.blueboard.view.adapter.recyclerview.content.AcContentInfoRvAdapter;
import org.succlz123.okdownload.OkDownloadRequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
public class AcContentInfoFragment extends BaseFragment {

    public static AcContentInfoFragment newInstance(String contentId) {
        AcContentInfoFragment fragment = new AcContentInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String CONTENT_ID = "contentId";
    private boolean mIsPrepared;
    private AcContentInfoRvAdapter mAdapter;
    private String mContentId;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_content_reply_recycler_view);
        mSwipeRefreshLayout = f(view, R.id.swipe_fresh_layout);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        BusProvider.getInstance().register(this);
        mContentId = getArguments().getString(CONTENT_ID);

        if (mContentId == null) {
            GlobalUtils.showToastShort("网络异常,请重试");
            return null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AcContentInfoRvAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnVideoPlayClickListener(new AcContentInfoRvAdapter.OnVideoPlayClickListener() {
            @Override
            public void onClick(View view, int position, String userId, String videoId, String sourceId, String sourceType, String sourceTitle) {
                if (position == 0) {
                    GlobalUtils.showToastShort("TODO " + userId);
                } else {
                    VideoPlayActivity.newInstance(getActivity(), videoId, sourceId, sourceType, sourceTitle);
                }
            }
        });
        mAdapter.setOnDownLoadClickListener(new AcContentInfoRvAdapter.OnDownLoadClickListener() {
            @Override
            public void onClick(View view, int position, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
                if (downLoadList == null || downLoadList.size() <= 0) {
                    return;
                }

                ArrayList<OkDownloadRequest> requestList = new ArrayList<>();
                if (requestList.size() > 5) {
                    GlobalUtils.showToastShort("抱歉,最多只能同时下5个视频");
                    return;
                }

                for (AcContentInfo.DataEntity.FullContentEntity.VideosEntity videosEntity : downLoadList) {
                    if (TextUtils.equals(videosEntity.getSourceType(), "zhuzhan")) {

                        OkDownloadRequest request = new OkDownloadRequest.Builder()
                                .title(videosEntity.getVideoTitle())
                                .description(videosEntity.getName())
                                .sign(videosEntity.getDanmakuId())
                                .build();

                        requestList.add(request);
                    } else {
                        GlobalUtils.showToastShort("非主站");
                    }
                }

                DownloadService.startService(getActivity(), requestList);
            }
        });

        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !mIsVisible || mContentId == null) {
            return;
        }
        if (mAdapter.getAcContentInfo() != null) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                sendHttpRequest();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    private void sendHttpRequest() {
        //视频信息
        HashMap<String, String> httpParameter = AcApi.buildAcContentInfoUrl(mContentId);

        Observable<AcContentInfo> observable = AcApi.getAcContentInfo().onResult(httpParameter);

        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcContentInfo, Boolean>() {
                    @Override
                    public Boolean call(AcContentInfo acContentInfo) {
                        Boolean isFragmentLive = AcContentInfoFragment.this.getUserVisibleHint()
                                && GlobalUtils.isActivityLive(getActivity());
                        return isFragmentLive;
                    }
                })
                .filter(new Func1<AcContentInfo, Boolean>() {
                    @Override
                    public Boolean call(AcContentInfo acContentInfo) {
                        boolean isSuccess = acContentInfo.isSuccess() && acContentInfo.getStatus() == 200;
                        if (!isSuccess) {
                            GlobalUtils.showToastShort(acContentInfo.getMsg());
                        }
                        return isSuccess;
                    }
                })
                .subscribe(new Action1<AcContentInfo>() {
                    @Override
                    public void call(AcContentInfo acContentInfo) {
                        mAdapter.setContentInfo(acContentInfo);
                        BusProvider.getInstance().post(acContentInfo.getData().getFullContent());

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

    @Subscribe
    public void onIsDlCheckBoxShow(AcContentActivity.DlCheckBox DlCheckBox) {
        mAdapter.setIsShowDlCheckBox(DlCheckBox.isDlCheckBoxShow(), DlCheckBox.isDlCheckBoxSelectAll());
    }

    @Subscribe
    public boolean getIsDlCheckBoxShow(AcContentActivity.DlCheckBox DlCheckBox) {
        return mAdapter.isShowDlCheckBox();
    }
}
