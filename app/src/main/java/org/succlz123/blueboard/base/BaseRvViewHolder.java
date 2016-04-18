package org.succlz123.blueboard.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by succlz123 on 15/12/20.
 */
public abstract class BaseRvViewHolder extends RecyclerView.ViewHolder {

    public BaseRvViewHolder(View itemView) {
        super(itemView);
    }

    protected <T extends View> T f(View itemView, int resId) {
        return (T) itemView.findViewById(resId);
    }
}
