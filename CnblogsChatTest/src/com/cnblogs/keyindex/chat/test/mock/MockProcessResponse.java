package com.cnblogs.keyindex.chat.test.mock;

import com.cnblogs.keyindex.chat.net.response.ProcessResponse;

public class MockProcessResponse implements ProcessResponse {

	public Object callbackeObj;

	@Override
	public Object processDownloaded(String respone) {
		// TODO Auto-generated method stub
		return callbackeObj;
	}

}
