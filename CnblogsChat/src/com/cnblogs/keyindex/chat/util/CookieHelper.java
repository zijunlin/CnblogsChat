package com.cnblogs.keyindex.chat.util;

import java.util.Date;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class CookieHelper implements ICookieHelper {

	@Override
	public boolean HasCookie(CookieStore cookieStore, String cookieName) {
		for (Cookie item : cookieStore.getCookies()) {
			if (item.getName().contains(cookieName)
					&& !item.isExpired(new Date())) {
				return true;
			}
		}
		return false;
	}

}
