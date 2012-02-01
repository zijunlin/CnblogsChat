package com.cnblogs.keyindex.service;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import com.cnblogs.keyindex.model.res.Resource;
import com.cnblogs.keyindex.net.HttpHelper;
import com.cnblogs.keyindex.serializers.Serializer;
import com.cnblogs.keyindex.serializers.SerializerFactory;

public class ResourceExplorer {
	private String responseContent = null;
	private Resource responseResource=null;

	public String getResponse() {
		return responseContent;
	}
	
	public Resource getResponseResource()
	{
		return responseResource;
	}

	private String download(String uri, List<BasicNameValuePair> forms,
			CookieStore cookieStore) {

		String result=null;
		HttpHelper httpHelper = new HttpHelper();
		httpHelper.setCookieStore(cookieStore);
		if (forms == null) {

			result = httpHelper.getMethod(uri);
		} else {
			result = httpHelper.postMethod(uri, forms);
		}
		return result;

	}

	public ResourceExplorer getResource(String uri) {
		responseContent = download(uri, null, null);
		return this;
	}

	public ResourceExplorer getResource(String uri, CookieStore cookieStore) {
		responseContent = download(uri, null, cookieStore);
		return this;
	}

	public ResourceExplorer getResource(String uri,
			List<BasicNameValuePair> forms, CookieStore cookieStore) {
		responseContent = download(uri, forms, cookieStore);
		return this;
	}
	
	
	public ResourceExplorer serializerResult(String className)
	{
		Serializer format=SerializerFactory.CreateSerializer(className);
		responseResource=format.format(responseContent);
		return this;
	}

}
