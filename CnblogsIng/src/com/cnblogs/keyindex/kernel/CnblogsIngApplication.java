package com.cnblogs.keyindex.kernel;

import com.cnblogs.indexkey.formatter.FormatterFactory;
import com.cnblogs.keyindex.R;

import android.app.Application;

public class CnblogsIngApplication extends Application {

	@Override
	public void onCreate() {

		super.onCreate();
		bindingFormatter();
		setRepository();
	}

	private void setRepository() {
		// TODO Auto-generated method stub

	}

	private void bindingFormatter() {

		String[] modelClassNames = getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = getResources().getStringArray(
				R.array.FormatterClassNames);

		FormatterFactory.bindModelToFormatter(modelClassNames,
				formatterClassNames);

	}
}
