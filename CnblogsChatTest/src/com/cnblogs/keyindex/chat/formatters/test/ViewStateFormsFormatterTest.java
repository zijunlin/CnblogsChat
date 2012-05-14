package com.cnblogs.keyindex.chat.formatters.test;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.formatters.Formatter;
import com.cnblogs.keyindex.chat.model.ViewStateForms;

import android.test.AndroidTestCase;

public class ViewStateFormsFormatterTest extends AndroidTestCase {

	Formatter formatter;

	@Override
	protected void setUp() throws Exception {

		FormatterFactory factory = FormatterFactory.getInstance();
		factory.clear();
		String[] modelClassNames = mContext.getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = mContext.getResources().getStringArray(
				R.array.FormatterClassNames);

		factory.bindModelToFormatter(modelClassNames, formatterClassNames);
		formatter = FormatterFactory.getInstance().getFormatter(
				ViewStateForms.class.getName());
		super.setUp();
	}

	public void test_Null_parse() {
		String content = null;
		ViewStateForms actual = (ViewStateForms) formatter.format(content);
		assertNull(actual);
	}

	public void test_parse() {
		String content = "<div><input type=\"hidden\" name=\"__EVENTTARGET\" id=\"__EVENTTARGET\" value=\"123\" />"
				+ "<input type=\"hidden\" name=\"__EVENTARGUMENT\" id=\"__EVENTARGUMENT\" value=\"\" />"
				+ "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" "
				+ "value=\"/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt\" />"
				+ "</div>"
				+ " name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"/wEWBQLWwpqPDQLyj/OQAgK3jsrkBALR55GJDgKC3IeGDE1m7t2mGlasoP1Hd9hLaFoI2G05\" />";
		ViewStateForms actual = (ViewStateForms) formatter.format(content);
		assertNotNull(actual);
		assertEquals(
				"/wEWBQLWwpqPDQLyj/OQAgK3jsrkBALR55GJDgKC3IeGDE1m7t2mGlasoP1Hd9hLaFoI2G05",
				actual.getEvent());
		assertEquals(
				"/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt",
				actual.getViewState());

	}

	public void test_canParse_parse() {
		String content = "sdfwegwegwe";
		ViewStateForms actual = (ViewStateForms) formatter.format(content);
		assertNull(actual);
	}

}
