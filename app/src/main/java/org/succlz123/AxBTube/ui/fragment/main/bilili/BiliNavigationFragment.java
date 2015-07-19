package org.succlz123.AxBTube.ui.fragment.main.bilili;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;


/**
 * Created by fashi on 2015/5/2.
 */
public class BiliNavigationFragment extends Fragment {
	private View mView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_navigation, container, false);
  		return mView;
	}



}
