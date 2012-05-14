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
import android.view.View;
import android.widget.EditText;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	public LoginActivityTest() {
		super(LoginActivity.class);
	}

	LoginActivity activity;
	Instrumentation instrumentation;
	EditText txtUserName;
	EditText txtPassword;

	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent("com.cnblogs.keyindex.UserAcitivity.sigin");
		intent.putExtra(ViewStateForms.VIEW_STATE_KEY, new ViewStateForms());

		activity = launchActivityWithIntent("com.cnblogs.keyindex.chat",
				LoginActivity.class, intent);
		instrumentation = getInstrumentation();
		txtUserName = (EditText) activity.findViewById(R.id.txtUserName);
		txtPassword = (EditText) activity.findViewById(R.id.txtPassword);

	}

	protected void tearDown() throws Exception {
		activity.finish();
		super.tearDown();
	}

	public void test_OnCreate() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				instrumentation.callActivityOnCreate(activity, null);
			}
		});
		instrumentation.waitForIdleSync();
		ProgressDialog dialog = activity.getProgressDialog();
		assertNotNull(dialog);

	}

	public void test_onResume() {
		User user = new User();
		user.setUserName("testor");
		user.saveCurrentUserName(activity);
		activity.runOnUiThread(new Runnable() {
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

	public void test_cancel_onClick() {
		final View v = new View(activity);
		v.setId(R.id.btnCanenl);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.onClick(v);
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

	public void test_noInput_Login() {
		final View v = new View(activity);
		v.setId(R.id.btnSigin);
		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.onClick(v);
			}

		});
		instrumentation.waitForIdleSync();
		boolean condition = authenticator.isLoginCalled;
		assertFalse(condition);
	}

	public void test_input_Login() {
		final View v = new View(activity);
		v.setId(R.id.btnSigin);
		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				txtPassword.setText("username");
				txtUserName.setText("password");
				activity.onClick(v);
			}

		});
		instrumentation.waitForIdleSync();
		boolean condition = authenticator.isLoginCalled;
		assertTrue(condition);
	}

	public void test_Success_handleMessage() {
		final Message msg = new Message();
		msg.what = R.string.msgLoginSuccess;
		activity.runOnUiThread(new Runnable() {

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

	public void test_handlerMessage() {
		final Message msg = new Message();
		msg.what = R.string.app_name;
		MockAuthenticator authenticator = new MockAuthenticator();
		activity.setAuthenticator(authenticator);
		activity.runOnUiThread(new Runnable() {

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
