package com.cnblogs.keyindex.chat.test.mock;

import com.cnblogs.keyindex.chat.net.Downloader;

import android.os.Handler;



public class MockDownloader implements Downloader {

	public boolean isCalled = false;

	

	@Override
	public void download(String url, Handler handler) {
		isCalled = true;
		
	}

}
