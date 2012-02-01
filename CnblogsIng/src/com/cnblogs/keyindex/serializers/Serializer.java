package com.cnblogs.keyindex.serializers;

import com.cnblogs.keyindex.model.res.Resource;

public interface Serializer {

	Resource format(String result);
}
