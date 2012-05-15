package com.cnblogs.keyindex.chat.test;

import com.cnblogs.keyindex.chat.LoginActivity;

import com.cnblogs.keyindex.chat.model.User;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.test.mock.MockAuthenticator;
import com.cnblogs.keyindex.chat.R;

import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;

//TODO reworke 

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public LoginActivityTest() {
		super(LoginActivity.class);
	}

	Instrumentation instrumentation;
	EditText txtUserName;
	EditText txtPassword;
	Button btnLogin;
	Button btnCancel;
	LoginActivity activity;

	Intent intent;

	protected void setUp() throws Exception {
		super.setUp();
		intent = new Intent("com.cnblogs.keyindex.UserAcitivity.sigin");
		intent.putExtra(ViewStateForms.INTENT_EXTRA_KEY, new ViewStateForms());
		instrumentation = getInstrumentation();

		this.setActivityIntent(intent);
		 activity = launchActivityWithIntent("com.cnblogs.keyindex.chat",
		 LoginActivity.class, intent);
//		activity = getActivity();
		txtUserName = (EditText) activity.findViewById(R.id.txtUserName);
		txtPassword = (EditText) activity.findViewById(R.id.txtPassword);
		btnLogin = (Button) activity.findViewById(R.id.btnSigin);
		btnCancel = (Button) activity.findViewById(R.id.btnCanenl);
	}

	protected void tearDown() throws Exception {
		activity.finish();
		instrumentation.waitForIdleSync();
		super.tearDown();
	}

	@MediumTest
	public void testPerCondition() {

		assertNotNull(activity);
		assertNotNull(txtPassword);
		assertNotNull(txtUserName);
		assertNotNull(btnCancel);
		assertNotNull(btnLogin);
	}

	@MediumTest
	public void test_OnCreate() throws Throwable {
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				instrumentation.callActivityOnCreate(activity, null);
			}
		});
		instrumentation.waitForIdleSync();
		ProgressDialog dialog = activity.getProgressDialog();
		assertNotNull(dialog);
	}

	@MediumTest
	public void test_onResume() throws Throwable {
		User user = new User();
		user.setUserName("testor");
		user.saveCurrentUserName(activity);
		runTestOnUiThread(new Runnable() {
			@Override
			public void run() {
				instrumentation.callActivityOnResume(activity);
			}
		});
		instrumentation.waitForIdleSync();
		String expected = "testor";
		String actual = txtUserName.getText().toString();
		assertEquals(expected, actual);
	}

	@MediumTest
	public void test_btnCancel_onClick() throws Throwable {

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				btnCancel.performClick();
			}

		});
		instrumentation.waitForIdleSync();

		boolean condition = activity.isFinishing();
		// ËøÆÁÊ± isFinishing Îªtrue;
		KeyguardManager kgm = (KeyguardManager) activity
				.getSystemService(Context.KEYGUARD_SERVICE);
		boolean lock = kgm.inKeyguardRestrictedInputMode();
		if (!lock) {
			assertTrue(condition);
		}
	}

	@MediumTest
	public void test_noInput_Login() throws Throwable {

		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				btnLogin.performClick();
			}
		});
		instrumentation.waitForIdleSync();
		boolean condition = authenticator.isLoginCalled;
		assertFalse(condition);
	}

	@MediumTest
	public void test_input_Login() throws Throwable {
		final LoginActivity activity = getActivity();

		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				txtPassword.setText("username");
				txtUserName.setText("password");
				btnLogin.performClick();
			}

		});
		instrumentation.waitForIdleSync();
		boolean condition = authenticator.isLoginCalled;
		assertTrue(condition);
	}

	@MediumTest
	public void test_Success_handleMessage() throws Throwable {
		final LoginActivity activity = getActivity();
		final Message msg = new Message();
		msg.what = R.string.msgLoginSuccess;
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.handleMessage(msg);
			}

		});
		instrumentation.waitForIdleSync();
		ProgressDialog dialog = activity.getProgressDialog();
		boolean condition = dialog.isShowing();
		assertFalse(condition);

	}

	@MediumTest
	public void test_handlerMessage() throws Throwable {
		final LoginActivity activity = getActivity();
		final Message msg = new Message();
		msg.what = R.string.app_name;
		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.handleMessage(msg);
			}

		});
		instrumentation.waitForIdleSync();
		boolean condition = authenticator.isHandleMessageCalled;
		assertTrue(condition);

	}

}
