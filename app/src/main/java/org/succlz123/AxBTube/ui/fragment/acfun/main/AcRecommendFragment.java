package org.succlz123.AxBTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Bind(R.id.ac_fragment_recommend_recycler_view)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_recommend, container, false);
        ButterKnife.bind(this, view);

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

        AcRecommendRvAdapter recyclerViewAdapter = new AcRecommendRvAdapter();
        recyclerViewAdapter.setOnClickListener(new AcRecommendRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String contentId) {
                if (contentId == null) {
                    //启动分区页面
                    AcPartitionActivity.startActivity(getActivity(), position);
                } else {
                    //启动视频信息页面
                    AcContentActivity.startActivity(getActivity(), contentId);
                }
            }
        });

        //加载首页recommend数据
        getAcRecommend(recyclerViewAdapter);

        mRecyclerView.setAdapter(recyclerViewAdapter);
        mRecyclerView.addItemDecoration(new AcRecommendRvAdapter.RecommendDecoration());
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    private void getAcRecommend(final AcRecommendRvAdapter recyclerViewAdapter) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_BASE).build();

        AcApi.getAcRecommend acRecommend = restAdapter.create(AcApi.getAcRecommend.class);

        //首页横幅
        acRecommend.onAcReBannerResult(AcApi.getAcReBannerUrl(), new Callback<AcReBanner>() {
            @Override
            public void success(AcReBanner acReBanner, Response response) {
                recyclerViewAdapter.onReBannerResult(acReBanner);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        //首页热门焦点
        acRecommend.onAcReHotResult(AcApi.getAcReHotUrl(), new Callback<AcReHot>() {
            @Override
            public void success(AcReHot acReHot, Response response) {
                recyclerViewAdapter.onAcReHotResult(acReHot);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        //动画区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.ANIMATION, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReOther, Response response) {
                recyclerViewAdapter.onAcReAnimationResult(acReOther);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //娱乐区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.FUN, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReFun, Response response) {
                recyclerViewAdapter.onAcReFunResult(acReFun);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //音乐区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.MUSIC, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReMusic, Response response) {
                recyclerViewAdapter.onAcReMusicResult(acReMusic);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //游戏区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.GAME, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReGame, Response response) {
                recyclerViewAdapter.onAcReGameResult(acReGame);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        //科学区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.SCIENCE, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReScience, Response response) {
                recyclerViewAdapter.onAcReScienceResult(acReScience);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        //体育区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.SPORT, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReSport, Response response) {
                recyclerViewAdapter.onAcReSportResult(acReSport);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        //影视区
        acRecommend.onAcReOtherResult(AcApi.getAcReOtherUrl(AcString.TV, AcString.LAST_POST, AcString.ONE_WEEK), new Callback<AcReOther>() {
            @Override
            public void success(AcReOther acReTv, Response response) {
                recyclerViewAdapter.onAcReTvResult(acReTv);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}

