package com.cnblogs.keyindex.chat.formatters.test;

import java.util.List;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.formatters.Formatter;
import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.model.Chat;

import android.test.AndroidTestCase;

public class ChatFormatterTest extends AndroidTestCase {

	Formatter formatter;

	protected void setUp() throws Exception {
		FormatterFactory factory = FormatterFactory.getInstance();
		factory.clear();
		String[] modelClassNames = mContext.getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = mContext.getResources().getStringArray(
				R.array.FormatterClassNames);
		factory.bindModelToFormatter(modelClassNames, formatterClassNames);
		formatter = FormatterFactory.getInstance().getFormatter(
				Chat.class.getName());
		super.setUp();
	}

	public void test_Null_Format() {
		String response = null;
		@SuppressWarnings("unchecked")
		List<Chat> list = (List<Chat>) formatter.format(response);

		assertEquals(0, list.size());
	}

	public void test_Cannot_Format() {
		String response = "teset only for can format";
		@SuppressWarnings("unchecked")
		List<Chat> list = (List<Chat>) formatter.format(response);

		assertEquals(0, list.size());
	}



//	public void test_Format()
//	{
//		String response = "teset only for can format";
//		@SuppressWarnings("unchecked")
//		List<Chat> list = (List<Chat>) formatter.format(response);
//
//		
//	}

}
