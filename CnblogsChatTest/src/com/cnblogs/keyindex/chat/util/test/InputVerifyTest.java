package com.cnblogs.keyindex.chat.util.test;

import com.cnblogs.keyindex.chat.util.InputVerify;

import android.test.AndroidTestCase;
import android.widget.EditText;

public class InputVerifyTest extends AndroidTestCase {

	public void test_Empty_editTextRequestVerify() {
		EditText edit = new EditText(mContext);
		edit.setText("");
		String errorMsg = "isEmpty";
		boolean condition = InputVerify.editTextRequestVerify(edit, errorMsg);
		assertFalse(condition);
		assertEquals(edit.getError(), errorMsg);

	}

	public void test_NoEmpty_editTextRequestVerify() {
		String content = "This is not Empty";
		EditText edit = new EditText(mContext);
		edit.setText(content);
		String errorMsg = "isEmpty";
		boolean condition = InputVerify.editTextRequestVerify(edit, errorMsg);
		assertTrue(condition);
		assertEquals(edit.getText().toString(), content);
	}
}
