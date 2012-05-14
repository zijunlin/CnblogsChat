package com.cnblogs.keyindex.chat.formatters.test;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.formatters.Formatter;
import com.cnblogs.keyindex.chat.formatters.FormatterFactory;

import android.test.AndroidTestCase;

public class FormatterFactoryTest extends AndroidTestCase {

	@Override
	protected void setUp() throws Exception {
		FormatterFactory.getInstance().clear();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		FormatterFactory.getInstance().clear();
		super.tearDown();
	}

	public void test_sinleton() {
		FormatterFactory expected = FormatterFactory.getInstance();
		FormatterFactory actual = FormatterFactory.getInstance();
		assertEquals(expected, actual);
	}

	public void test_Empty_AddRangeFormatter() {
		FormatterFactory target = FormatterFactory.getInstance();

		target.bindModelToFormatter(new String[] {}, new String[] {});

		int expected = 0;
		int actual = target.getBindModelCount();
		assertEquals(expected, actual);

	}

	public void test_diffLenght_AddRangeFormatter() {
		FormatterFactory target = FormatterFactory.getInstance();

		String[] modelClass = new String[] { "test", "test1", "test2" };
		String[] formatter = new String[] { "test", "test1", };
		target.bindModelToFormatter(modelClass, formatter);
		int expected = 0;
		int actual = target.getBindModelCount();
		assertEquals(expected, actual);
	}

	public void test_AddRangeFormatter() {
		FormatterFactory target = FormatterFactory.getInstance();

		String[] modelClass = new String[] { "test", "test1", "test2" };
		String[] formatter = new String[] { "formatter1", "formatter2",
				"formatter3" };
		target.bindModelToFormatter(modelClass, formatter);
		int expected = 3;
		int actual = target.getBindModelCount();
		assertEquals(expected, actual);

	}

	public void test_GetFormatter() {

		String[] modelClassNames = mContext.getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = mContext.getResources().getStringArray(
				R.array.FormatterClassNames);

		FormatterFactory factory = FormatterFactory.getInstance();
		factory.bindModelToFormatter(modelClassNames, formatterClassNames);

		Formatter formatter = factory.getFormatter(modelClassNames[1]);

		assertEquals(formatter.getClass().getName(), formatterClassNames[1]);

	}
}
