package com.cnblogs.keyindex;

import com.cnblogs.keyindex.service.InitContext;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity {

	private ProgressBar pgbLoading;
	private TextView txtMessage;
	private InitContext beginService;
	private final String MAIN_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.view";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		pgbLoading = (ProgressBar) findViewById(R.id.pgbInit);
		txtMessage = (TextView) findViewById(R.id.txtMessage);
		beginService = new InitContext(this);
		beginService.buildContext();
	}

	public void onIniting(int resId) {
		String message = getString(resId);
		txtMessage.setText(Html.fromHtml(message));
	}

	public void onSuccessInit() {
		Intent intent = new Intent(MAIN_ACTION);
		startActivity(intent);
		finish();
	}

	public void onFailureInit() {
		pgbLoading.setVisibility(View.INVISIBLE);
	}

}