package com.cnblogs.keyindex.chat.service;

import android.content.Intent;

public interface ActivityCaller {

	void startActivity(Intent intent);

	void startActivity(Intent intent, int delayMillis);

}
