package com.cnblogs.keyindex;

import com.cnblogs.keyindex.business.Authorization;
import com.cnblogs.keyindex.model.ViewStateForms;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity implements Callback {

	private ProgressBar pgbLoading;
	private TextView txtMessage;
	private Authorization authorize;
	private Handler mHandler;
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
		authorize = new Authorization(this.getApplicationContext());
		mHandler = new Handler(this);
		turnToIngList();
	}

	private void turnToIngList() {
		if (authorize.hasAuthorize()) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(ING_ACTION);
					startActivity(intent);
					finish();
				}
			}, delayMillis);

		} else {
			pgbLoading.setVisibility(View.VISIBLE);
			authorize.getAspDotNetForms(mHandler);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		showHandlerMessage(getString(msg.what));
		switch (msg.what) {
		case R.string.msgSuccessStart:
			onSuccess((ViewStateForms) msg.obj);
			break;
		case R.string.msgInitError:
			onFailure();
			break;
		default:
			break;
		}
		return false;
	}

	public void showHandlerMessage(String message) {
		txtMessage.setText(Html.fromHtml(message));
	}

	public void onSuccess(ViewStateForms model) {
		Intent intent = new Intent(LOGIN_ACTION);
		intent.putExtra(ViewStateForms.VIEW_STATE_KEY, model);
		startActivity(intent);
		finish();
	}

	public void onFailure() {
		pgbLoading.setVisibility(View.INVISIBLE);
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				authorize.getAspDotNetForms(mHandler);
			}
		}, retryMillis);
	}

}