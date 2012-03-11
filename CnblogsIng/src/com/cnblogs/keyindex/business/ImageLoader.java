package com.cnblogs.keyindex.business;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ImageHttpResponseHandler;

public class ImageLoader {

	/**
	 * 由于FlashMessage 经常需要刷新 所以不再 FlashMessage中缓存图片
	 */
	private HashMap<String, SoftReference<Drawable>> imagesCache;
	private AsyncHttpClient httpClient;
	private final Handler handler = new Handler();

	public ImageLoader() {
		httpClient = new AsyncHttpClient();
		imagesCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable getImage(String key) {
		Drawable drawable = null;
		if (imagesCache.containsKey(key)) {
			SoftReference<Drawable> softReference = imagesCache.get(key);
			if (softReference != null)
				drawable = softReference.get();
		}
		return drawable;

	}

	/**
	 * 头像下载，在Adapter 中 改方法将会被重复调用。 本方法内部会开启线程任务，因此多次调用会产生多个线程 因为所要触发的任务
	 * 所需处理的为UI线程里的资源，所以不能在线程内直接调用 ImageDownloaded 接口，需要利用Handler机制，
	 * 所以每一个线程内需要对应的生成一个 Handler 实例，用于处理该线程内的 消息。
	 * 
	 * @param url
	 *            头像的Url地址
	 * @param location
	 *            位于List中的索引
	 * @param onImageDownLoadedListener
	 *            针每次调用传递进来的新图片下载后 回调
	 * @return
	 */
	public void asynLoaderImage(final String url, int locationTag,
			final ImageDownLoadedListener listener) {

//		// 不包含表示尚未投递 下载,需要下载，如果已经下载则不需要在重复下载数据，以减少http请求
//		if (!imagesCache.containsKey(url)) {
//			imagesCache.put(url, null);
			downloadImage(url, locationTag, listener);
//		} 

	}

	private void downloadImage(final String url, final int locationTag,
			final ImageDownLoadedListener listener) {

		httpClient.get(url, new ImageHttpResponseHandler() {

			@Override
			public void onSuccess(final Drawable image) {
				imagesCache.put(url, new SoftReference<Drawable>(image));
				handler.post(new Runnable() {

					@Override
					public void run() {
						if (listener != null) {
							listener.onImageLoaded(image, locationTag);
						}
					}
				});
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
