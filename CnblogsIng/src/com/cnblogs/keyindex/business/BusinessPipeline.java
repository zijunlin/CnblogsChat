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
	 * ��ˮ�߼����������ڲ���Ϣ�仯�ͳɹ���ʧ��ʱ�����ص�
	 * @param value
	 */
	public void setPipeLineListener(IPipelineCallback value) {
		serviceHandler = value;
	}
	

	/**
	 * ���ô˷���ǰ��������г�ʼ�������Ե���InitService�������г�ʼ��
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
