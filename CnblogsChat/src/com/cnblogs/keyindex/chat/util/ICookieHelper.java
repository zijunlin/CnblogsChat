package com.cnblogs.keyindex.chat.util;

import org.apache.http.client.CookieStore;

public interface ICookieHelper {
	public boolean HasCookie(CookieStore cookieStore, String cookieName);

}
