package com.cnblogs.keyindex.chat;

import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.service.Authenticator;
import com.cnblogs.keyindex.chat.util.InputVerify;
import com.google.inject.Inject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class LoginActivity extends RoboActivity implements OnClickListener,
		Callback {

	@InjectView(R.id.btnSigin)
	private Button btnSign;
	@InjectView(R.id.btnCanenl)
	private Button btnCancel;
	@InjectView(R.id.txtUserName)
	private EditText txtUserName;
	@InjectView(R.id.txtPassword)
	private EditText txtPassword;
	@InjectExtra( value="viewStateKey",optional=true)
	private ViewStateForms viewStateForm;

	private ProgressDialog pdglogining;
	private Handler handler;
	@Inject
	private Authenticator authenticator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btnSign.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		handler = new Handler(this);
		pdglogining = new ProgressDialog(this);
		pdglogining.setTitle(R.string.lblLogining);
	}

	@Override
	protected void onResume() {
		super.onResume();
		User user = new User();
		txtUserName.setText(user.getCurrentUser(this.getApplicationContext()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSigin:
			onLoginClick();
			break;
		case R.id.btnCanenl:
			onCancelClick();
			break;
		}
	}

	private void onCancelClick() {
		this.finish();
	}

	private void onLoginClick() {
		pdglogining.show();
		if (!InputVerify.editTextRequestVerify(txtUserName,
				getString(R.string.verifyNeedInputUserName)))
			return;
		if (!InputVerify.editTextRequestVerify(txtPassword,
				getString(R.string.verifyNeedInputPasswrod)))
			return;
		User user = new User();
		user.setPassword(txtPassword.getText().toString());
		user.setUserName(txtUserName.getText().toString());

		authenticator.login(handler, viewStateForm, user);
	}

	@Override
	public boolean handleMessage(Message msg) {
		pdglogining.setTitle(msg.what);
		if (msg.what == R.string.msgLoginSuccess) {
			pdglogining.dismiss();
		}

		authenticator.handleMessage(msg);
		return false;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public ProgressDialog getProgressDialog() {
		return pdglogining;
	}

	

}
