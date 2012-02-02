package com.cnblogs.keyindex.service;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import com.cnblogs.keyindex.net.HttpHelper;
import com.cnblogs.keyindex.net.HttpResult;
import com.cnblogs.keyindex.response.res.Resource;
import com.cnblogs.keyindex.serializers.Serializer;
import com.cnblogs.keyindex.serializers.SerializerFactory;

public class ResourceExplorer {
	private Resource serializResult = null;
	private HttpResult response = null;

	public HttpResult getResponse() {
		return response;
	}

	public Resource getResponseResource() {
		return serializResult;
	}

	private HttpResult bulidResult(String html, HttpHelper helper) {
		HttpResult result = new HttpResult();
		result.setContentHtml(html);
		result.setCookieStore(helper.getCookieStore());
		result.setResponceHead(helper.getResponseHead());
		return result;
	}

	private HttpResult download(String uri, List<BasicNameValuePair> forms,
			CookieStore cookieStore) {
		HttpResult result = new HttpResult();
		String html = null;
		HttpHelper httpHelper = new HttpHelper();
		httpHelper.setCookieStore(cookieStore);
		if (forms == null) {
			html = httpHelper.getMethod(uri);

		} else {

			html = httpHelper.postMethod(uri, forms);
		}
		result = bulidResult(html, httpHelper);
		return result;

	}

	public ResourceExplorer getResource(String uri) {
		response = download(uri, null, null);
		return this;
	}

	public ResourceExplorer getResource(String uri, CookieStore cookieStore) {
		response = download(uri, null, cookieStore);
		return this;
	}

	public ResourceExplorer getResource(String uri,
			List<BasicNameValuePair> forms, CookieStore cookieStore) {
		response = download(uri, forms, cookieStore);
		return this;
	}

	public ResourceExplorer serializerResult(String className) {
		Serializer format = SerializerFactory.CreateSerializer(className);
		serializResult = format.format(response);
		return this;
	}

}
