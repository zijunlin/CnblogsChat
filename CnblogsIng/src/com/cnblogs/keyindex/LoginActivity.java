package com.cnblogs.keyindex;

import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.LoginService;
import com.cnblogs.keyindex.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener,
		IPipelineCallback {

	private Button btnSign;
	private Button btnCancel;
	private EditText txtUserName;
	private EditText txtPassword;
	private ProgressDialog logining;
	private TextView txtMessage;
	private LoginService Signer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sigin);
		Signer = new LoginService();
		Signer.InitPipeline(this);
		Signer.setPipeLineListener(this);
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
		logining.setTitle(R.string.lblLogin);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String userName = Signer.loadUserName();
		txtUserName.setText(userName);
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
		if (editTextVerify(txtUserName) && editTextVerify(txtPassword)) {
			logining.show();

			User user = new User();
			user.setUserName(getUserNameText());
			user.setPassword(getPasswordText());
			Signer.setUser(user);
			Signer.Start();

		}

	}

	private String getPasswordText() {

		return txtPassword.getText().toString();
	}

	private String getUserNameText() {

		return txtUserName.getText().toString();
	}

	public void cancel() {
		this.finish();
	}

	public boolean editTextVerify(EditText view) {
		if (view.getText().toString().length() == 0) {
			view.setError(getString(R.string.lblNeedInput));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onMaking(int messageId, String message) {
		txtMessage.setText(messageId);

	}

	@Override
	public void onSuccess(BusinessPipeline context) {
		logining.dismiss();
		finish();
	}

	@Override
	public void onFailure(BusinessPipeline context) {
		logining.dismiss();
		txtMessage.setText(R.string.lblFaildAuthenticate);
	}

}
