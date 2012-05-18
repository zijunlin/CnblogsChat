package com.cnblogs.keyindex.chat.test.mock;

import java.util.Map;

import android.os.Handler;

import com.cnblogs.keyindex.chat.net.HttpSender;

public class MockHttpSender implements HttpSender {

	public boolean isPostMothedCalled = false;

	@Override
	public void post(Handler handler, String url, Map<String, String> forms) {

		isPostMothedCalled = true;
	}

}
