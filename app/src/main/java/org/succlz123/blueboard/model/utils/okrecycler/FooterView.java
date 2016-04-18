package org.succlz123.blueboard.model.utils.okrecycler;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.model.utils.common.OkUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by succlz123 on 15/12/30.
 */
public class FooterView extends LinearLayout {

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, OkUtils.dp2px(getContext(), 70));
        setLayoutParams(lp);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(R.layout.wave_view, this, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
