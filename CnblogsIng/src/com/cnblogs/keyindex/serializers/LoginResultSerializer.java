package com.cnblogs.keyindex.serializers;

import com.cnblogs.keyindex.net.HttpResult;
import com.cnblogs.keyindex.response.res.LoginResult;
import com.cnblogs.keyindex.response.res.Resource;

public class LoginResultSerializer implements Serializer {

	@Override
	public Resource format(HttpResult response) {
		LoginResult result = new LoginResult();
		result.setCookieStore(response.getCookieStore());
		return result;
	}

}
