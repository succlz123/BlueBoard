package org.succlz123.BlueTube.support.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class FixSwipeRefreshLayout extends SwipeRefreshLayout {
	private boolean mDisallow = false;

	public FixSwipeRefreshLayout(Context context) {
		super(context);
	}

	public FixSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean b) {
		mDisallow = b;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mDisallow = false;
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mDisallow) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
}
