package com.cnblogs.keyindex.business;

import com.loopj.android.http.AsyncHttpClient;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;

public abstract class BusinessPipeline implements Callback {

	protected Context mContext;
	protected IPipelineCallback serviceHandler;
	protected Handler mHandler;
	protected AsyncHttpClient httpClient;

	public void InitPipeline(Context context) {
		mContext = context;
		mHandler = new Handler(this);
		httpClient = new AsyncHttpClient();
	}

	/**
	 * 流水线监听器，在内部消息变化和成功，失败时触发回调
	 * @param value
	 */
	public void setPipeLineListener(IPipelineCallback value) {
		serviceHandler = value;
	}
	

	/**
	 * 调用此方法前，必需进行初始化，可以调用InitService（）进行初始化
	 */
	public abstract void Start();

	protected void processing(int msgResId) {
		if (serviceHandler != null) {
			serviceHandler.onMaking(msgResId, mContext.getString(msgResId));
		}
	}

	protected void success() {
		if (serviceHandler != null) {
			serviceHandler.onSuccess(this);
		}
	}

	protected void failure() {
		if (serviceHandler != null) {
			serviceHandler.onFailure(this);
		}
	}

}
