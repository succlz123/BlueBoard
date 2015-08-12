package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContentReply;
import org.succlz123.AxBTube.support.adapter.acfun.recyclerview.AcContentReplyRvAdapter;
import org.succlz123.AxBTube.support.helper.acfun.AcApi;
import org.succlz123.AxBTube.support.helper.acfun.AcString;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
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

    @Bind(R.id.ac_fragment_content_reply_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.ac_fragment_content_reply_pro_bar)
    ProgressBar mProgressBar;

    private boolean mIsPrepared;
    private boolean mIsContentId;

    private String mContentId;
    private AcContentReplyRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_reply, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AcContentReplyRvAdapter();
//        mAdapter.setOnClickListener(new AcContentInfoRvAdapter.OnClickListener() {
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

        mIsPrepared = true;

        return view;
    }

    public void onAcContentResult(String contentId) {
        this.mContentId = contentId;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!mIsPrepared || !isVisible || mContentId == null) {
            return;
        } else {
            getHttpResult();
        }
    }

    private void getHttpResult() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AcString.URL_ACFUN_TV).build();
        AcApi.getAcContentReply acContentReply = restAdapter.create(AcApi.getAcContentReply.class);
        //评论
        acContentReply.onContentReplyResult(AcApi.getAcContentReplyUrl(mContentId, AcString.PAGE_SIZE_NUM_50, AcString.PAGE_NO_NUM_1),
                new Callback<AcContentReply>() {

                    @Override
                    public void success(AcContentReply mAcContentReply, Response response) {
                        ArrayList<AcContentReply.DataEntity.Entity> replys = new ArrayList<>();
                        List<Integer> replyIds = mAcContentReply.getData().getPage().getList();
                        HashMap<String, AcContentReply.DataEntity.Entity> replyIdMap = mAcContentReply.getData().getPage().getMap();

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

                        mAdapter.setContentReply(replys);

                        if (mProgressBar.getVisibility() == View.VISIBLE && mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}


