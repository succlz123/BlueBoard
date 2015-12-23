package org.succlz123.blueboard.controller.base;

import com.squareup.leakcanary.RefWatcher;

import org.succlz123.blueboard.MyApplication;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by succlz123 on 2015/7/8.
 */
public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    protected boolean mIsVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected <T extends View> T f(View view, int resId) {
        return (T) view.findViewById(resId);
    }

}

