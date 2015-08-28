package org.succlz123.bluetube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentReply;
import org.succlz123.bluetube.support.adapter.acfun.recyclerview.AcContentReplyRvAdapter;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.support.utils.ViewUtils;
import org.succlz123.bluetube.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentReplyFragment extends BaseFragment {

    public static AcContentReplyFragment newInstance() {
        AcContentReplyFragment fragment = new AcContentReplyFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean mIsPrepared;
    private String mContentId;
    private AcContentReplyRvAdapter mAdapter;

    @Bind(R.id.ac_fragment_content_reply_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_reply, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AcContentReplyRvAdapter();
//        mAdapter.setOnVideoPlayClickListener(new AcContentInfoRvAdapter.OnClickListener() {
//            @Override
//            public void onClick(View view, int position, String userId, String videoId, String danmakuId, String sourceId, String sourceType) {
//                if (position == 0) {
//                    GlobalUtils.showToastShort(getActivity(), "哇哈哈哈 " + userId);
//                } else {
//                    VideoPlayActivity.startActivity(getActivity(),
//                            videoId,
//                            danmakuId,
//                            sourceId,
//                            sourceType);
//                }
//            }
//        });

        mRecyclerView.setAdapter(mAdapter);
        ViewUtils.setSwipeRefreshLayoutColor(mSwipeRefreshLayout);

        mIsPrepared = true;

        return view;
    }

    public void onAcContentResult(String contentId) {
        this.mContentId = contentId;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible || mContentId == null) {
            return;
        } else {
            if (mAdapter.getmAcContentReply() == null) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getHttpResult();
                    }
                });
            }
        }
    }

    private void getHttpResult() {
        //评论
        RetrofitConfig.getAcContentReply().onContentReplyResult(AcApi.getAcContentReplyUrl(
                        mContentId,
                        AcString.PAGE_SIZE_NUM_50,
                        AcString.PAGE_NO_NUM_1),
                new Callback<AcContentReply>() {

                    @Override
                    public void success(AcContentReply acContentReply, Response response) {
                        if (getActivity() != null && !getActivity().isDestroyed()) {
                            if (acContentReply.getData().getPage().getList().size() == 0) {
                                if (!getActivity().isDestroyed()) {
                                    GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "并没有评论");
                                }
                            } else {
                                mAdapter.setContentReply(sortListReply(acContentReply));
                            }

                            if (mSwipeRefreshLayout != null) {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mSwipeRefreshLayout.setEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (getActivity() != null && !getActivity().isDestroyed()) {
                            GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络连接异常");
                            if (mSwipeRefreshLayout != null) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });
    }

    private ArrayList<AcContentReply.DataEntity.Entity> sortListReply(AcContentReply acContentReply) {
        ArrayList<AcContentReply.DataEntity.Entity> replys = new ArrayList<>();
        List<Integer> replyIds = acContentReply.getData().getPage().getList();
        HashMap<String, AcContentReply.DataEntity.Entity> replyIdMap = acContentReply.getData().getPage().getMap();

        for (Integer id : replyIds) {
            replys.add(replyIdMap.get("c" + String.valueOf(id)));
        }

        for (AcContentReply.DataEntity.Entity reply : replys) {
            AcContentReply.DataEntity.Entity currentReply = reply;
            int quoteId = currentReply.getQuoteId();
            while (quoteId != 0 && currentReply.getQuoteReply() == null) {
                AcContentReply.DataEntity.Entity quoteReply = replyIdMap.get("c" + quoteId);
                currentReply.setQuoteReply(quoteReply);
                currentReply = quoteReply;
                quoteId = currentReply.getQuoteId();
            }
        }

        return replys;
    }
}


