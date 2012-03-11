package com.cnblogs.keyindex.adapter;

import java.util.List;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.business.ImageLoader;
import com.cnblogs.keyindex.business.ImageLoader.ImageDownLoadedListener;

import com.cnblogs.keyindex.model.FlashMessage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<FlashMessage> messages;
	private ImageLoader asynImageLoader;

	private ImageViewChangeListener imgChangeListener;

	public MessageAdapter(Context context, ImageLoader loader) {
		inflater = LayoutInflater.from(context);

		asynImageLoader = loader;
	}

	public void bindDate(List<FlashMessage> list) {
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
			convertView = inflater.inflate(R.layout.flash_message_item, null);
			view = new ViewHolder();
			findView(convertView, view);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		FlashMessage message = messages.get(position);
		bindViewData(view, message);
		bindHeaderImage(position, view.headerImage, message);
		return convertView;
	}

	/**
	 * 获取View实例
	 * 
	 * @param convertView
	 * @param view
	 */
	private void findView(View convertView, ViewHolder view) {
		view.author = (TextView) convertView
				.findViewById(R.id.txtFlashMsgItemAuther);
		view.content = (TextView) convertView
				.findViewById(R.id.txtFlashMsgItemContent);
		view.time = (TextView) convertView
				.findViewById(R.id.txtFlashMsgItemTime);
		view.headerImage = (ImageView) convertView.findViewById(R.id.imgHeader);
		view.Shining = (ImageView) convertView.findViewById(R.id.imgShining);
		view.newPerson = (ImageView) convertView
				.findViewById(R.id.imgNewPerson);
	}

	/**
	 * 绑定View 内容
	 * 
	 * @param view
	 * @param message
	 */
	private void bindViewData(ViewHolder view, FlashMessage message) {
		view.author.setText(message.getAuthorName());
		view.content.setText(message.getSendContent());
		view.time.setText(message.getGeneralTime());
		changeImageVisiblity(view.newPerson,
				message.IsNewPerson() ? View.VISIBLE : View.INVISIBLE);
		changeImageVisiblity(view.Shining, message.IsShining() ? View.VISIBLE
				: View.INVISIBLE);

	}

	private void changeImageVisiblity(ImageView imageView, int visiblity) {
		imageView.setVisibility(visiblity);
	}

	private void bindHeaderImage(final int position, ImageView imageView,
			FlashMessage message) {
		String url = message.getHeadImageUrl();
		// 用于图片下载完成时查找到该View
		imageView.setTag(position);
		// 必需先先恢复为默认图片
		imageView.setImageResource(R.drawable.sample_face);
		if (message.HasDefineHeaderImage()) {
			Drawable drawable = asynImageLoader.getImage(message
					.getHeadImageUrl());

			if (drawable != null) {
				imageView.setImageDrawable(drawable);
			} else {

				asynImageLoader
						.asynLoaderImage(url, position, downloadListener);
			}
		}
	}

	private final ImageDownLoadedListener downloadListener = new ImageDownLoadedListener() {

		@Override
		public void onImageLoaded(Drawable imageDrawable, int locationTag) {
			if (imgChangeListener != null) {
				imgChangeListener.onImageViewChange(imageDrawable, locationTag);
			}

		}
	};

	private static class ViewHolder {
		public TextView author;
		public TextView content;
		public TextView time;
		public ImageView headerImage;
		public ImageView Shining;
		public ImageView newPerson;

	}

	/**
	 * 用于监听 图片变更。刷新头像
	 * 
	 * @param listener
	 */
	public void setOnImageViewChangeListener(ImageViewChangeListener listener) {
		imgChangeListener = listener;
	}

	public interface ImageViewChangeListener {
		void onImageViewChange(Drawable imageDrawable, int location);
	}

}
