package com.cnblogs.keyindex.chat.net;

import android.os.Handler;

public interface Downloader {


	void download(String url,Handler handler);
}