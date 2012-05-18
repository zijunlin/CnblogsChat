package com.cnblogs.keyindex.chat.net.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.cookie.BasicClientCookie;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.net.LoginHttpSender;
import com.cnblogs.keyindex.chat.test.mock.MockAsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Application;
import android.os.Handler;
import android.test.AndroidTestCase;

public class LoginSenderTest extends AndroidTestCase {

	MockAsyncHttpClient httpClient;
	LoginHttpSender target;

	@Override
	protected void setUp() throws Exception {
		httpClient = new MockAsyncHttpClient();
		target = new LoginHttpSender(
				(Application) this.mContext.getApplicationContext());
		target.setHttpClient(httpClient);
		super.setUp();
	}

	public void testPostStart() {
		Handler handler = new Handler();
		String url = "";
		Map<String, String> forms = new HashMap<String, String>();
		forms.put("test1", "testValue");
		target.post(handler, url, forms);
		boolean condition = handler.hasMessages(R.string.msgLoginBulidForms);
		assertTrue(condition);
	}

	public void testPostFailure() {
		Handler handler = new Handler();
		String url = "";
		Map<String, String> forms = new HashMap<String, String>();
		forms.put("test1", "testValue");
		httpClient.isTestSuccess = false;
		target.post(handler, url, forms);
		boolean condition = handler.hasMessages(R.string.msgLoginError);
		assertTrue(condition);
	}

	public void testPostSuccess() {
		Handler handler = new Handler();
		String url = "";
		Map<String, String> forms = new HashMap<String, String>();
		forms.put("test1", "testValue");
		httpClient.isTestSuccess = true;
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		cookieStore.addCookie(new BasicClientCookie("test", "test"));
		target.setCookieStore(cookieStore);
		target.post(handler, url, forms);
		boolean condition = handler.hasMessages(R.string.msgLoginSuccess);
		assertTrue(condition);
	}

}
