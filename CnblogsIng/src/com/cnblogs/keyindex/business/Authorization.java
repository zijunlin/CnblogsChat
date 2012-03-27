package com.cnblogs.keyindex.business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.cookie.Cookie;

import com.cnblogs.indexkey.formatter.FormatterFactory;
import com.cnblogs.indexkey.formatter.IFormatter;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.User;
import com.cnblogs.keyindex.model.ViewStateForms;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class Authorization {

	private Context mContext;
	private Handler mHandler;
	private AsyncHttpClient httpClient;
	private String url;

	private final String COOLKIE_NAME = ".DottextCookie";
	private final String USER_NAME = "username";

	public Authorization(Context context) {
		mContext = context;
		httpClient = new AsyncHttpClient();
		url = mContext.getString(R.string.urlPassport);
	}

	public boolean hasAuthorize() {
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);

		for (Cookie item : cookieStore.getCookies()) {
			if (item.getName().contains(COOLKIE_NAME)
					&& !item.isExpired(new Date())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * download the viewstate ,which the login need;
	 * 
	 * @param handler
	 */
	public void getAspDotNetForms(Handler handler) {
		mHandler = handler;
		httpClient.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				mHandler.sendEmptyMessage(R.string.msgDownload);
			}

			@Override
			public void onSuccess(String content) {
				mHandler.sendEmptyMessage(R.string.msgInitContext);
				try {
					IFormatter formatter = FormatterFactory
							.getFormatter(ViewStateForms.class.getName());
					ViewStateForms model = (ViewStateForms) formatter
							.format(content);
					if (model != null && model.getViewState() != null) {
						Message msg = new Message();
						msg.what = R.string.msgSuccessStart;
						msg.obj = model;
						mHandler.sendMessage(msg);
					} else {
						mHandler.sendEmptyMessage(R.string.msgInitError);
					}
				} catch (Exception ex) {
					Log.e("cnblogs", ex.getMessage());
					mHandler.sendEmptyMessage(R.string.msgInitError);
				}

			}

			@Override
			public void onFailure(Throwable error, String content) {
				mHandler.sendEmptyMessage(R.string.msgInitError);
			}

		});

	}

	/**
	 * sign in
	 * @param handler
	 * @param baseForms
	 * @param user
	 */
	public void Login(Handler handler, ViewStateForms baseForms, User user) {
		mHandler = handler;
		final PersistentCookieStore cookieStore = new PersistentCookieStore(
				mContext);
		httpClient.setCookieStore(cookieStore);
		RequestParams requestParams = new RequestParams(bulidForm(baseForms,
				user.getUserName(), user.getPassword()));
		httpClient.post(url, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
				mHandler.sendEmptyMessage(R.string.msgLoginError);
			}

			@Override
			public void onStart() {
				mHandler.sendEmptyMessage(R.string.msgLoginBulidForms);
			}

			@Override
			public void onSuccess(String arg0) {
				if (isSueecssedLogin(cookieStore)) {
					mHandler.sendEmptyMessage(R.string.msgLoginSuccess);
					// TODO test if the cookie has change in shareprferents;
				} else {
					mHandler.sendEmptyMessage(R.string.msgLoginError);
				}
			}

		});
	}

	private Map<String, String> bulidForm(ViewStateForms baseForms,
			String userName, String password) {
		Map<String, String> forms = new HashMap<String, String>();
		forms.putAll(baseForms.getForms());
		forms.put("btnLogin", "µÇ Â¼");
		forms.put("tbPassword", password);
		forms.put("tbUserName", userName);
		return forms;
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

	public void saveUserName(String name) {
		SharedPreferences.Editor userEditor = PreferenceManager
				.getDefaultSharedPreferences(mContext).edit();
		userEditor.putString(USER_NAME, name);
		userEditor.commit();
	}

	public String getUserName() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		return sp.getString(USER_NAME, "");
	}

}
