package com.cnblogs.keyindex.business;

import java.util.HashMap;
import java.util.Map;

import android.os.Message;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MessageSenderService extends BusinessPipeline {

	private String uri;
	private String flashMessage;

	public void setSendMessage(String value) {
		flashMessage = value;
		uri = mContext.getString(R.string.urlMessage);
	}

	@Override
	public void Start() {
		sendMessage(flashMessage, uri);

	}

	private void sendMessage(final String message, final String uri) {

		httpClient.setCookieStore(CnblogsIngContext.getContext()
				.getCookieStore());
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
