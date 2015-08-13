package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcPartitionRvAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.ui.activity.acfun.AcContentActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
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

    @Bind(R.id.ac_fragment_partition_recycler_view)
    RecyclerView mRecyclerView;

    private static final int TYPE_RECOMMEND_HOT = 0;
    private static final int TYPE_HOT = 1;
    private static final int TYPE_LAST_POST = 2;

    private String mPartitionType;
    private boolean mIsPrepared;
    private AcPartitionRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);
        ButterKnife.bind(this, view);
        mPartitionType = getArguments().getString(AcString.CHANNEL_IDS);


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position == 1 | position == 2 | position == 3 | position == 4) {
                    return 1;
                }
                return 2;
            }
        });

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AcPartitionRvAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new AcPartitionRvAdapter.PartitionDecoration());
        mAdapter.setOnClickListener(new AcPartitionRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                AcContentActivity.startActivity(getActivity(), contentId);
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
            if (TextUtils.equals(mPartitionType, AcString.TITLE_HOT) && mAdapter.getmAcReHot() == null) {
                getHttpResult(TYPE_RECOMMEND_HOT);
                GlobalUtils.showToastShort(getActivity(), AcString.TITLE_HOT);
                return;
            } else if (mAdapter.getmAcHot() == null) {
                getHttpResult(TYPE_HOT);
            } else if (mAdapter.getmAcLastPost() == null) {
                getHttpResult(TYPE_LAST_POST);
            }
        }
    }

    private void getHttpResult(int type) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();
        AcApi.getAcPartition acPartition = restAdapter.create(AcApi.getAcPartition.class);
        AcApi.getAcRecommend acRecommend = restAdapter.create(AcApi.getAcRecommend.class);
        //热门焦点
        if (type == TYPE_RECOMMEND_HOT) {
            acRecommend.onAcReHotResult(AcApi.getAcReHotUrl(), new Callback<AcReHot>() {
                @Override
                public void success(AcReHot acReHot, Response response) {
                    mAdapter.onHotResult(acReHot);
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
        //人气最旺
        if (type == TYPE_HOT) {
            acPartition.onResult(AcApi.getAcPartitionUrl(mPartitionType, AcString.POPULARITY, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReOther, Response response) {
                    if (acReOther.getData().getPage().getList().size() > 1) {
                        mAdapter.onPartitionHotResult(acReOther);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        //最新发布
        if (type == TYPE_LAST_POST) {
            acPartition.onResult(AcApi.getAcPartitionUrl(mPartitionType, AcString.TIME_ORDER, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReOther, Response response) {
                    if (acReOther.getData().getPage().getList().size() > 1) {
                        mAdapter.onPartitionLastPostResult(acReOther);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
}
