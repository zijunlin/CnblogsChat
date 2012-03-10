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
	 * ����FlashMessage ������Ҫˢ�� ���Բ��� FlashMessage�л���ͼƬ
	 */
	private HashMap<String, SoftReference<Drawable>> imagesCache;
	private AsyncHttpClient httpClient;
	private final int IMAGE_DOWNLOADED = 1;

	public ImageLoader() {
		httpClient = new AsyncHttpClient();
		imagesCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * ͷ�����أ���Adapter �� �ķ������ᱻ�ظ����á�
	 * �������ڲ��Ὺ���߳�������˶�ε��û��������̣߳�ÿһ���̻߳���һ����ԵĻص��ӿ���Ϊ�Ը��̴߳�����ɺ� ��Ҫ���������� ��Ϊ��Ҫ����������
	 * ���账���ΪUI�߳������Դ�����Բ������߳���ֱ�ӵ��� ImageDownloaded �ӿڣ���Ҫ����Handler���ƣ�
	 * ����ÿһ���߳�����Ҫ��Ӧ������һ�� Handler ʵ�������ڴ�����߳��ڵ� ��Ϣ��
	 * 
	 * @param url
	 *            ͷ���Url��ַ
	 * @param location
	 *            λ��List�е�����
	 * @param onImageDownLoadedListener
	 *            ��ÿ�ε��ô��ݽ�������ͼƬ���غ� �ص� ʵ����
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
		 * ÿһ���̶߳���Ҫ�ж�ӦHandler
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
				//֪ͨ��Ŀ�������
				Message msg = handler.obtainMessage(IMAGE_DOWNLOADED, image);
				msg.arg1 = locationTag;
				handler.sendMessage(msg);
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
