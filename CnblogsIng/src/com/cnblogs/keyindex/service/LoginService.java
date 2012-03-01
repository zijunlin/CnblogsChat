package com.cnblogs.keyindex.service;

import java.util.HashMap;
import java.util.Map;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.LoginActivity;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.AspDotNetForms;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.preference.PreferenceManager;

public class LoginService implements Callback {

	private Handler loginHandler;
	private final String xmlUserKey = "username";
	private LoginActivity activity;
	private AsyncHttpClient httpClient;
	private String uri;

	public LoginService(LoginActivity userActivity) {
		activity = userActivity;
		loginHandler = new Handler(this);
		httpClient = new AsyncHttpClient();
		uri = activity.getResources().getString(R.string.urlPassport);
	}

	public void login(String userName, String password) {
		activity.showLoginingMessage(R.string.msgLogining);

		final PersistentCookieStore cookieStore = new PersistentCookieStore(
				activity);
		httpClient.setCookieStore(cookieStore);
		RequestParams requestParams = new RequestParams(bulidForm(userName,
				password));
		httpClient.post(uri, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
				loginHandler.sendEmptyMessage(R.string.msgLoginError);
			}

			@Override
			public void onStart() {
				loginHandler.sendEmptyMessage(R.string.msgLoginBulidForms);
			}

			@Override
			public void onSuccess(String arg0) {
				if (isSueecssedLogin(cookieStore)) {
					loginHandler.sendEmptyMessage(R.string.msgLoginSuccess);
					CnblogsIngContext.getContext().setCookieStore(cookieStore);
				} else {
					loginHandler.sendEmptyMessage(R.string.msgLoginError);
				}
			}

		});
	}

	private boolean isSueecssedLogin(PersistentCookieStore cookieStore) {
		if (cookieStore == null)
			return false;

		if (cookieStore.getCookies().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private Map<String, String> bulidForm(String userName, String password) {
		Map<String, String> forms = new HashMap<String, String>();
		AspDotNetForms baseForms = CnblogsIngContext.getContext()
				.getBaseForms();
		forms.putAll(baseForms.getForms());
		forms.put("btnLogin", "µÇ Â¼");
		forms.put("tbPassword", password);
		forms.put("tbUserName", userName);
		return forms;
	}

	@Override
	public boolean handleMessage(Message msg) {
		activity.showLoginingMessage(msg.what);
		switch (msg.what) {
		case R.string.msgLoginError:
			activity.authenticateFaild();
			break;
		case R.string.msgLoginSuccess:
			saveUserName(activity.getUserNameText());
			activity.authenticateSuccess();
			break;
		}
		return false;
	}

	public void saveUserName(String name) {
		SharedPreferences.Editor userEditor = PreferenceManager
				.getDefaultSharedPreferences(activity).edit();
		userEditor.putString(xmlUserKey, name);
		userEditor.commit();
	}

	public String getUserName() {
		SharedPreferences shared = PreferenceManager
				.getDefaultSharedPreferences(activity);
		return shared.getString(xmlUserKey, "");

	}

}
