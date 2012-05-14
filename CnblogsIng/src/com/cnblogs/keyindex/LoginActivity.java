package com.cnblogs.keyindex;

import com.cnblogs.keyindex.business.Authorization;
import com.cnblogs.keyindex.kernel.EventActivity;
import com.cnblogs.keyindex.kernel.MessageEvent;
import com.cnblogs.keyindex.model.User;
import com.cnblogs.keyindex.model.ViewStateForms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends EventActivity implements OnClickListener
		 {

	private static final String Ing_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.view";
	private Button btnSign;
	private Button btnCancel;
	private EditText txtUserName;
	private EditText txtPassword;
	private ProgressDialog logining;
	private TextView txtMessage;
	
	private Authorization authorize;

	private ViewStateForms baseForms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_sigin);
		init();
		showCurrentUser();
		
		addEventHandler(R.string.msgSuccessStart, onViewStateComplete);
		addEventHandler(R.string.msgLoginSuccess,onSuccessLogin);
		addEventHandler(R.string.msgLoginError,onFailureLogin);
		addEventHandler(R.string.msgInitError, onFailureLogin);
	}

	private void init() {
		initViews();
		authorize = new Authorization(this.getApplicationContext());

		// if the intent will be Null?
		Intent intent = this.getIntent();
		if (intent != null) {
			baseForms = intent
					.getParcelableExtra(ViewStateForms.VIEW_STATE_KEY);
		}

	}

	private void initViews() {
		btnSign = (Button) findViewById(R.id.btnSigin);
		btnSign.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCanenl);
		btnCancel.setOnClickListener(this);
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtMessage = (TextView) findViewById(R.id.txtSiginMessage);
		setMessageShower(txtMessage);
		logining = new ProgressDialog(this);
		logining.setTitle(R.string.lblLogin);
	}

	private void showCurrentUser() {
		txtUserName.setText(authorize.getUserName());
	}

	
	
	private MessageEvent onViewStateComplete=new MessageEvent() {
		
		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			baseForms = (ViewStateForms) msgEventArg .obj;
			sigin();
		}
	};
	
	private MessageEvent onSuccessLogin=new MessageEvent() {
		
		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			authorize.saveUserName(getUserNameText());
			logining.dismiss();
			Intent intent = new Intent(Ing_ACTION);
			startActivity(intent);
			finish();
			
		}
	};
	
	private MessageEvent onFailureLogin=new MessageEvent() {
		
		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			logining.dismiss();
			txtMessage.setText(R.string.lblFaildAuthenticate);
		}
	};
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSigin:
			sigin();
			break;
		case R.id.btnCanenl:
			cancel();
			break;
		}
	}

	private void sigin() {

		if (baseForms != null) {
			if (editTextVerify(txtUserName) && editTextVerify(txtPassword)) {
				logining.show();
				User user = new User();
				user.setUserName(getUserNameText());
				user.setPassword(getPasswordText());
				authorize.Login(mHandler,baseForms,user);
			}
		} else {
			authorize.getAspDotNetForms(mHandler);
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

}
