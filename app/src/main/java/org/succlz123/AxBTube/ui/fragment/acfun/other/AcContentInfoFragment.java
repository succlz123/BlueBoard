package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentInfo;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcContentInfoRvAdapter;
import org.succlz123.AxBTube.support.utils.GlobalUtils;
import org.succlz123.AxBTube.support.utils.ViewUtils;
import org.succlz123.AxBTube.ui.activity.VideoPlayActivity;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentInfoFragment extends BaseFragment {

    public static AcContentInfoFragment newInstance() {
        AcContentInfoFragment fragment = new AcContentInfoFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.ac_fragment_content_reply_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsPrepared;
    private AcContentInfoRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AcContentInfoRvAdapter();
        mAdapter.setOnClickListener(new AcContentInfoRvAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType) {
                if (position == 0) {
                    GlobalUtils.showToastShort(getActivity(), "哇哈哈 " + userId);
                } else {
                    VideoPlayActivity.startActivity(getActivity(),
                            videoId,
                            danmakuId,
                            sourceId,
                            sourceType);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mSwipeRefreshLayout.setRefreshing(true);

        mIsPrepared = true;

        return view;
    }

    public void onAcContentResult(AcContentInfo acContentInfo) {
        mAdapter.setContentInfo(acContentInfo);


        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible) {
            return;
        } else {
        }
    }
}
