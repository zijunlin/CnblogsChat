package com.cnblogs.keyindex.chat.kernel;

import java.util.List;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.google.inject.Module;

import roboguice.application.RoboApplication;

public class CnblogsChatApplication extends RoboApplication {

	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(new CnblogsChatModule());
	}

	@Override
	public void onCreate() {

		super.onCreate();
		bindingFormatter();
	}

	private void bindingFormatter() {

		String[] modelClassNames = getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = getResources().getStringArray(
				R.array.FormatterClassNames);

		FormatterFactory factory = FormatterFactory.getInstance();
		factory.bindModelToFormatter(modelClassNames, formatterClassNames);

	}
}
