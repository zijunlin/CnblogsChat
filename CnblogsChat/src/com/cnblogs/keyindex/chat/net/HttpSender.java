package com.cnblogs.keyindex.chat.net;

import java.util.Map;

import android.os.Handler;

public interface HttpSender {

	void post(Handler handler,String url, Map<String, String> forms);
}
