package org.succlz123.AxBTube.ui.fragment.main.acfun;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.adapter.AcReRecyclerViewAdapter;
import org.succlz123.AxBTube.support.asynctask.GetAcReAnimation;
import org.succlz123.AxBTube.support.asynctask.GetAcReBanner;
import org.succlz123.AxBTube.support.asynctask.GetAcReHot;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcRecommendFragment extends BaseFragment {
	@Bind(R.id.ac_re_recycler_view)
	RecyclerView mRecyclerView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_re, container, false);
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

		AcReRecyclerViewAdapter adapter = new AcReRecyclerViewAdapter();

		//横幅
		GetAcReBanner GetAcReBanner = new GetAcReBanner(adapter);
		GetAcReBanner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		//热门焦点
		GetAcReHot getAcReHot = new GetAcReHot(adapter);
		getAcReHot.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		//动画
		GetAcReAnimation getAcReAnimation = new GetAcReAnimation(adapter);
		getAcReAnimation.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		mRecyclerView.setAdapter(adapter);
		mRecyclerView.addItemDecoration(new AcReRecyclerViewAdapter.MyDecoration());
		return view;
	}

}
