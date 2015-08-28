package org.succlz123.BuleTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.BuleTube.R;
import org.succlz123.BuleTube.support.adapter.acfun.recyclerview.AcNavigationRvAdapter;
import org.succlz123.BuleTube.ui.activity.acfun.AcPartitionActivity;
import org.succlz123.BuleTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by fashi on 2015/5/2.
 */
public class AcNavigationFragment extends BaseFragment {

    @Bind(R.id.ac_fragment_navigation_recycler_view)
    RecyclerView mRecyclerView;

    private boolean mIsPrepared;
    private AcNavigationRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_main_navigation, container, false);
        ButterKnife.bind(this, view);
        mIsPrepared = true;

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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
