package org.succlz123.blueboard.view.adapter.recyclerview.tab;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.api.acfun.AcString;
import org.succlz123.blueboard.model.utils.common.GlobalUtils;
import org.succlz123.blueboard.view.adapter.base.BaseRvViewHolder;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by succlz123 on 15/8/12.
 */
public class AcNavigationRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NAVIGATION_TITLE = 0;
    private static final int NAVIGATION_BUTTON = 1;

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(View view, String partitionType);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public class TitleViewHolder extends BaseRvViewHolder {
        private TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = f(itemView, R.id.ac_rv_tv_title);
        }
    }

    public class ButtonViewHolder extends BaseRvViewHolder {
        private ImageView imgButton;
        private TextView tvButton;
        private CardView cvButon;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            imgButton = f(itemView, R.id.ac_rv_navigation_img);
            tvButton = f(itemView, R.id.ac_rv_navigation_tv);
            cvButon = f(itemView, R.id.ac_rv_navigation_cv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 | position == 3) {
            return NAVIGATION_TITLE;
        } else {
            return NAVIGATION_BUTTON;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View titleView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_title, parent, false);
        View buttonView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_rv_cardview_navigation_button, parent, false);

        if (viewType == NAVIGATION_TITLE) {
            return new TitleViewHolder(titleView);
        } else if (viewType == NAVIGATION_BUTTON) {
            return new ButtonViewHolder(buttonView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleViewHolder) {
            switch (position) {
                case 0:
                    ((TitleViewHolder) holder).tvTitle.setText(AcString.TITLE_HOT_RANKING);
                    break;
                case 3:
                    ((TitleViewHolder) holder).tvTitle.setText(AcString.TITLE_PARTITION);
                    break;
            }
        } else if (holder instanceof ButtonViewHolder) {
            switch (position) {
                case 1:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.hot,
                            R.color.md_yellow_700,
                            AcString.TITLE_HOT);
                    break;
                case 2:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.ranking,
                            R.color.md_blue_700,
                            AcString.TITLE_RANKING);
                    break;
                case 4:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.animation,
                            R.color.md_blue_grey_700,
                            AcString.TITLE_ANIMATION);
                    break;
                case 5:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.fun,
                            R.color.md_cyan_700,
                            AcString.TITLE_FUN);
                    break;
                case 6:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.music,
                            R.color.md_deep_orange_700,
                            AcString.TITLE_MUSIC);
                    break;
                case 7:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.game,
                            R.color.md_deep_purple_700,
                            AcString.TITLE_GAME);
                    break;
                case 8:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.science,
                            R.color.md_green_700,
                            AcString.TITLE_SCIENCE);
                    break;
                case 9:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.sport,
                            R.color.md_grey_700,
                            AcString.TITLE_SPORT);
                    break;
                case 10:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.tv,
                            R.color.md_purple_700,
                            AcString.TITLE_TV);
                    break;
                case 11:
                    setButtonInfo(((ButtonViewHolder) holder).imgButton,
                            ((ButtonViewHolder) holder).tvButton,
                            ((ButtonViewHolder) holder).cvButon,
                            R.drawable.essay,
                            R.color.md_lime_700,
                            AcString.TITLE_ESSAY);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    private void setButtonInfo(ImageView imgButton, TextView tvButton, CardView cvButton, int drawable, int color, final String partitionType) {
        imgButton.setBackgroundResource(drawable);
        tvButton.setText(partitionType);
        cvButton.setCardBackgroundColor(MyApplication.getInstance().getResources().getColor(color));
        cvButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(v, partitionType);
            }
        });
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = GlobalUtils.dip2px(parent.getContext(), 10f);
            if (position == 1 | position == 4 | position == 6 | position == 8 | position == 10) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }
}