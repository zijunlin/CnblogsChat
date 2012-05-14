package com.cnblogs.keyindex.chat.net.test;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.net.ViewStateFormDownloader;
import com.cnblogs.keyindex.chat.test.mock.MockAsyncHttpClient;
import com.cnblogs.keyindex.chat.test.mock.MockProcessResponse;

import android.os.Handler;
import android.test.AndroidTestCase;

public class ViewStateFormDownloaderTest extends AndroidTestCase {

	ViewStateFormDownloader target;
	MockAsyncHttpClient httpClient;
	MockProcessResponse processResponse;

	protected void setUp() throws Exception {
		target = new ViewStateFormDownloader();
		httpClient = new MockAsyncHttpClient();
		processResponse = new MockProcessResponse();
		target.setHandleResponse(processResponse);
		target.setHttpClient(httpClient);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStartDown() {

		String url = "test";
		Handler handler = new Handler();

		target.download(url, handler);

		int expected = R.string.msgStartDownload;
		boolean condition = handler.hasMessages(expected);
		assertTrue(condition);

	}

	public void testSuccessDown() {
		String url = "test";
		Handler handler = new Handler();
		ViewStateForms forms = new ViewStateForms();
		forms.setViewState("view");
		processResponse.callbackeObj = forms;
		httpClient.isTestSuccess = true;
		target.download(url, handler);

		boolean condition = handler.hasMessages(
				R.string.msgSuccessDowloadedStateForm, forms);
		assertTrue(condition);
	}

	public void testSuccessDownButNoFormsValue() {
		String url = "test";
		Handler handler = new Handler();
		ViewStateForms forms = new ViewStateForms();

		processResponse.callbackeObj = forms;
		httpClient.isTestSuccess = true;
		target.download(url, handler);

		boolean condition = handler
				.hasMessages(R.string.msgFailureDowloadedStateForm);
		
		assertTrue(condition);
	}

	public void testSuccessDownButFormsNull()
	{
		String url = "test";
		Handler handler = new Handler();
		httpClient.isTestSuccess = true;
		target.download(url, handler);
		boolean condition = handler
				.hasMessages(R.string.msgFailureDowloadedStateForm);
		assertTrue(condition);
	}
	
	
	public void testFailureDown()
	{
		String url = "test";
		Handler handler = new Handler();
		httpClient.isTestSuccess = false;
		target.download(url, handler);
		boolean condition = handler
				.hasMessages(R.string.msgFailureDowloadedStateForm);
		assertTrue(condition);
	}
}
