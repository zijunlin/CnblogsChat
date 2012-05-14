package com.cnblogs.keyindex.chat.test.mock;


import android.os.Handler;
import android.os.Message;

import com.cnblogs.keyindex.chat.service.Initialization;

public class MocInitialization implements Initialization {

	public boolean verify;

	@Override
	public boolean isVerify() {
		// TODO Auto-generated method stub
		return verify;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMessageHandler(Handler handler) {
		// TODO Auto-generated method stub
		
	}

}
