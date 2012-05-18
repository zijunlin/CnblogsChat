package com.cnblogs.keyindex.chat.test.mock;

import java.util.HashMap;

import android.content.Intent;

import com.cnblogs.keyindex.chat.service.ActivityCaller;

public class MockActivityCaller implements ActivityCaller {

	private HashMap<String, Boolean> actionCalled = new HashMap<String, Boolean>();

	public MockActivityCaller() {
		actionCalled.put("Method1", false);
		actionCalled.put("Method2", false);
	}

	public boolean isOneParamesStartActivityCalled() {
		return actionCalled.get("Method1");
	}

	public boolean isDelayStartActivityCalled() {
		return actionCalled.get("Method2");
	}

	@Override
	public void startActivity(Intent intent) {
		actionCalled.put("Method1", true);

	}

	@Override
	public void startActivity(Intent intent, int delayMillis) {
		actionCalled.put("Method2", true);

	}

}
