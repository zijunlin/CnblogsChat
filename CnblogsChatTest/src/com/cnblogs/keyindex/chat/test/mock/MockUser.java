package com.cnblogs.keyindex.chat.test.mock;

import android.content.Context;

import com.cnblogs.keyindex.chat.model.User;

public class MockUser extends User {
	private boolean isVerfiy;

	public MockUser(boolean isAuthentication) {
		isVerfiy = isAuthentication;
	}

	@Override
	public boolean hasAuthorize(Context context) {
		return isVerfiy;
	}

}
