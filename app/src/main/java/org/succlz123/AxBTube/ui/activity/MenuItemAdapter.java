package org.succlz123.AxBTube.ui.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.succlz123.AxBTube.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fashi on 2015/7/7.
 */
public class MenuItemAdapter extends BaseAdapter {
	private final int mIconSize;
	private LayoutInflater mInflater;
	private Context mContext;

	public MenuItemAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);//25dp
	}

	private List<LvMenuItem> mItems = new ArrayList<>(
			Arrays.asList(
					new LvMenuItem(R.drawable.arrow, "精彩视频"),
					new LvMenuItem(R.drawable.arrow, "我的收藏"),
					new LvMenuItem(R.drawable.arrow, "历史记录"),
					new LvMenuItem(R.drawable.arrow, "离线管理"),
					new LvMenuItem(R.drawable.arrow, "功能设置"),
					new LvMenuItem(),
					new LvMenuItem("分区"),
					new LvMenuItem(R.drawable.arrow, "番剧"),
					new LvMenuItem(R.drawable.arrow, "音乐"),
					new LvMenuItem(R.drawable.arrow, "音乐"),
					new LvMenuItem(R.drawable.arrow, "音乐"),
					new LvMenuItem(R.drawable.arrow, "音乐"),
					new LvMenuItem(R.drawable.arrow, "音乐")

			));

	@Override
	public int getCount() {
		return mItems.size();
	}


	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		return mItems.get(position).type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LvMenuItem item = mItems.get(position);
		switch (item.type) {
			case LvMenuItem.TYPE_NORMAL:
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.design_drawer_item, parent, false);
				}
				TextView itemView = (TextView) convertView;
				itemView.setText(item.name);
				Drawable icon = mContext.getResources().getDrawable(item.icon);
				setIconColor(icon);
				if (icon != null) {
					icon.setBounds(0, 0, mIconSize, mIconSize);
					TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, null, null);
				}

				break;
			case LvMenuItem.TYPE_NO_ICON:
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.design_drawer_item_subheader,
							parent, false);
				}
				TextView subHeader = (TextView) convertView;
				subHeader.setText(item.name);
				break;
			case LvMenuItem.TYPE_SEPARATOR:
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.design_drawer_item_separator,
							parent, false);
				}
				break;
		}

		return convertView;
	}

	public void setIconColor(Drawable icon) {
		int textColorSecondary = android.R.attr.textColorSecondary;
		TypedValue value = new TypedValue();
		if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true)) {
			return;
		}
		int baseColor = mContext.getResources().getColor(value.resourceId);
		icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
	}
}

