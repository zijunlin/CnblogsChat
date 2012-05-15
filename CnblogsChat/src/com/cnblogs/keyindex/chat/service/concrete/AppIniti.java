package com.cnblogs.keyindex.chat.service.concrete;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.net.Downloader;
import com.cnblogs.keyindex.chat.service.ActivityCaller;
import com.cnblogs.keyindex.chat.service.Initialization;

import com.google.inject.Inject;

// 抽象成接口
public class AppIniti implements Initialization {

	// 注入 IntentListener 接口
	@Inject
	private ActivityCaller mIntentListener;

	// 注入 IDowanLoadListener 接口
	@Inject
	private Downloader mDowanLoadListener;

	private Context mContext;
	private User currentUser;
	private Handler mHandler;

	private int delayMillis = 1000;
	private int retryMillis = 5000;
	
	private static final String ING_ACTION = "com.cnblogs.keyindex.ChatActivity.view";
	private static final String LOGIN_ACTION = "com.cnblogs.keyindex.chat.sigin";


	@Inject
	public AppIniti(Application context) {
		mContext = context;
		currentUser = new User();
	}

	public boolean isVerify() {
		boolean verify = currentUser.hasAuthorize(mContext);
		if (verify) {
			toChatActivity(delayMillis);
		} else {
			dowanloadStateForm();
		}
		return verify;
	}

	private void dowanloadStateForm() {
		String url = mContext.getString(R.string.urlPassport);
		mDowanLoadListener.download(url, mHandler);
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case R.string.msgSuccessDowloadedStateForm:
			toLoginActivity((ViewStateForms) msg.obj);
			break;
		case R.string.msgFailureDowloadedStateForm:
			retryDowanLoadStateForm();
			break;
		}
	}
	
	private void toLoginActivity(ViewStateForms forms) {

		Intent intent = new Intent(LOGIN_ACTION);
		intent.putExtra(ViewStateForms.INTENT_EXTRA_KEY, forms);
		mIntentListener.startActivity(intent);
	}

	private void toChatActivity(int delayMillis) {
		mIntentListener.startActivity(new Intent(ING_ACTION), delayMillis);
	}

	

	private void retryDowanLoadStateForm() {

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				dowanloadStateForm();
			}

		}, retryMillis);

	}

	public void setIntentListener(ActivityCaller mIntentListener) {
		this.mIntentListener = mIntentListener;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setDelayMillis(int delayMillis) {
		this.delayMillis = delayMillis;
	}

	public void setRetryMillis(int retryMillis) {
		this.retryMillis = retryMillis;
	}

	public void setDownloadListener(Downloader mDowanLoadListener) {
		this.mDowanLoadListener = mDowanLoadListener;
	}

	@Override
	public void setMessageHandler(Handler handler) {
		mHandler = handler;
	}

}
