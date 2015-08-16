package org.succlz123.AxBTube.ui.fragment.acfun.main;

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
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcRecommendRvAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.acfun.AcContentActivity;
import org.succlz123.AxBTube.ui.activity.acfun.AcPartitionActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
    private boolean mIsPrepared;
    private AcRecommendRvAdapter mAdapter;
    private AcApi.getAcRecommend acRecommend;

    @Bind(R.id.ac_fragment_recommend_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_recommend, container, false);
        ButterKnife.bind(this, view);

        //http请求
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_API_SERVER).build();
        acRecommend = restAdapter.create(AcApi.getAcRecommend.class);

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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new AcRecommendRvAdapter.MyDecoration());
        mAdapter = new AcRecommendRvAdapter();
        mAdapter.setOnClickListener(new AcRecommendRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, String partitionType, String contentId) {
                if (contentId == null) {
                    //启动分区页面
                    AcPartitionActivity.startActivity(getActivity(), partitionType);
                } else {
                    //启动视频信息页面
                    AcContentActivity.startActivity(getActivity(), contentId);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);
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
             if (mAdapter.getmAcReBanner() == null) {
                getHttpResult(AcString.BANNER);
            }
            if (mAdapter.getmAcReHot() == null) {
                getHttpResult(AcString.HOT);
            }
            if (mAdapter.getmAcReAnimation() == null) {
                getHttpResult(AcString.ANIMATION);
            }
            if (mAdapter.getmAcReFun() == null) {
                getHttpResult(AcString.FUN);
            }
            if (mAdapter.getmAcReMusic() == null) {
                getHttpResult(AcString.MUSIC);
            }
            if (mAdapter.getmAcReGame() == null) {
                getHttpResult(AcString.GAME);
            }
            if (mAdapter.getmAcReScience() == null) {
                getHttpResult(AcString.SCIENCE);
            }
            if (mAdapter.getmAcReSport() == null) {
                getHttpResult(AcString.SPORT);
            }
            if (mAdapter.getmAcReTv() == null) {
                getHttpResult(AcString.TV);
            }
        }
    }

    private void getHttpResult(String httpGetType) {
        if (TextUtils.equals(httpGetType, AcString.BANNER)) {
            //首页横幅
            acRecommend.onAcReBannerResult(AcApi.getAcReBannerUrl(), new Callback<AcReBanner>() {
                @Override
                public void success(AcReBanner acReBanner, Response response) {
                    mAdapter.onReBannerResult(acReBanner);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } else if (TextUtils.equals(httpGetType, AcString.HOT)) {
            //首页热门焦点
            acRecommend.onAcReHotResult(AcApi.getAcReHotUrl(), new Callback<AcReHot>() {
                @Override
                public void success(AcReHot acReHot, Response response) {
                    mAdapter.onAcReHotResult(acReHot);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } else if (TextUtils.equals(httpGetType, AcString.ANIMATION)) {
            //动画区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.ANIMATION, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReOther, Response response) {
                    mAdapter.onAcReAnimationResult(acReOther);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } else if (TextUtils.equals(httpGetType, AcString.FUN)) {
            //娱乐区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.FUN, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReFun, Response response) {
                    mAdapter.onAcReFunResult(acReFun);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else if (TextUtils.equals(httpGetType, AcString.MUSIC)) {
            //音乐区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.MUSIC, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReMusic, Response response) {
                    mAdapter.onAcReMusicResult(acReMusic);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else if (TextUtils.equals(httpGetType, AcString.GAME)) {
            //游戏区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.GAME, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReGame, Response response) {
                    mAdapter.onAcReGameResult(acReGame);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else if (TextUtils.equals(httpGetType, AcString.SCIENCE)) {
            //科学区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.SCIENCE, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReScience, Response response) {
                    mAdapter.onAcReScienceResult(acReScience);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else if (TextUtils.equals(httpGetType, AcString.SPORT)) {
            //体育区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.SPORT, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReSport, Response response) {
                    mAdapter.onAcReSportResult(acReSport);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else if (TextUtils.equals(httpGetType, AcString.TV)) {
            //影视区
            acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.TV, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
                @Override
                public void success(AcReOther acReTv, Response response) {
                    mAdapter.onAcReTvResult(acReTv);
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
}

