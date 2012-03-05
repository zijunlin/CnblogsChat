package com.cnblogs.keyindex.business;

import java.util.HashMap;
import java.util.Map;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.AspDotNetForms;
import com.cnblogs.keyindex.model.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;

public class LoginService extends BusinessPipeline  {

	private final String xmlUserKey = "username";
	private User user;
	private String uri;

	@Override
	public void InitPipeline(Context context) {

		super.InitPipeline(context);
		uri = context.getString(R.string.urlPassport);
	}

	@Override
	public void Start() {
		login(user.getUserName(), user.getPassword());
	}

	public void setUser(User value) {
		user = value;
	}

	private void login(String userName, String password) {
		processing(R.string.msgLogining);
		final PersistentCookieStore cookieStore = new PersistentCookieStore(
				mContext);
		httpClient.setCookieStore(cookieStore);
		RequestParams requestParams = new RequestParams(bulidForm(userName,
				password));
		httpClient.post(uri, requestParams, new AsyncHttpResponseHandler() {

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
					CnblogsIngContext.getContext().setCookieStore(cookieStore);
				} else {
					mHandler.sendEmptyMessage(R.string.msgLoginError);
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
		processing(msg.what);
		switch (msg.what) {
		case R.string.msgLoginError:
			failure();
			break;
		case R.string.msgLoginSuccess:
			saveUserName(user.getUserName());
			success();
			break;
		}
		return false;
	}

	public void saveUserName(String name) {
		SharedPreferences.Editor userEditor = PreferenceManager
				.getDefaultSharedPreferences(mContext).edit();
		userEditor.putString(xmlUserKey, name);
		userEditor.commit();
	}

	
	public String loadUserName()
	{
		SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(mContext);
		return sp.getString(xmlUserKey, "");
	}

	


}
