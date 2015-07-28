package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcPartitionFragment extends BaseFragment {

	public static AcPartitionFragment newInstance(String ids) {
		AcPartitionFragment fragment = new AcPartitionFragment();
		Bundle bundle = new Bundle();
		bundle.putString(AcString.AC_CHANNEL_IDS, ids);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Bind(R.id.ac_fragment_partition_recycler_view)
	RecyclerView mRecyclerView;

	private String ids;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_partition, container, false);
		ButterKnife.bind(this, view);
		ids = getArguments().getString(AcString.AC_CHANNEL_IDS);


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
//
//		mRecyclerView.setLayoutManager(manager);
//		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//		AcPartitionRecyclerViewAdapter adapter = new AcPartitionRecyclerViewAdapter();
////		adapter.setOnItemClickListener(new AcReRecyclerViewAdapter.OnItemClickListener() {
////			@Override
////			public void onItemClick(View view, int position) {
////				AcPartitionActivity.startActivity(getActivity(), position);
////			}
////		});
//
//
//		mRecyclerView.setAdapter(adapter);
//		mRecyclerView.addItemDecoration(new AcReRecyclerViewAdapter.MyDecoration());
		return view;
	}

	public class AcPartitionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return null;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		}

		@Override
		public int getItemCount() {
			return 0;
		}
	}
}
