package com.cnblogs.keyindex.chat.formatters;

import java.util.HashMap;
import java.util.WeakHashMap;

import android.util.Log;

/**
 * ÐÞ¸ÄÎª µ¥Àý
 * 
 */
public class FormatterFactory {

	private static FormatterFactory instance = null;

	private FormatterFactory() {

	}

	public static FormatterFactory getInstance() {
		if (instance == null) {
			synchronized (FormatterFactory.class) {
				if (instance == null) {
					instance = new FormatterFactory();
				}
			}
		}
		return instance;
	}

	private WeakHashMap<String, Formatter> formatterCollection = new WeakHashMap<String, Formatter>();
	private HashMap<String, String> modelToFormatter = new HashMap<String, String>();

	/**
	 * Can't get Formatter before Binding, see binderModelToFormatter
	 * 
	 * @param key
	 *            Model Class name
	 * @return
	 * 
	 */
	public Formatter getFormatter(String key) {
		Formatter formatter = formatterCollection.get(key);
		if (formatter != null) {
			return formatter;
		}
		formatter = createFormatter(modelToFormatter.get(key));
		formatterCollection.put(key, formatter);
		return formatter;
	}

	@SuppressWarnings("rawtypes")
	private Formatter createFormatter(String className) {
		Formatter formatter = null;
		Class c;
		try {
			c = Class.forName(className);
			formatter = (Formatter) c.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Log.e("cnblogs", e.getMessage());

		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Log.e("cnblogs", e.getMessage());

		} catch (InstantiationException e) {
			e.printStackTrace();
			Log.e("cnblogs", e.getMessage());

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
	public void bindModelToFormatter(String modelClassName,
			String formatterClassName) {

		modelToFormatter.put(modelClassName, formatterClassName);
	}

	/**
	 * 
	 * @param modelClassNams
	 * @param formatterClassNames
	 */
	public void bindModelToFormatter(String[] modelClassNams,
			String[] formatterClassNames) {
		if (modelClassNams.length != formatterClassNames.length) {
			return;
		}
		for (int i = 0; i < modelClassNams.length; i++) {
			modelToFormatter.put(modelClassNams[i], formatterClassNames[i]);
		}
	}

	public void clear() {
		formatterCollection.clear();
		modelToFormatter.clear();
	}

	public int getBindModelCount() {

		return modelToFormatter.size();
	}

}
