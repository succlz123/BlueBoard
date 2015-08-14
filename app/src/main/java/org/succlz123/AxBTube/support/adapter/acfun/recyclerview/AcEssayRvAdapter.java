package org.succlz123.AxBTube.support.adapter.acfun.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.succlz123.AxBTube.MyApplication;
import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.bean.acfun.AcEssay;
import org.succlz123.AxBTube.support.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ac_recycle_view_essay_cv_title)
        TextView tvTitle;

        @Bind(R.id.ac_recycle_view_essay_cv_name)
        TextView tvName;

        @Bind(R.id.ac_recycle_view_essay_cv_time)
        TextView tvTime;

        @Bind(R.id.ac_recycle_view_essay_cv_click)
        TextView tvClick;

        @Bind(R.id.ac_recycle_view_essay_cv_reply)
        TextView tvReply;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_recycleview_cardview_essay, parent, false);

        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mEntityList.size() != 0) {
            AcEssay.DataEntity.PageEntity.ListEntity entity = mEntityList.get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).tvTitle
                        .setText(entity.getTitle());
                ((CardViewHolder) holder).tvName
                        .setText(entity.getUser().getUsername());
                ((CardViewHolder) holder).tvTime
                        .setText(GlobalUtils.getDateToStringWithYDHM(entity.getReleaseDate()));
                ((CardViewHolder) holder).tvClick
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.click) + " " + entity.getViews());
                ((CardViewHolder) holder).tvReply
                        .setText(MyApplication.getsInstance().getApplicationContext().getString(R.string.reply) + " " + entity.getComments());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }
}
