package com.cnblogs.keyindex.business;

import java.util.Date;

import org.apache.http.cookie.Cookie;

import com.cnblogs.indexkey.formatter.FormatterFactory;
import com.cnblogs.indexkey.formatter.IFormatter;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.AspDotNetForms;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class Authorization {

	private Context mContext;
	private Handler mHandler;
	private AsyncHttpClient httpClient;
	private String url;

	private final String COOLKIE_NAME = ".DottextCookie";

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
							.getFormatter(AspDotNetForms.class.getName());
					AspDotNetForms model = (AspDotNetForms) formatter
							.format(content);
					if (model != null && model.getViewState() != null) {
						mHandler.sendEmptyMessage(R.string.msgSuccessStart);
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

	public void Login() {

	}
}
