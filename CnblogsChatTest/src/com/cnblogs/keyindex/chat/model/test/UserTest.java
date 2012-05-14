package com.cnblogs.keyindex.chat.model.test;

import com.cnblogs.keyindex.chat.model.User;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

public class UserTest extends AndroidTestCase {

	protected void setUp() throws Exception {

		SharedPreferences sp = this.mContext.getSharedPreferences(
				"CookiePrefsFile", 0);
		sp.edit().clear();
		sp.edit().commit();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();

		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this.mContext).edit();
		editor.clear();
		editor.commit();

		SharedPreferences sp = this.mContext.getSharedPreferences(
				"CookiePrefsFile", 0);
		sp.edit().clear();
		sp.edit().commit();
	}

	public void test_Get_Emtpy_User() {
		User user = new User();
		String name = user.getCurrentUser(this.mContext);
		assertEquals(name, "");
	}

	public void test_Can_getCurrentUser() {
		User user = new User();

		String actual = "indexKey";
		user.setUserName(actual);
		user.saveCurrentUserName(this.mContext);

		String expected = user.getCurrentUser(this.mContext);
		assertEquals(expected, actual);
	}

	public void test_Not_Authorize() {
		User user = new User();
		boolean expected = user.hasAuthorize(this.mContext);
		assertFalse(expected);
	}

//	public void test_HasAuthorize() {
//
//		SharedPreferences.Editor editor = this.mContext.getSharedPreferences(
//				"CookiePrefsFile", 0).edit();
//
//		editor.putString("names", "test1,test2");
//		editor.putString("cookie_test1", "value1");
//		editor.putString("cookie_test2", "value2");
//
//		editor.commit();
//
//		User user = new User();
//		boolean condition = user.hasAuthorize(mContext);
//		assertTrue(condition);
//	}

}
