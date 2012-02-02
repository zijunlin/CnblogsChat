package com.cnblogs.keyindex.serializers;

import com.cnblogs.keyindex.net.HttpResult;
import com.cnblogs.keyindex.response.res.Resource;

public interface Serializer {

	Resource format(HttpResult response);
}
