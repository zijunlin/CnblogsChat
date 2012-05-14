package com.cnblogs.keyindex.kernel;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Html;
import android.widget.TextView;

public class EventActivity extends Activity {

	protected Handler mHandler;
	private TextView txtMessageShower;

	private HashMap<Integer, MessageEvent> eventList = new HashMap<Integer, MessageEvent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mHandler = new Handler(eventHandler);

	}

	private Callback eventHandler = new Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			showHandlerMessage(getString(msg.what));
			MessageEvent event = eventList.get(msg.what);
			if (event != null)
				event.EventHandler(this, msg);
			return false;
		}
	};

	protected void addEventHandler(int resMsgId, MessageEvent event) {

		eventList.put(resMsgId, event);
	}

	protected void postEventHandler(Runnable runable) {
		mHandler.post(runable);
	}

	protected void postEventHandler(Runnable runable, int delayMillis) {
		mHandler.postDelayed(runable, delayMillis);
	}

	protected void setMessageShower(TextView view) {
		txtMessageShower = view;
	}

	private void showHandlerMessage(String message) {
		if (txtMessageShower != null) {
			txtMessageShower.setText(Html.fromHtml(message));
		}
	}

}
