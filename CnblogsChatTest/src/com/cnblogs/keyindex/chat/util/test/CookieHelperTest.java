package com.cnblogs.keyindex.chat.util.test;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.cnblogs.keyindex.chat.util.CookieHelper;
import com.cnblogs.keyindex.chat.util.ICookieHelper;

import android.test.AndroidTestCase;

public class CookieHelperTest extends AndroidTestCase {

	public void test_NoHaveCookie() {
		ICookieHelper cookieHelper = new CookieHelper();
		CookieStore store = new BasicCookieStore();
		boolean condition = cookieHelper.HasCookie(store, "test");
		assertFalse(condition);

	}

	public void test_HaveCookie() {

		ICookieHelper cookieHelper = new CookieHelper();
		CookieStore store = new BasicCookieStore();
		store.addCookie(new BasicClientCookie("test", "testValue"));
		boolean condition = cookieHelper.HasCookie(store, "test");
		assertTrue(condition);
	}
}
