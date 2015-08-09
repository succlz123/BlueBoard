package org.succlz123.AxBTube.ui.fragment.acfun.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;

import butterknife.ButterKnife;


/**
 * Created by fashi on 2015/5/2.
 */
public class AcNavigationFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_main_navigation, container, false);
		ButterKnife.bind(this,view);





  		return view;
	}



}
