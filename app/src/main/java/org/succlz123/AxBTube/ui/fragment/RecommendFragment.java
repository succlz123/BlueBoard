package org.succlz123.AxBTube.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.succlz123.AxBTube.R;


/**
 * Created by fashi on 2015/5/2.
 */
public class RecommendFragment extends BaseFragment {
	private View mView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_recommend, container, false);

//		ObservableScrollView scrollView = (ObservableScrollView) mView.findViewById(R.id.scroll);
//
//
//		Fragment parentFragment = getParentFragment();
//		ViewGroup viewGroup = (ViewGroup) parentFragment.getView();
//		if (viewGroup != null) {
//			scrollView.setTouchInterceptionViewGroup((ViewGroup) viewGroup.findViewById(R.id.container));
//			if (parentFragment instanceof ObservableScrollViewCallbacks) {
//				scrollView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentFragment);
//			}
//		}


		return mView;
	}
}
