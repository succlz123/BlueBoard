package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcContent;
import org.succlz123.AxBTube.support.callback.GetAcContentHttpResult;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentInfoFragment extends BaseFragment implements GetAcContentHttpResult {

    public static AcContentInfoFragment newInstance() {
        AcContentInfoFragment fragment = new AcContentInfoFragment();

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.ac_fragment_content_info_recycler_view)
    RecyclerView mRecyclerView;

    private AcContent mAcContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        AcContentInfoRecyclerViewAdapter adapter = new AcContentInfoRecyclerViewAdapter();

        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAcContentResult(AcContent acContent) {

     }

    public class AcContentInfoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int CONTENT_INFO_TITLE = 0;
        private static final int CONTENT_INFO_UP = 1;
        private static final int CONTENT_INFO_VIDEO = 2;

        private class TitleViewHolder extends RecyclerView.ViewHolder {

            public TitleViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class UpViewHolder extends RecyclerView.ViewHolder {

            public UpViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class VideoViewHolder extends RecyclerView.ViewHolder {

            public VideoViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return CONTENT_INFO_TITLE;
                case 1:
                    return CONTENT_INFO_UP;
                default:
                    return CONTENT_INFO_VIDEO;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View titleView
                    = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_title, parent, false);
            View upView
                    = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_up, parent, false);
            View ViewView
                    = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_content_info_video, parent, false);

            if (viewType == CONTENT_INFO_TITLE) {
                return new TitleViewHolder(titleView);
            } else if (viewType == CONTENT_INFO_UP) {
                return new UpViewHolder(upView);
            } else {
                return new VideoViewHolder(ViewView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TitleViewHolder) {

            } else if (holder instanceof UpViewHolder) {

            } else {

            }
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
