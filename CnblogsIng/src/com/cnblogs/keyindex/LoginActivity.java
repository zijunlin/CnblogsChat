package com.cnblogs.keyindex;

import com.cnblogs.keyindex.service.LoginService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

	private Button btnSign;
	private Button btnCancel;
	private EditText txtUserName;
	private EditText txtPassword;
	private ProgressDialog logining;
	private TextView txtMessage;
	private LoginService loginService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sigin);
		loginService = new LoginService(this);
		initViews();
	}

	private void initViews() {
		btnSign = (Button) findViewById(R.id.btnSigin);
		btnSign.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCanenl);
		btnCancel.setOnClickListener(this);
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtMessage = (TextView) findViewById(R.id.txtSiginMessage);
		logining = new ProgressDialog(this);
		logining.setTitle("登录");
	}

	@Override
	protected void onResume() {
		super.onResume();
		txtUserName.setText(loginService.getUserName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSigin:
			authenticate();
			break;
		case R.id.btnCanenl:
			cancel();
			break;
		}
	}

	private void authenticate() {
		if (editTextVerify(txtUserName)&&editTextVerify(txtPassword) ) {
			logining.show();
			loginService.login(getUserNameText(), getPasswordText());
		}

	}

	public void authenticateFaild() {
		logining.dismiss();
		txtMessage.setText("登录失败，请确认用户名或密码是否正确");
	}

	public void authenticateSuccess() {
		logining.dismiss();
		finish();
	}

	public void cancel() {
		this.finish();
	}

	public String getUserNameText() {
		return txtUserName.getText().toString();
	}

	public void setUserNameText(String value) {
		txtUserName.setText(value);
	}

	public String getPasswordText() {

		return txtPassword.getText().toString();
	}

	public void setPasswordText(String value) {
		txtPassword.setText(value);
	}

	public void showLoginingMessage(int resId) {

		logining.setMessage(getString(resId));
	}

	public boolean editTextVerify(EditText view) {
		if (view.getText().toString().length() == 0) {
			view.setError("不能为空");
			return false;
		} else {
			return true;
		}
	}
}
