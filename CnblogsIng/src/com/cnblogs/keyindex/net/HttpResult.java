package com.cnblogs.keyindex.net;


import org.apache.http.Header;
import org.apache.http.client.CookieStore;

public class HttpResult {
	private String html;
	private Header[] heads;
	private CookieStore cookieStore;

	public String getContentHtml() {
		return html;
	}

	public void setContentHtml(String value) {
		html = value;
	}

	public Header[] getResponceHead() {
		return heads;
	}

	public void setResponceHead(Header[] value) {
		heads = value;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore value) {
		cookieStore = value;
	}

}
