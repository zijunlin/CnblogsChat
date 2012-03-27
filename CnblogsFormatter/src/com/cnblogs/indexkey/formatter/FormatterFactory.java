package com.cnblogs.indexkey.formatter;

import java.util.WeakHashMap;

import android.util.Log;

public class FormatterFactory {

	private static WeakHashMap<String, IFormatter> formatterCollection = new WeakHashMap<String, IFormatter>();
	private static WeakHashMap<String, String> modelToFormatter = new WeakHashMap<String, String>();

	/**
	 * Can't get Formatter before Binding, see binderModelToFormatter
	 * 
	 * @param key
	 *            Model Class name
	 * @return
	 * 
	 */
	public static IFormatter getFormatter(String key) {

		IFormatter formatter = formatterCollection.get(key);
		if (formatter != null) {
			return formatter;
		}

		formatter = createFormatter(modelToFormatter.get(key));
		formatterCollection.put(key, formatter);

		return formatter;
	}

	@SuppressWarnings("rawtypes")
	private static IFormatter createFormatter(String className) {
		IFormatter formatter = null;
		Class c;
		try {
			c = Class.forName(className);
			formatter = (IFormatter) c.newInstance();
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

		return formatter;
	}

	/**
	 * 
	 * @param modelClassName
	 *            the Model Class Name who need to Format
	 * @param formatterClassName
	 *            the Formatter extends IFormatter
	 */
	public static void binderModelToFormatter(String modelClassName,
			String formatterClassName) {

		modelToFormatter.put(modelClassName, formatterClassName);
	}
}
