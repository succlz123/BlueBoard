package org.succlz123.blueboard.controller.fragment.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.view.adapter.recyclerview.AcBangumiRvAdapter;
import org.succlz123.blueboard.model.api.acfun.AcApi;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.bean.acfun.AcBangumi;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.controller.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by succlz123 on 2015/5/2.
 */
public class AcBangumiFragment extends BaseFragment {
    private boolean mIsPrepared;
    private AcBangumiRvAdapter mAdapter;

    @Bind(R.id.ac_fragment_bangumi_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_bangumi, container, false);
        ButterKnife.bind(this, view);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new AcBangumiRvAdapter.MyDecoration());
        mAdapter = new AcBangumiRvAdapter();
        mAdapter.setOnClickListener(new AcBangumiRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                GlobalUtils.showToastShort(getActivity(), "TODO");
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
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
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
            if (mAdapter.getmAcBangumi() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getHttpResult();
                        mSwipeRefreshLayout.setRefreshing(true);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
            }
        }
    }

    private void getHttpResult() {
        //新番专题
        Call<AcBangumi> call = AcApi.getAcBangumi().onResult(AcApi.buildAcBangumiUrl(AcString.BANGUMI_TYPES_ANIMATION));

        call.enqueue(new Callback<AcBangumi>() {
            @Override
            public void onResponse(Response<AcBangumi> response, Retrofit retrofit) {
                AcBangumi acBangumi = response.body();
                if (acBangumi != null
                        && getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcBangumiFragment.this.getUserVisibleHint()) {
                    mAdapter.setBangumiInfo(acBangumi);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (getActivity() != null
                        && !getActivity().isDestroyed()
                        && !getActivity().isFinishing()
                        && AcBangumiFragment.this.getUserVisibleHint()) {
                    GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "刷新过快或者网络连接异常");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }
        });
    }
}
