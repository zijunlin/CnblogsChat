package com.cnblogs.keyindex.chat.net;

import java.util.Map;

import org.apache.http.client.CookieStore;

import com.cnblogs.keyindex.chat.R;
import com.google.inject.Inject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.app.Application;
import android.os.Handler;

public class LoginHttpSender implements HttpSender {

	private AsyncHttpClient httpClient;

	private CookieStore cookieStore;

	@Inject
	public LoginHttpSender(Application context) {
		httpClient = new AsyncHttpClient();
		cookieStore = new PersistentCookieStore(context);
		httpClient.setCookieStore(cookieStore);
	}

	@Override
	public void post(Handler handler, String url, Map<String, String> forms) {
		RequestParams requestParams = new RequestParams(forms);
		httpPost(handler, url, requestParams);
	}

	private void httpPost(final Handler handler, String url,
			RequestParams requestParams) {

		httpClient.post(url, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
				handler.sendEmptyMessage(R.string.msgLoginError);
			}

			@Override
			public void onStart() {
				handler.sendEmptyMessage(R.string.msgLoginBulidForms);
			}

			@Override
			public void onSuccess(String arg0) {

				if (isSueecssedLogin((PersistentCookieStore) cookieStore)) {
					handler.sendEmptyMessage(R.string.msgLoginSuccess);
				} else {
					handler.sendEmptyMessage(R.string.msgLoginError);
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

	public void setHttpClient(AsyncHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

}
