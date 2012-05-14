package com.cnblogs.keyindex;

import com.cnblogs.keyindex.business.Authorization;
import com.cnblogs.keyindex.kernel.EventActivity;
import com.cnblogs.keyindex.kernel.MessageEvent;
import com.cnblogs.keyindex.model.ViewStateForms;

import android.content.Intent;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends EventActivity {

	private ProgressBar pgbLoading;
	private TextView txtMessage;
	private Authorization authorize;
	private final int delayMillis = 2000;
	private final int retryMillis = 5000;
	private static final String LOGIN_ACTION = "com.cnblogs.keyindex.UserAcitivity.sigin";
	private static final String ING_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.view";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		pgbLoading = (ProgressBar) findViewById(R.id.pgbInit);
		txtMessage = (TextView) findViewById(R.id.txtMessage);

		addEventHandler(R.string.msgSuccessStart, onSuccessStart);
		addEventHandler(R.string.msgInitError, onFailureStart);
		setMessageShower(txtMessage);

		authorize = new Authorization(this.getApplicationContext());

		verify();
	}

	private void verify() {
		if (authorize.hasAuthorize()) {
			postEventHandler(startIngActivity, delayMillis);

		} else {
			pgbLoading.setVisibility(View.VISIBLE);
			authorize.getAspDotNetForms(mHandler);
		}
	}

	private Runnable startIngActivity = new Runnable() {

		@Override
		public void run() {
			Intent intent = new Intent(ING_ACTION);
			startActivity(intent);
			finish();
		}
	};

	private MessageEvent onSuccessStart = new MessageEvent() {

		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			Intent intent = new Intent(LOGIN_ACTION);
			intent.putExtra(ViewStateForms.VIEW_STATE_KEY,
					(ViewStateForms) msgEventArg.obj);
			startActivity(intent);
			finish();

		}
	};

	private MessageEvent onFailureStart = new MessageEvent() {

		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			pgbLoading.setVisibility(View.INVISIBLE);
			postEventHandler(retryStart, retryMillis);
		}
	};

	private Runnable retryStart = new Runnable() {

		@Override
		public void run() {
			authorize.getAspDotNetForms(mHandler);
		}
	};

}