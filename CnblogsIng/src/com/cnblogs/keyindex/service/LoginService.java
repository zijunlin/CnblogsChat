package com.cnblogs.keyindex.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.LoginActivity;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.User;
import com.cnblogs.keyindex.response.res.AspDotNetForms;
import com.cnblogs.keyindex.response.res.LoginResult;
import com.cnblogs.keyindex.serializers.LoginResultSerializer;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.preference.PreferenceManager;

public class LoginService implements Callback {

	private Handler loginHandler;
	private final String xmlUserKey = "username";
	private LoginActivity activity;
	private ResourceExplorer re;

	public LoginService(LoginActivity userActivity) {
		activity = userActivity;
		loginHandler = new Handler(this);
	}

	public void login(String userName, String password) {
		activity.onLogining(R.string.msgLogining);
		final User user = new User();
		user.setPassword(password);
		user.setUserName(userName);
		new Thread(new Runnable() {

			@Override
			public void run() {
				authenticateUser(user);
			}
		}).start();
	}

	private void authenticateUser(User user) {
		loginHandler.sendEmptyMessage(R.string.msgLoginBulidForms);
		re = new ResourceExplorer();
		String uri = activity.getResources().getString(R.string.urlPassport);
		List<BasicNameValuePair> forms = bulidForm(user.getUserName(),
				user.getPassword());
		CookieStore cookieStore = CnblogsIngContext.getContext()
				.getCookieStore();
		loginHandler.sendEmptyMessage(R.string.msgLogining);
		re.getResource(uri, forms, cookieStore).serializerResult(
				LoginResultSerializer.class.getName());

		LoginResult loginResult = (LoginResult) re.getResponseResource();

		if (isSueecssedLogin(loginResult)) {
			loginHandler.sendEmptyMessage(R.string.msgLoginSuccess);
			CnblogsIngContext.getContext().setCookieStore(
					loginResult.getCookieStore());
		} else {
			loginHandler.sendEmptyMessage(R.string.msgLoginError);
		}
	}
	
	private boolean isSueecssedLogin(LoginResult loginResult)
	{
		if(loginResult==null)
			return false;
		CookieStore cookieStore=loginResult.getCookieStore();
		if(cookieStore==null)
			return false;
		
		if(cookieStore.getCookies().size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private List<BasicNameValuePair> bulidForm(String userName, String password) {
		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
		AspDotNetForms baseForms = (AspDotNetForms) CnblogsIngContext
				.getContext().getAspDotNetForms();
		forms.addAll(baseForms.getForms());
		forms.add(new BasicNameValuePair("btnLogin", "µÇ Â¼"));
		forms.add(new BasicNameValuePair("tbPassword", password));
		forms.add(new BasicNameValuePair("tbUserName", userName));
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
