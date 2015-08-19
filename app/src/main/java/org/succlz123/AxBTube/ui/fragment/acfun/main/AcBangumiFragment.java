package org.succlz123.AxBTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcBangumi;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcBangumiRvAdapter;
import org.succlz123.AxBTube.support.config.RetrofitConfig;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by fashi on 2015/5/2.
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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult();
                    }
                });
            }
        }
    }

    private void getHttpResult() {
        //新番专题
        RetrofitConfig.getAcBangumi().onBangumiResult(AcApi.getAcBangumiUrl(AcString.BANGUMI_TYPES_ANIMATION), new Callback<AcBangumi>() {
            @Override
            public void success(AcBangumi acBangumi, Response response) {
                if (getActivity() != null && !getActivity().isDestroyed()) {
                    mAdapter.setBangumiInfo(acBangumi);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
