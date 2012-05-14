package com.cnblogs.keyindex.chat.test.mock;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MockAsyncHttpClient extends AsyncHttpClient {

	public boolean isTestSuccess = false;
	public String content;
	public Throwable error;

	@Override
	public void get(String url, AsyncHttpResponseHandler responseHandler) {

		responseHandler.onStart();

		if (isTestSuccess) {
			responseHandler.onSuccess(content);
		} else {
			responseHandler.onFailure(error, content);
		}
	}

}
