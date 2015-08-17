package org.succlz123.AxBTube.ui.fragment.acfun.other;

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

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcHotRankingRvAdapter;
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
 * Created by chinausky on 2015/7/27.
 */
public class AcHotRankingFragment extends BaseFragment {

    public static AcHotRankingFragment newInstance(String channelType) {
        AcHotRankingFragment fragment = new AcHotRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AcString.CHANNEL_IDS, channelType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int TYPE_RECOMMEND_HOT = 0;
    private static final int TYPE_MOST_POPULAR = 1;

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcHotRankingRvAdapter mAdapter;
    private GridLayoutManager mManager;
    private int mPagerNoNum = 1;

    @Bind(R.id.ac_fragment_partition_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);
        ButterKnife.bind(this, view);
        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcHotRankingRvAdapter.MyDecoration());
        mAdapter = new AcHotRankingRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnClickListener(new AcPartitionRvAdapter.OnClickListener() {
//            @Override
//            public void onClick(View view, int position, String contentId) {
//                AcContentActivity.startActivity(getActivity(), contentId);
//            }
//        });
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
//                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {
//                    mSwipeRefreshLayout.setRefreshing(true);
//                    mPagerNoNum++;
//                    getHttpResult(TYPE_LAST_POST, "" + mPagerNoNum);
//                }
//            }
//        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getHttpResult(TYPE_MOST_POPULAR, null);
//                getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
//            }
//        });

        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
            if (TextUtils.equals(mPartitionType, AcString.TITLE_HOT) && mAdapter.getmAcReHot() == null) {
                getHttpResult(TYPE_RECOMMEND_HOT, null);
                GlobalUtils.showToastShort(getActivity(), AcString.TITLE_HOT);
                return;
            }
            if (mAdapter.getmAcReOther() == null) {
                getHttpResult(TYPE_MOST_POPULAR, AcString.PAGE_NO_NUM_1);
            }
        }
    }

    private void getHttpResult(int type, final String pagerNoNum) {
        mSwipeRefreshLayout.setRefreshing(true);

        RetrofitConfig.getAcRanking().onRankingResult(AcApi.getAcRankingUrl(mPartitionType, pagerNoNum),
                new Callback<AcReOther>() {
                    @Override
                    public void success(AcReOther acReOther, Response response) {
                        if (acReOther.getData() != null) {
                            if (acReOther.getData().getPage().getList().size() != 0) {
                                if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                    mAdapter.addDate(acReOther);
                                } else {
                                    mAdapter.setmAcReOther(acReOther);
                                }
                            } else {
                                GlobalUtils.showToastShort(getActivity(), "没有更多了 (´･ω･｀)");
                            }
                        }
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}
