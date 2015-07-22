package org.succlz123.AxBTube.ui.fragment;

import android.support.v4.app.Fragment;

import butterknife.ButterKnife;

/**
 * Created by fashi on 2015/7/8.
 */
public abstract class BaseFragment extends Fragment {


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}

