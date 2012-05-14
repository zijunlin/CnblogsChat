package com.cnblogs.keyindex.chat.service;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.service.concrete.AppIniti;
import com.cnblogs.keyindex.chat.test.mock.MockDownloader;
import com.cnblogs.keyindex.chat.test.mock.MockActivityCaller;
import com.cnblogs.keyindex.chat.test.mock.MockUser;

import android.app.Application;
import android.os.Message;
import android.test.AndroidTestCase;

public class AppInitTest extends AndroidTestCase {

	AppIniti target;
	MockActivityCaller intentListener;
	MockDownloader dowanLoadListener;

	@Override
	protected void setUp() throws Exception {
		target = new AppIniti((Application) mContext.getApplicationContext());
		intentListener = new MockActivityCaller();
		dowanLoadListener = new MockDownloader();
		target.setIntentListener(intentListener);
		target.setDownloadListener(dowanLoadListener);
		
		super.setUp();
	}

	public void test_isVerify() {
		MockUser user = new MockUser(true);
		target.setCurrentUser(user);
		boolean condition = target.isVerify();
		assertTrue(condition);
		assertTrue(intentListener.isMethod2Called());
	}

	public void test_NotVerify() {
		MockUser user = new MockUser(false);
		target.setCurrentUser(user);
		boolean condition = target.isVerify();
		assertFalse(condition);
		assertTrue(dowanLoadListener.isCalled);
	}

	public void test_success_HandlerMessage() {
		Message msg = new Message();
		msg.what = R.string.msgSuccessDowloadedStateForm;
		
		msg.obj = new ViewStateForms();
		target.handleMessage(msg);
		assertTrue(intentListener.isMethod1Called());

	}

	public void test_successTwo_HandlerMessage() {
		Message msg = new Message();
		msg.what = R.string.msgSuccessDowloadedStateForm;
		target.handleMessage(msg);
		assertTrue(intentListener.isMethod1Called());

	}

	// TODO Handler  post  »Á∫Œ≤‚ ‘£ø
	// public void test_failure_HandlerMessage() {
	// Message msg = new Message();
	// msg.what = R.string.msgFailureDowloadedStateForm;
	// target.handleMessage(msg);
	// target.setRetryMillis(0);
	//
	// assertTrue(dowanLoadListener.isCalled);
	// }

}
