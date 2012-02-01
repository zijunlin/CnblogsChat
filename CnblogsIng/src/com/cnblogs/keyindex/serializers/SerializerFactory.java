package com.cnblogs.keyindex.serializers;

import android.util.Log;

public class SerializerFactory {

	@SuppressWarnings("rawtypes")
	public static Serializer CreateSerializer(String className) {
		Serializer serializer = null;
		Class c;
		try {
			c = Class.forName(className);
			serializer = (Serializer) c.newInstance();
		} catch (ClassNotFoundException e) {
			Log.e("cnblogs", e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("cnblogs", e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e("cnblogs", e.getMessage());
			e.printStackTrace();
		}

		return serializer;
	}
}
