package org.succlz123.AxBTube.ui.fragment.acfun.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;
import org.succlz123.AxBTube.ui.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentReplyFragment extends BaseFragment {

    public static AcContentReplyFragment newInstance() {
        AcContentReplyFragment fragment = new AcContentReplyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_fragment_content_comment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
