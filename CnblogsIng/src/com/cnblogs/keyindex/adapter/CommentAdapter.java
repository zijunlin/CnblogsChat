package com.cnblogs.keyindex.adapter;

import java.util.List;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.FlashMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<FlashMessage> comments;

	public CommentAdapter(Context context, List<FlashMessage> list) {
		inflater = LayoutInflater.from(context);
		comments = list;
	}

	@Override
	public int getCount() {

		return comments == null ? 0 : comments.size();
	}

	@Override
	public Object getItem(int position) {

		return comments.get(position);
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
			convertView = inflater.inflate(R.layout.comment_item, null);
			view.auther = (TextView) convertView
					.findViewById(R.id.txtCommentItemAuther);
			view.content = (TextView) convertView
					.findViewById(R.id.txtCommentItemContent);
			view.time = (TextView) convertView
					.findViewById(R.id.txtCommentItemTime);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		FlashMessage model = comments.get(position);
		view.auther.setText(model.getAuthorName()+":");
		view.content.setText(model.getSendContent());
		view.time.setText(model.getGeneralTime());
		return convertView;
	}

	static class ViewHolder {
		public TextView auther;
		public TextView content;
		public TextView time;

	}

}
