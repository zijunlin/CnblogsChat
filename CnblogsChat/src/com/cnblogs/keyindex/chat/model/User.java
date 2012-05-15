package com.cnblogs.keyindex.chat.model;

import java.util.HashMap;
import java.util.Map;

import com.cnblogs.keyindex.chat.util.CookieHelper;
import com.cnblogs.keyindex.chat.util.ICookieHelper;
import com.loopj.android.http.PersistentCookieStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class User {

	public static final String USER_NAME = "username";
	private static final String COOLKIE_NAME = ".DottextCookie";

	private String userName;
	private String password;

	private ICookieHelper cookieHelper;

	public User() {
		this(new CookieHelper());
	}

	private User(ICookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}

	public void setUserName(String value) {
		userName = value;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String value) {
		password = value;
	}

	public String getPassword() {
		return password;
	}

	public String getCurrentUser(Context context) {

		return User.getSaveUser(context);
	}

	public static String getSaveUser(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getString(USER_NAME, "");
	}

	public void saveCurrentUserName(Context context) {
		User.saveUserName(context, this.getUserName());
	}

	public static void saveUserName(Context context, String userName) {
		SharedPreferences.Editor userEditor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		userEditor.putString(USER_NAME, userName);
		userEditor.commit();
	}

	public boolean hasAuthorize(Context context) {
		PersistentCookieStore cookieStore = new PersistentCookieStore(context);

		return cookieHelper.HasCookie(cookieStore, COOLKIE_NAME);
	}

	public Map<String, String> createPassport(ViewStateForms stateForms) {
		Map<String, String> forms = new HashMap<String, String>();
		forms.putAll(stateForms.getForms());
		forms.put("btnLogin", "µÇ Â¼");
		forms.put("tbPassword", this.getPassword());
		forms.put("tbUserName", this.getUserName());
		return forms;
	}

}
