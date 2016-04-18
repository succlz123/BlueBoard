package org.succlz123.blueboard.controller.fragment.drawer;

import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by succlz123 on 2015/8/20.
 */
public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);

        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}
