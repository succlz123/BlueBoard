package org.succlz123.blueboard.view.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.config.BusProvider;
import org.succlz123.blueboard.controller.adapter.recyclerview.AcContentInfoRvAdapter;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.bean.acfun.AcContentInfo;
import org.succlz123.blueboard.utils.GlobalUtils;
import org.succlz123.blueboard.utils.ViewUtils;
import org.succlz123.blueboard.view.activity.acfun.AcContentActivity;
import org.succlz123.blueboard.view.activity.acfun.DownLoadActivity;
import org.succlz123.blueboard.view.activity.video.VideoPlayActivity;
import org.succlz123.blueboard.view.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    public static AcContentInfoFragment startFragment(String contentId) {
        AcContentInfoFragment fragment = new AcContentInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_ID, contentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.ac_fragment_content_reply_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String CONTENT_ID = "contentId";
    private boolean mIsPrepared;
    private AcContentInfoRvAdapter mAdapter;
    private String mContentId;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);
        ButterKnife.bind(this, view);
        BusProvider.getInstance().register(this);
        mContentId = getArguments().getString(CONTENT_ID);

        if (mContentId == null) {
            GlobalUtils.showToastShort(getActivity(), "数据连接错误,重重试");
            return null;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AcContentInfoRvAdapter();
        mAdapter.setOnVideoPlayClickListener(new AcContentInfoRvAdapter.OnVideoPlayClickListener() {
            @Override
            public void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType) {
                if (position == 0) {
                    GlobalUtils.showToastShort(getActivity(), "TODO " + userId);
                } else {
                    VideoPlayActivity.startActivity(getActivity(), videoId, danmakuId, sourceId, sourceType);
                }
            }
        });
        mAdapter.setOnDownLoadClickListener(new AcContentInfoRvAdapter.OnDownLoadClickListener() {
            @Override
            public void onClick(View view, int position, ArrayList<AcContentInfo.DataEntity.FullContentEntity.VideosEntity> downLoadList) {
                DownLoadActivity.startActivity(getActivity(), downLoadList);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    @Subscribe
    public void onIsDlCheckBoxShow(AcContentActivity.DlCheckBox DlCheckBox) {
        mAdapter.setIsShowDlCheckBox(DlCheckBox.isDlCheckBoxShow(), DlCheckBox.isDlCheckBoxSelectAll());
    }

    @Subscribe
    public boolean getIsDlCheckBoxShow(AcContentActivity.DlCheckBox DlCheckBox) {
        return mAdapter.isShowDlCheckBox();
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible || mContentId == null) {
            return;
        } else {
            if (mAdapter.getAcContentInfo() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        sendHttpRequest();
                    }
                });
            }
        }
    }

    private void sendHttpRequest() {
        //视频信息
        Observable<AcContentInfo> observable = AcApi.getAcContentInfo().onResult(AcApi.buildAcContentInfoUrl(mContentId));
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<AcContentInfo, Boolean>() {
                    @Override
                    public Boolean call(AcContentInfo acContentInfo) {
                        return GlobalUtils.isActivityLive(getActivity());
                    }
                })
                .filter(new Func1<AcContentInfo, Boolean>() {
                    @Override
                    public Boolean call(AcContentInfo acContentInfo) {
                        boolean isSuccess = !(!acContentInfo.isSuccess()
                                || acContentInfo.getStatus() != 200
                                || acContentInfo.getStatus() == 404
                                || acContentInfo.getStatus() == 403);
                        if (!isSuccess) {
                            GlobalUtils.showToastShort(getActivity(), acContentInfo.getMsg());
                        }
                        return isSuccess;
                    }
                })
                .subscribe(new Action1<AcContentInfo>() {
                    @Override
                    public void call(AcContentInfo acContentInfo) {
                        mAdapter.setContentInfo(acContentInfo);
                        BusProvider.getInstance().post(acContentInfo.getData().getFullContent());
                        ViewUtils.setSwipeRefreshOk(mSwipeRefreshLayout);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        GlobalUtils.showToastShort(getActivity(), "网络连接异常");
                        ViewUtils.setSwipeRefreshFailed(mSwipeRefreshLayout);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        BusProvider.getInstance().unregister(this);
    }
}
