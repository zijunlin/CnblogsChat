package com.cnblogs.keyindex.business;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import android.os.Message;

import com.cnblogs.keyindex.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class MessageSenderService extends BusinessPipeline {

	private String uri;
	private String flashMessage;
	private String IngId;
	private String commentUrl;

	public void setSendMessage(String value, String template) {

		flashMessage = String.format(template, value);
		uri = mContext.getString(R.string.urlMessage);
		commentUrl=mContext.getString(R.string.urlCommentSender);
	}

	public void setMessageId(String id) {
		IngId = id;
	}

	@Override
	public void Start() {
		if (IngId == null) {
			sendMessage(flashMessage, uri);
		} else {
			try {
				sendComment(flashMessage, commentUrl, IngId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void sendMessage(final String message, final String uri) {

		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		httpClient.setCookieStore(cookieStore);
		RequestParams requestParams = new RequestParams(bulidForm(message));
		httpClient.post(uri, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
				mHandler.sendEmptyMessage(R.string.msgSendError);
			}

			@Override
			public void onStart() {
				mHandler.sendEmptyMessage(R.string.msgSending);
			}

			@Override
			public void onSuccess(String result) {
				if (result != null
						&& result.contentEquals("{\"IsSuccess\":true}")) {
					mHandler.sendEmptyMessage(R.string.msgSendSuccess);

				} else {
					mHandler.sendEmptyMessage(R.string.msgSendError);
				}
			}

		});

	}

	private void sendComment(String message, String url, String feedId)
			throws Exception {

		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		httpClient.setCookieStore(cookieStore);
		httpClient.addHeader("X_REQUESTED_WITH", "XMLHttpRequest");

		StringEntity se = new StringEntity("{ingId:" + feedId + ",content:\""
				+ message + "\",contentType:" + "10" + "}",HTTP.UTF_8);

		
		httpClient.post(mContext, url, se, "application/json; charset=utf-8",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String content) {
						if (content != null && content.contains("{\"d\":")) {
							mHandler.sendEmptyMessage(R.string.msgSendSuccess);

						} else {
							mHandler.sendEmptyMessage(R.string.msgSendError);
						}
					}

					@Override
					public void onStart() {
						mHandler.sendEmptyMessage(R.string.msgSending);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						mHandler.sendEmptyMessage(R.string.msgSendError);
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

		processing(msg.what);
		switch (msg.what) {
		case R.string.msgSendSuccess:
			success();
			break;
		case R.string.msgSendError:
			failure();
		default:
			break;
		}
		return false;
	}

}
