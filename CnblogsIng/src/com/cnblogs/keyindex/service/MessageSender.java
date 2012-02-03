package com.cnblogs.keyindex.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.cnblogs.keyindex.IngSenderActivity;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;

public class MessageSender implements Callback {

	private IngSenderActivity activity;
	private Handler handler;

	public MessageSender(IngSenderActivity context) {
		activity = context;
		handler = new Handler(this);
	}

	public void send(String message, String uri) {
		sendMessage(message, uri);
	}

	private void sendMessage(final String message, final String uri) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				List<BasicNameValuePair> forms = bulidForm(message);
				CookieStore cookieStore = CnblogsIngContext.getContext()
						.getCookieStore();
				ResourceExplorer re = new ResourceExplorer();
				handler.sendEmptyMessage(R.string.msgSending);
				re.getResource(uri, forms, cookieStore);

				String result = re.getResponse().getContentHtml();
				if (result != null
						&& result.contentEquals("{\"IsSuccess\":true}")) {
					handler.sendEmptyMessage(R.string.msgSendSuccess);

				} else {
					handler.sendEmptyMessage(R.string.msgSendError);
				}

			}
		}).start();
	}

	private List<BasicNameValuePair> bulidForm(String message) {
		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
		forms.add(new BasicNameValuePair("content", message));
		forms.add(new BasicNameValuePair("publicFlag", "1"));
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
