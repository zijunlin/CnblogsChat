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

public class MessageAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<FlashMessage> messages;

	public MessageAdapter(Context context, List<FlashMessage> list) {
		inflater = LayoutInflater.from(context);
		messages = list;
	}

	@Override
	public int getCount() {

		return messages == null ? 0 : messages.size();
	}

	@Override
	public Object getItem(int position) {

		return messages.get(position);
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
			convertView = inflater.inflate(R.layout.flash_message_item, null);
			view.author = (TextView) convertView
					.findViewById(R.id.txtFlashMsgItemAuther);
			view.content = (TextView) convertView
					.findViewById(R.id.txtFlashMsgItemContent);
			view.time = (TextView) convertView
					.findViewById(R.id.txtFlashMsgItemTime);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		FlashMessage message = messages.get(position);

		view.author.setText(message.getAuthorName());
		view.content.setText(message.getSendContent());

		String test = message.getGeneralTime();
		for (FlashMessage item : message.getCommentsMessage()) {
			test += "\n" + item.getAuthorName() + ":" + item.getSendContent()
					+ " " + item.getGeneralTime();
		}
		view.time.setText(test);

		return convertView;
	}

	private class ViewHolder {
		public TextView author;
		public TextView content;
		public TextView time;

	}

}
