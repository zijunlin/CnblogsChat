package com.cnblogs.keyindex.response.res;

import org.apache.http.client.CookieStore;

public class LoginResult extends Resource {

	private CookieStore cookieStore;

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore value) {
		cookieStore = value;

	}
}
