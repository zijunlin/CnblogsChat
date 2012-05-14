package com.cnblogs.keyindex.chat;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.cnblogs.keyindex.chat.service.Initialization;
import com.google.inject.Inject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class StartActivity extends RoboActivity implements Callback {

	@InjectView(R.id.pgbInit)
	private ProgressBar progessBarLoader;
	@InjectView(R.id.txtMessage)
	private TextView txtBgMessage;
	@Inject
	private Initialization authenticator;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		authenticator.setMessageHandler(new Handler(this));
		init(progessBarLoader);
	}

	public void init(View loading) {
		boolean verify = authenticator.isVerify();
		loading.setVisibility(verify ? View.INVISIBLE : View.VISIBLE);
	}

	/**
	 * 根据消息 修改TxtView 和 pgbView 的状态
	 */
	@Override
	public boolean handleMessage(Message msg) {
		setInitMessage(getString(msg.what));
		if (msg.what == R.string.msgFailureDowloadedStateForm) {
			progessBarLoader.setVisibility(View.VISIBLE);
		}
		if (msg.what == R.string.msgSuccessDowloadedStateForm) {
			progessBarLoader.setVisibility(View.INVISIBLE);
		}
		authenticator.handleMessage(msg);
		return false;
	}

	private void setInitMessage(String message) {
		if (txtBgMessage != null)
			txtBgMessage.setText(Html.fromHtml(message));
	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}

	public void setAuthenticator(Initialization authenticator) {
		this.authenticator = authenticator;
	}

}