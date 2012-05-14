package com.cnblogs.keyindex.chat.service;


import android.os.Handler;
import android.os.Message;

public interface Initialization {


	void setMessageHandler(Handler handler);

	boolean isVerify();

	void handleMessage(Message msg);
}
