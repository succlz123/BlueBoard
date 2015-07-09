package org.succlz123.AxBTube.ui.activity;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import org.succlz123.AxBTube.R;

import java.util.ArrayList;

/**
 * Created by fashi on 2015/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
	private static final int NUM_OF_ITEMS = 100;
	private static final int NUM_OF_ITEMS_FEW = 3;

	protected int getActionBarSize() {
		TypedValue typedValue = new TypedValue();
		int[] textSizeAttr = new int[]{R.attr.actionBarSize};
		int indexOfAttrTextSize = 0;
		TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
		int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
		a.recycle();
		return actionBarSize;
	}

	protected int getScreenHeight() {
		return findViewById(android.R.id.content).getHeight();
	}

	public static ArrayList<String> getDummyData() {
		return getDummyData(NUM_OF_ITEMS);
	}

	public static ArrayList<String> getDummyData(int num) {
		ArrayList<String> items = new ArrayList<>();
		for (int i = 1; i <= num; i++) {
			items.add("Item " + i);
		}
		return items;
	}

	protected void setDummyData(ListView listView) {
		setDummyData(listView, NUM_OF_ITEMS);
	}

	protected void setDummyDataFew(ListView listView) {
		setDummyData(listView, NUM_OF_ITEMS_FEW);
	}

	protected void setDummyData(ListView listView, int num) {
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDummyData(num)));
	}

	protected void setDummyDataWithHeader(ListView listView, int headerHeight) {
		setDummyDataWithHeader(listView, headerHeight, NUM_OF_ITEMS);
	}

	protected void setDummyDataWithHeader(ListView listView, int headerHeight, int num) {
		View headerView = new View(this);
		headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
		headerView.setMinimumHeight(headerHeight);
		// This is required to disable header's list selector effect
		headerView.setClickable(true);
		setDummyDataWithHeader(listView, headerView, num);
	}

	protected void setDummyDataWithHeader(ListView listView, View headerView, int num) {
		listView.addHeaderView(headerView);
		setDummyData(listView, num);
	}

	protected void setDummyData(GridView gridView) {
		gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDummyData()));
	}

//	protected void setDummyData(RecyclerView recyclerView) {
//		setDummyData(recyclerView, NUM_OF_ITEMS);
//	}
//
//	protected void setDummyDataFew(RecyclerView recyclerView) {
//		setDummyData(recyclerView, NUM_OF_ITEMS_FEW);
//	}
//
//	protected void setDummyData(RecyclerView recyclerView, int num) {
//		recyclerView.setAdapter(new SimpleRecyclerAdapter(this, getDummyData(num)));
//	}
//
//	protected void setDummyDataWithHeader(RecyclerView recyclerView, int headerHeight) {
//		View headerView = new View(this);
//		headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
//		headerView.setMinimumHeight(headerHeight);
//		// This is required to disable header's list selector effect
//		headerView.setClickable(true);
//		setDummyDataWithHeader(recyclerView, headerView);
//	}
//
//	protected void setDummyDataWithHeader(RecyclerView recyclerView, View headerView) {
//		recyclerView.setAdapter(new SimpleHeaderRecyclerAdapter(this, getDummyData(), headerView));
//	}
}
