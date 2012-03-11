package com.cnblogs.keyindex.business;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ImageHttpResponseHandler;

public class ImageLoader {

	/**
	 * ����FlashMessage ������Ҫˢ�� ���Բ��� FlashMessage�л���ͼƬ
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
	 * ͷ�����أ���Adapter �� �ķ������ᱻ�ظ����á� �������ڲ��Ὺ���߳�������˶�ε��û��������߳� ��Ϊ��Ҫ����������
	 * ���账���ΪUI�߳������Դ�����Բ������߳���ֱ�ӵ��� ImageDownloaded �ӿڣ���Ҫ����Handler���ƣ�
	 * ����ÿһ���߳�����Ҫ��Ӧ������һ�� Handler ʵ�������ڴ�����߳��ڵ� ��Ϣ��
	 * 
	 * @param url
	 *            ͷ���Url��ַ
	 * @param location
	 *            λ��List�е�����
	 * @param onImageDownLoadedListener
	 *            ��ÿ�ε��ô��ݽ�������ͼƬ���غ� �ص�
	 * @return
	 */
	public void asynLoaderImage(final String url, int locationTag,
			final ImageDownLoadedListener listener) {

//		// ��������ʾ��δͶ�� ����,��Ҫ���أ�����Ѿ���������Ҫ���ظ��������ݣ��Լ���http����
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
	 * ͼƬ���ؼ����������ڴ���ͼƬ������ɺ�Ļص���
	 * 
	 * @author linzijun
	 * 
	 */
	public interface ImageDownLoadedListener {
		/**
		 * 
		 * @param imageDrawable
		 *            ���ص�ͼƬ
		 * @param location
		 *            ��ȡ����չʾͼƬ��View���Ķ�Ӧλ�á�
		 */
		public void onImageLoaded(Drawable imageDrawable, int locationTag);
	}

}
