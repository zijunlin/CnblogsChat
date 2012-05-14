package com.cnblogs.keyindex.chat.service;

import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;

import android.os.Handler;
import android.os.Message;

public interface Authenticator {

	void handleMessage(Message msg);

	void login(Handler handler, ViewStateForms baseForms, User user);
}
