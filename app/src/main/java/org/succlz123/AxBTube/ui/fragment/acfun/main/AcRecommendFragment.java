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
import org.succlz123.AxBTube.support.adapter.acfun.AcReRecyclerViewAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
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
//		mRecyclerView.setHasFixedSize(true);
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

		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());


		AcReRecyclerViewAdapter recyclerViewAdapter = new AcReRecyclerViewAdapter();
		recyclerViewAdapter.setOnItemClickListener(new AcReRecyclerViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				AcPartitionActivity.startActivity(getActivity(), position);
			}
		});

		RestAdapter restAdapter = new RestAdapter
				.Builder()
				.setEndpoint(AcString.URL_BASE)
				.build();
		//首页横幅
		getAcReBanner(restAdapter, recyclerViewAdapter);
		//首页热门焦点
		getAcReHot(restAdapter, recyclerViewAdapter);


		mRecyclerView.setAdapter(recyclerViewAdapter);
		mRecyclerView.addItemDecoration(new AcReRecyclerViewAdapter.MyDecoration());
		return view;
	}

	private void getAcReHot(RestAdapter restAdapter, final AcReRecyclerViewAdapter recyclerViewAdapter) {
		AcApi.getAcReHot acReHot = restAdapter.create(AcApi.getAcReHot.class);

		acReHot.onResult(AcApi.getAcReHotUrl(), new Callback<AcReHot>() {
			@Override
			public void success(AcReHot acReHot, Response response) {
				recyclerViewAdapter.onAcReHotResult(acReHot);
			}

			@Override
			public void failure(RetrofitError error) {
			}
		});
	}

	private void getAcReBanner(RestAdapter restAdapter, final AcReRecyclerViewAdapter recyclerViewAdapter) {
		AcApi.getAcReBanner acReBanner = restAdapter.create(AcApi.getAcReBanner.class);

		acReBanner.onResult(AcApi.getAcReBannerUrl(), new Callback<AcReBanner>() {
			@Override
			public void success(AcReBanner acReBanner, Response response) {
				recyclerViewAdapter.onReBannerResult(acReBanner);
			}

			@Override
			public void failure(RetrofitError error) {
			}
		});
	}

}
