package com.cnblogs.keyindex.chat.test.mock;

import android.os.Handler;
import android.os.Message;

import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.service.Authenticator;

public class MockAuthenticator implements Authenticator {

	public boolean isLoginCalled = false;
	public boolean isHandleMessageCalled = false;

	@Override
	public void handleMessage(Message msg) {
		isHandleMessageCalled = true;

	}

	@Override
	public void login(Handler handler, ViewStateForms baseForms, User user) {
		isLoginCalled = true;
	}

}
