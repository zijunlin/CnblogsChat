package com.cnblogs.keyindex.chat.service;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.service.concrete.UserAuthenticator;
import com.cnblogs.keyindex.chat.test.mock.MockActivityCaller;
import com.cnblogs.keyindex.chat.test.mock.MockHttpSender;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.test.AndroidTestCase;

public class UserAuthenticatorTest extends AndroidTestCase {

	MockActivityCaller activityCaller;
	UserAuthenticator target;

	@Override
	protected void setUp() throws Exception {
		activityCaller = new MockActivityCaller();
		target = new UserAuthenticator((Application) this.getContext()
				.getApplicationContext());
		target.setActivityCaller(activityCaller);
		super.setUp();
	}

	public void testOnSendSuccessMsg() {

		Message msg = new Message();
		msg.what = R.string.msgLoginSuccess;
		msg.obj = "username";
		target.handleMessage(msg);
		assertTrue(activityCaller.isOneParamesStartActivityCalled());
	}

	public void testLogin() {
		Handler handler = new Handler();
		ViewStateForms stateForms = new ViewStateForms();
		User user = new User();
		MockHttpSender sender = new MockHttpSender();
		target.setHttpSender(sender);
		target.login(handler, stateForms, user);
		assertTrue(sender.isPostMothedCalled);

	}

}
