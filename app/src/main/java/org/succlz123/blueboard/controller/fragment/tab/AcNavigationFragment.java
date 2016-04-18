package org.succlz123.blueboard.controller.fragment.tab;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.controller.activity.acfun.AcPartitionActivity;
import org.succlz123.blueboard.base.BaseFragment;
import org.succlz123.blueboard.view.adapter.recyclerview.tab.AcNavigationRvAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by succlz123 on 2015/5/2.
 */
public class AcNavigationFragment extends BaseFragment {

    public static AcNavigationFragment newInstance() {
        AcNavigationFragment fragment = new AcNavigationFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private AcNavigationRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_navigation, container, false);

        mRecyclerView = f(view, R.id.ac_fragment_navigation_recycler_view);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 | position == 3) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new AcNavigationRvAdapter.MyDecoration());
        mAdapter = new AcNavigationRvAdapter();
        mAdapter.setOnClickListener(new AcNavigationRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, String partitionType) {
                AcPartitionActivity.startActivity(getActivity(), partitionType);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}
