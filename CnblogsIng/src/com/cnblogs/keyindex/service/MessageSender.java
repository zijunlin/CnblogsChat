package com.cnblogs.keyindex.service;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.cnblogs.keyindex.IngSenderActivity;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MessageSender implements Callback {

	private IngSenderActivity activity;
	private Handler handler;
	private AsyncHttpClient httpClient;

	public MessageSender(IngSenderActivity context) {
		activity = context;
		handler = new Handler(this);
		httpClient = new AsyncHttpClient();
	}

	public void send(String message, String uri) {
		sendMessage(message, uri);
	}

	private void sendMessage(final String message, final String uri) {

		httpClient.setCookieStore(CnblogsIngContext.getContext()
				.getCookieStore());
		RequestParams requestParams = new RequestParams(bulidForm(message));
		httpClient.post(uri, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
				handler.sendEmptyMessage(R.string.msgSendError);
			}

			@Override
			public void onStart() {
				handler.sendEmptyMessage(R.string.msgSending);
			}

			@Override
			public void onSuccess(String result) {
				if (result != null
						&& result.contentEquals("{\"IsSuccess\":true}")) {
					handler.sendEmptyMessage(R.string.msgSendSuccess);

				} else {
					handler.sendEmptyMessage(R.string.msgSendError);
				}
			}

		});

	}

	private Map<String, String> bulidForm(String message) {
		Map<String, String> forms = new HashMap<String, String>();
		forms.put("content", message);
		forms.put("publicFlag", "1");
		return forms;
	}

	@Override
	public boolean handleMessage(Message msg) {

		activity.showMessage(msg.what);
		switch (msg.what) {
		case R.string.msgSendSuccess:
			activity.onSuccessedSend();
			break;
		case R.string.msgSendError:
			activity.onFaildSend();
		default:
			break;
		}
		return false;
	}

}
