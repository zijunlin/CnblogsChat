package com.cnblogs.keyindex.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpHelper {

	private HttpClient httpClient;
	private HttpContext httpLocalContext;
	private CookieStore cookieStore;
	private String charSet;

	public HttpHelper() {
		httpLocalContext = new BasicHttpContext();
		charSet = HTTP.UTF_8;
	}

	public void setCookieStore(CookieStore value) {
		cookieStore = value;
		httpLocalContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	@SuppressWarnings("finally")
	public String getMethod(String url) {
		String result = null;
		httpClient = buildHttpClient();
		HttpGet httpget = new HttpGet(url);
//		pasteHeaders(httpget);
		try {
			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), charSet);
			}

		} catch (ClientProtocolException e) {
			Log.e("cnblogs", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("cnblogs", e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			closeConnection();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public String postMethod(String url, List<BasicNameValuePair> forms) {
		String result = null;
		httpClient = buildHttpClient();
		HttpPost httpPost = new HttpPost(url);
//		pasteHeaders(httpPost);
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(forms, charSet);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost,
					httpLocalContext);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), charSet);
				cookieStore = ((DefaultHttpClient) httpClient).getCookieStore();
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("cnblogs", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e("cnblogs", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("cnblogs", e.getLocalizedMessage());
			e.printStackTrace();
		}

		finally {
			closeConnection();
			return result;
		}

	}

	private HttpClient buildHttpClient() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
		HttpConnectionParams.setSoTimeout(httpParams, 20000);
		ConnManagerParams.setTimeout(httpParams, 1000);

		HttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	public void pasteHeaders(HttpUriRequest httpRequest) {
		httpRequest
				.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		httpRequest
				.addHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpRequest.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpRequest.addHeader("Accept-Encoding", "gzip, deflate");
		httpRequest.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		httpRequest.addHeader("Keep-Alive", "115");
		httpRequest.addHeader("Connection", "keep-alive");
	}

	public void pasteAjaxHeaders(HttpUriRequest httpRequest) {
		pasteHeaders(httpRequest);
		httpRequest.addHeader("Content-Type",
				" application/json; charset=utf-8");
		httpRequest.addHeader("X-Requested-With", "XMLHttpRequest");
		httpRequest.addHeader("Content-Length", "58");
		httpRequest.addHeader("Pragma", "no-cache");
		httpRequest.addHeader("Cache-Control", "no-cache");
	}

	public void closeConnection() {
		if (httpClient != null)
			httpClient.getConnectionManager().shutdown();
	}
}
