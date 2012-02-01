package com.cnblogs.keyindex.adapter;

import java.util.List;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.Section;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SectionAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Section> sectionList;

	public SectionAdapter(Context context, List<Section> list) {
		inflater = LayoutInflater.from(context);
		sectionList = list;
	}

	@Override
	public int getCount() {

		return sectionList == null ? 0 : sectionList.size();
	}

	@Override
	public Object getItem(int position) {

		return sectionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;
		if (convertView == null) {
			view = new ViewHolder();
			convertView = inflater.inflate(R.layout.cnblogs_item, null);
			view.img = (ImageView) convertView
					.findViewById(R.id.imgCnblogsItem);
			view.txtTile = (TextView) convertView
					.findViewById(R.id.txtCnblogsItemTitle);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		Section model = sectionList.get(position);
		if (model != null) {
			view.img.setImageResource(model.getLogoUri());
			view.txtTile.setText(model.getName());
		}
		return convertView;
	}

	private class ViewHolder {
		public ImageView img;
		public TextView txtTile;
	}

}
