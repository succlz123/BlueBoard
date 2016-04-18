package org.succlz123.blueboard.view.adapter.recyclerview.tab;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseRvViewHolder;
import org.succlz123.blueboard.model.bean.acfun.AcEssay;
import org.succlz123.blueboard.model.utils.common.OkUtils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by succlz123 on 15/8/13.
 */
public class AcEssayRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AcEssay mAcEssay;
    private List<AcEssay.DataEntity.PageEntity.ListEntity> mEntityList = new ArrayList<>();

    public AcEssay getmAcEssay() {
        return mAcEssay;
    }

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, int position, String contentId);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setEssayInfo(AcEssay acEssay) {
        this.mAcEssay = acEssay;
        //下拉时保证重新填充
        mEntityList.clear();
        mEntityList.addAll(mAcEssay.getData().getPage().getList());
        notifyDataSetChanged();
    }

    public void addData(AcEssay acEssay) {
        mEntityList.addAll(acEssay.getData().getPage().getList());
        notifyItemInserted(getItemCount());
    }

    public class CardViewHolder extends BaseRvViewHolder {
        private TextView tvTitle;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvClick;
        private TextView tvReply;
        private CardView cardView;

        public CardViewHolder(View itemView) {
            super(itemView);
            tvTitle = f(itemView, R.id.ac_rv_essay_cv_title);
            tvName = f(itemView, R.id.ac_rv_essay_cv_name);
            tvTime = f(itemView, R.id.ac_rv_essay_cv_time);
            tvClick = f(itemView, R.id.ac_rv_essay_cv_click);
            tvReply = f(itemView, R.id.ac_rv_essay_cv_reply);
            cardView = f(itemView, R.id.ac_rv_essay_cv);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_essay, parent, false);

        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mEntityList.size() != 0) {
            final AcEssay.DataEntity.PageEntity.ListEntity entity = mEntityList.get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).tvTitle
                        .setText(entity.getTitle());
                ((CardViewHolder) holder).tvName
                        .setText(entity.getUser().getUsername());
                ((CardViewHolder) holder).tvTime
                        .setText(OkUtils.getDateToStringWithYDHM(entity.getReleaseDate()));
                ((CardViewHolder) holder).tvClick
                        .setText(MyApplication.getInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((CardViewHolder) holder).tvReply
                        .setText(MyApplication.getInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
                if (mOnClickListener != null) {
                    ((CardViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnClickListener.onClick(v, position, entity.getContentId());
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }
}
