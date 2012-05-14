package com.cnblogs.keyindex.chat.util;



import android.widget.EditText;

public class InputVerify {

	/**
	 * 验证输入是否为空
	 * @param view
	 * @param errorMessage
	 * @return 空则返回false  不为空则返回true
	 */
	public static boolean editTextRequestVerify(EditText view,
			String errorMessage) {
		if (view.getText().toString().length() == 0) {
			view.setError(errorMessage);
			return false;
		} else {
			return true;
		}
	}
}
