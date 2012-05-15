package com.cnblogs.keyindex.chat.test;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.StartActivity;
import com.cnblogs.keyindex.chat.service.Initialization;
import com.cnblogs.keyindex.chat.test.mock.MocInitialization;

import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivityTest extends
		ActivityInstrumentationTestCase2<StartActivity> {

	public StartActivityTest() {
		super(StartActivity.class);
	}

	StartActivity activity;
	Instrumentation mInstrumentation;
	private ProgressBar pgbLoading;
	private TextView txtMessage;

	@Override
	protected void setUp() throws Exception {

		activity = getActivity();
		mInstrumentation = this.getInstrumentation();
		pgbLoading = (ProgressBar) activity.findViewById(R.id.pgbInit);
		txtMessage = (TextView) activity.findViewById(R.id.txtMessage);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		getActivity().finish();
	
		mInstrumentation.waitForIdleSync();
		super.tearDown();
	}

	public void test_OnCreate() {
		Initialization mockAuthenticator = new MocInitialization();
		activity.setAuthenticator(mockAuthenticator);

		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mInstrumentation.callActivityOnCreate(activity, null);
			}
		});

	}

	public void test_Init_NotVerify() {
		MocInitialization mockAuthenticator = new MocInitialization();
		mockAuthenticator.verify = false;
		activity.setAuthenticator(mockAuthenticator);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.init(pgbLoading);
			}
		});
		mInstrumentation.waitForIdleSync();
		int expected = View.VISIBLE;
		int actual = pgbLoading.getVisibility();
		assertEquals(expected, actual);
	}

	public void test_Init_Verify() {
		MocInitialization mockAuthenticator = new MocInitialization();
		mockAuthenticator.verify = true;
		activity.setAuthenticator(mockAuthenticator);
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.init(pgbLoading);
			}
		});
		mInstrumentation.waitForIdleSync();
		int expected = View.INVISIBLE;
		int actual = pgbLoading.getVisibility();
		assertEquals(expected, actual);
	}

	public void test_common_handleMessage() {
		Initialization mockAuthenticator = new MocInitialization();
		activity.setAuthenticator(mockAuthenticator);
		final Message msg = new Message();
		msg.what = R.string.app_name;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.handleMessage(msg);
			}
		});
		mInstrumentation.waitForIdleSync();
		String expected = activity.getString(R.string.app_name);
		String actual = txtMessage.getText().toString();
		assertEquals(expected, actual);
	}

	public void test_FailureMsg_HandleMessage() {
		Initialization mockAuthenticator = new MocInitialization();
		activity.setAuthenticator(mockAuthenticator);
		final Message msg = new Message();
		msg.what = R.string.msgFailureDowloadedStateForm;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.handleMessage(msg);
			}
		});
		mInstrumentation.waitForIdleSync();
		String expected = activity
				.getString(R.string.msgFailureDowloadedStateForm);
		String actual = txtMessage.getText().toString();
		assertEquals(expected, actual);

		int expectedViewState = View.VISIBLE;
		int actualViewState = pgbLoading.getVisibility();
		assertEquals(expectedViewState, actualViewState);
	}

	public void test_SuccessMsg_HandleMessage() {
		Initialization mockAuthenticator = new MocInitialization();
		activity.setAuthenticator(mockAuthenticator);
		final Message msg = new Message();
		msg.what = R.string.msgSuccessDowloadedStateForm;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.handleMessage(msg);
			}
		});
		mInstrumentation.waitForIdleSync();
		String expected = activity
				.getString(R.string.msgSuccessDowloadedStateForm);
		String actual = txtMessage.getText().toString();
		assertEquals(expected, actual);
		int expectedViewState = View.INVISIBLE;
		int actualViewState = pgbLoading.getVisibility();
		assertEquals(expectedViewState, actualViewState);
	}

	public void test_onBackPressed() {
		boolean condition = activity.isFinishing();
		assertFalse(condition);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
		mInstrumentation.waitForIdleSync();
		condition = activity.isFinishing();
		KeyguardManager kgm = (KeyguardManager) activity
				.getSystemService(Context.KEYGUARD_SERVICE);
		boolean lock = kgm.inKeyguardRestrictedInputMode();
		if (!lock) {
			assertTrue(condition);
		}

	}

}
