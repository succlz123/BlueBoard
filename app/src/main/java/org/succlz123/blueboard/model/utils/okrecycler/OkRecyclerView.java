package org.succlz123.blueboard.model.utils.okrecycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by succlz123 on 15/12/29.
 */
public class OkRecyclerView extends RecyclerView {
    private static final String TAG = OkRecyclerView.class.getSimpleName();
    private LoadingListener mListener;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private CustomAdapter mCustomAdapter;
    private boolean mCanLoadMore = true;

    public OkRecyclerView(Context context) {
        this(context, null);
    }

    public OkRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OkRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
//        HeaderView headerView = new HeaderView(getContext());
//        mHeaderViews.add(headerView);

        FooterView footerView = new FooterView(getContext());
//        footerView.setLayoutParams(lp);
        footerView.setVisibility(GONE);
        mFooterViews.add(footerView);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
//        mCustomAdapter = new CustomAdapter(mAdapter, mHeaderViews, mFooterViews);
        super.setAdapter(mAdapter);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        LayoutManager layoutManager = getLayoutManager();
        int lastVisibleItemPosition = 0;

        // GridLayoutManager extends LinearLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] spanCount = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(spanCount);
            int max = spanCount[0];
            for (int value : spanCount) {
                if (value > max) {
                    max = value;
                }
            }
            lastVisibleItemPosition = max;
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (layoutManager.getChildCount() > 0
                && lastVisibleItemPosition + 1 >= layoutManager.getItemCount() - 1
                && layoutManager.getItemCount() > layoutManager.getChildCount()) {

//            View footView = mFootViews.get(0);
//            isLoadingData = true;
//            if (footView instanceof LoadingMoreFooter) {
//                ((LoadingMoreFooter) footView).setState(LoadingMoreFooter.STATE_LAODING);
//            } else {
//                footView.setVisibility(View.VISIBLE);
//            }
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            mFooterViews.get(0).setLayoutParams(lp);

            if (mCanLoadMore) {
                mFooterViews.get(0).setVisibility(VISIBLE);
                mListener.onLoadMore();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
//        Log.w(TAG, "onScrolled: " + dx + "/" + dy);
    }

    @Override
    public void smoothScrollBy(int dx, int dy) {
        super.smoothScrollBy(dx, dy);
    }

    private int xx;

    @Override
    public void offsetChildrenVertical(int dy) {
        super.offsetChildrenVertical(dy);
    }

    public void setFooter() {
        mCanLoadMore=false;
        mFooterViews.get(0).setVisibility(GONE);
//        mFooterViews.clear();
//        mCustomAdapter.notifyDataSetChanged();
    }

    private class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
//        private ArrayList<View> headerViews;
//        private ArrayList<View> footerViews;

        private static final int TYPE_HEADER = Integer.MIN_VALUE + 1;
        private static final int TYPE_FOOTER = Integer.MIN_VALUE + 2;
        private static final int TYPE_OTHER = Integer.MIN_VALUE;


        public CustomAdapter(RecyclerView.Adapter adapter, ArrayList<View> headerViews, ArrayList<View> footerViews) {
            super();
            this.adapter = adapter;
//            this.headerViews = headerViews;
//            this.footerViews = footerViews;
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }

            if (adapter != null) {
                if (!isHeader(position) && !isFooter(position)) {
                    return adapter.getItemViewType(position - 1);
                }
            }
            return TYPE_OTHER;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new CustomViewHolder(mHeaderViews.get(0));
            } else if (viewType == TYPE_FOOTER) {
                return new CustomViewHolder(mFooterViews.get(0));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (adapter != null) {
                if (!isHeader(position) && !isFooter(position)) {
                    adapter.onBindViewHolder(holder, position - getHeadersCount());
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFooterViews.size();
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < getHeadersCount();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - getFootersCount();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public CustomViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setOnLoadingListener(LoadingListener listener) {
        mListener = listener;
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();
    }
}
