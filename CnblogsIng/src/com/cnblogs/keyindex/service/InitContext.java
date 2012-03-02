/*
 * ��ʼ�������Ļ�������Ҫ������asp.net viewstate �Ļ�ȡ
 */
package com.cnblogs.keyindex.service;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.kernel.IServiceHandler;
import com.cnblogs.keyindex.model.AspDotNetForms;
import com.cnblogs.keyindex.serializers.AspDotNetFormsSerializer;
import com.cnblogs.keyindex.serializers.Serializer;
import com.cnblogs.keyindex.serializers.SerializerFactory;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class InitContext implements Callback {

	// private StartActivity mActivity;
	private Handler mHandler;
	private String uri;
	private int delayMillis;
	private Resources res;
	private AsyncHttpClient httpClient;
	private final int retryMillis = 5000;
	private IServiceHandler contextMessage;

	public InitContext(Context context) {
		mHandler = new Handler(this);
		res = context.getResources();
		uri = res.getString(R.string.urlPassport);
		delayMillis = res.getInteger(R.integer.delayMillis);
		httpClient = new AsyncHttpClient();
	}

	public void setContextMessageHandler(IServiceHandler value) {
		contextMessage = value;
	}

	/**
	 * ����������
	 */
	public void buildContext() {
		initCnblogsContext();
		initBaseState();
	}

	/**
	 * ��ʼ��Cnblogs ������
	 */
	private void initCnblogsContext() {
		mHandler.sendEmptyMessage(R.string.msgStart);
		CnblogsIngContext.getContext();
	}

	/**
	 * ��ȡAsp.net ��View State Event��Formsֵ
	 */
	private void initBaseState() {
		httpClient.get(uri, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				mHandler.sendEmptyMessage(R.string.msgDownload);
			}

			@Override
			public void onSuccess(String result) {
				mHandler.sendEmptyMessage(R.string.msgInitContext);
				Serializer format = SerializerFactory
						.CreateSerializer(AspDotNetFormsSerializer.class
								.getName());
				AspDotNetForms model = (AspDotNetForms) format.format(result);

				if (model != null && model.getViewState() != null) {
					mHandler.sendEmptyMessage(R.string.msgSuccessStart);
					CnblogsIngContext.getContext().setBaseForms(model);
				} else {
					mHandler.sendEmptyMessage(R.string.msgInitError);
				}

			}

			@Override
			public void onFailure(Throwable arg0) {
				mHandler.sendEmptyMessage(R.string.msgInitError);
			}

		});
	}

	@Override
	public boolean handleMessage(Message msg) {

		if (contextMessage != null) {
			contextMessage.onProcessing(msg.what, res.getString(msg.what));
		}
		switch (msg.what) {
		case R.string.msgSuccessStart:
			initComplete();
			break;
		case R.string.msgInitError:
			error();
			break;
		default:
		}
		return false;
	}

	private void initComplete() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (contextMessage != null) {
					contextMessage.onSuccess();
				}
			}
		}, delayMillis);
	}

	private void error() {
		if (contextMessage != null) {
			contextMessage.onFailure();
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				initBaseState();
			}
		}, retryMillis);
	}
}
