package com.cnblogs.keyindex.business;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ImageHttpResponseHandler;

public class ImageLoader {

	/**
	 * 由于FlashMessage 经常需要刷新 所以不再 FlashMessage中缓存图片
	 */
	private HashMap<String, SoftReference<Drawable>> imagesCache;
	private AsyncHttpClient httpClient;
	private final int IMAGE_DOWNLOADED = 1;

	public ImageLoader() {
		httpClient = new AsyncHttpClient();
		imagesCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * 头像下载，在Adapter 中 改方法将会被重复调用。
	 * 本方法内部会开启线程任务，因此多次调用会产生多个线程，每一个线程会有一个针对的回调接口作为对该线程处理完成后 所要触发的任务。 因为所要触发的任务
	 * 所需处理的为UI线程里的资源，所以不能在线程内直接调用 ImageDownloaded 接口，需要利用Handler机制，
	 * 所以每一个线程内需要对应的生成一个 Handler 实例，用于处理该线程内的 消息。
	 * 
	 * @param url
	 *            头像的Url地址
	 * @param location
	 *            位于List中的索引
	 * @param onImageDownLoadedListener
	 *            针每次调用传递进来的新图片下载后 回调 实例。
	 * @return
	 */
	public Drawable asynLoaderImage(final String url, int locationTag,
			final ImageDownLoadedListener listener) {

		if (imagesCache.containsKey(url)) {
			SoftReference<Drawable> softReference = imagesCache.get(url);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}

		/**
		 * 每一个线程都需要有对应Handler
		 */
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == IMAGE_DOWNLOADED) {
					listener.onImageLoaded((Drawable) msg.obj, msg.arg1);
				}

			}
		};
		downloadImage(url, locationTag, handler);

		return null;
	}

	private void downloadImage(final String url, final int locationTag,
			final Handler handler) {

		httpClient.get(url, new ImageHttpResponseHandler() {

			@Override
			public void onSuccess(Drawable image) {
				imagesCache.put(url, new SoftReference<Drawable>(image));
				//通知节目完成下载
				Message msg = handler.obtainMessage(IMAGE_DOWNLOADED, image);
				msg.arg1 = locationTag;
				handler.sendMessage(msg);
			}

		});
	}

	/**
	 * 图片下载监听器，用于处理图片下载完成后的回调。
	 * 
	 * @author linzijun
	 * 
	 */
	public interface ImageDownLoadedListener {
		/**
		 * 
		 * @param imageDrawable
		 *            下载的图片
		 * @param location
		 *            获取用于展示图片的View，的对应位置。
		 */
		public void onImageLoaded(Drawable imageDrawable, int locationTag);
	}

}
