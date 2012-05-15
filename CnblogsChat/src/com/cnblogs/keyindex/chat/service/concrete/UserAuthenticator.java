package com.cnblogs.keyindex.chat.service.concrete;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.net.HttpSender;
import com.cnblogs.keyindex.chat.service.ActivityCaller;
import com.cnblogs.keyindex.chat.service.Authenticator;
import com.google.inject.Inject;

public class UserAuthenticator implements Authenticator {

	private Context context;
	private HttpSender httpSender;
	private static final String ING_ACTION = "com.cnblogs.keyindex.ChatActivity.view";
	@Inject
	private ActivityCaller mIntentListener;

	@Inject
	public UserAuthenticator(Application context) {
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case R.string.msgLoginSuccess:
			onSueecssedLogin((String) msg.obj);
			break;
		case R.string.msgLoginError:
			break;
		}
	}

	@Override
	public void login(Handler handler, ViewStateForms stateForms, User user) {
		Map<String, String> forms = user.createPassport(stateForms);
		String url = context.getString(R.string.urlPassport);
		httpSender.post(handler, url, forms);
	}

	private void onSueecssedLogin(String userName) {
		User.saveUserName(context, userName);
		mIntentListener.startActivity(new Intent(ING_ACTION));

	}

	public void setHttpSender(HttpSender httpSender) {
		this.httpSender = httpSender;
	}

	public void setActivityCaller(ActivityCaller caller) {
		this.mIntentListener = caller;
	}
}
