package com.cnblogs.keyindex.chat.util;



import android.widget.EditText;

public class InputVerify {

	/**
	 * ��֤�����Ƿ�Ϊ��
	 * @param view
	 * @param errorMessage
	 * @return ���򷵻�false  ��Ϊ���򷵻�true
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
