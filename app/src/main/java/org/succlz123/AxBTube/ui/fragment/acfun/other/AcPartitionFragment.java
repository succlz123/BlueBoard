package org.succlz123.AxBTube.ui.fragment.acfun.other;

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

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcPartitionRvAdapter;
import org.succlz123.AxBTube.support.config.RetrofitConfig;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.acfun.AcContentActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chinausky on 2015/7/27.
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
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                        && mManager.findLastVisibleItemPosition() + 1 == mAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mPagerNoNum++;
                    getHttpResult(TYPE_LAST_POST, "" + mPagerNoNum);
                }
            }
        });

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpResult(TYPE_MOST_POPULAR, null);
                getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
            }
        });

        mIsPrepared = true;
        lazyLoad();

        return view;
    }

    /**
     * 延迟加载 默认只在fragment显示时才去发起http请求 使viewPager只加载一页
     */
    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
            if (mAdapter.getmAcMostPopular() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult(TYPE_MOST_POPULAR, null);
                    }
                });
            }
            if (mAdapter.getmAcLastPost() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult(TYPE_LAST_POST, AcString.PAGE_NO_NUM_1);
                    }
                });
            }
        }
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
            RetrofitConfig.getAcPartition().onResult(AcApi.getAcPartitionUrl(
                    mPartitionType,
                    AcString.POPULARITY,
                    AcString.ONE_WEEK,
                    AcString.PAGE_SIZE_NUM_10,
                    AcString.PAGE_NO_NUM_1), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReOther, Response response) {
                    if (getActivity() != null && !getActivity().isDestroyed()) {
                        if (acReOther.getData() != null
                                && acReOther.getData().getPage().getList().size() != 0) {
                            mAdapter.setmAcMostPopular(acReOther);
                        }
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (getActivity() != null && !getActivity().isDestroyed() && AcPartitionFragment.this.getUserVisibleHint()) {
                        GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络连接异常");
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            });
        }
        //最新发布
        if (type == TYPE_LAST_POST) {
            RetrofitConfig.getAcPartition().onResult(AcApi.getAcPartitionUrl(mPartitionType,
                    order,
                    AcString.ONE_WEEK,
                    AcString.PAGE_SIZE_NUM_10,
                    pagerNoNum), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReOther, Response response) {
                    if (getActivity() != null && !getActivity().isDestroyed()) {
                        if (acReOther.getData() != null) {
                            if (acReOther.getData().getPage().getList().size() != 0) {
                                if (!TextUtils.equals(pagerNoNum, AcString.PAGE_NO_NUM_1)) {
                                    mAdapter.addDate(acReOther);
                                } else {
                                    mAdapter.setmAcLastPost(acReOther);
                                }
                            } else {
                                GlobalUtils.showToastShort(getActivity(), "没有更多了 (´･ω･｀)");
                            }
                        }
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (getActivity() != null
                            && !getActivity().isDestroyed()
                            && AcPartitionFragment.this.getUserVisibleHint()) {
                        GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络连接异常");
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            });
        }
    }
}
