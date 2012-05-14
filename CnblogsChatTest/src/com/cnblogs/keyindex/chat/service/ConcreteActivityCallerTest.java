package com.cnblogs.keyindex.chat.service;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.cnblogs.keyindex.chat.StartActivity;
import com.cnblogs.keyindex.chat.service.concrete.ConcreteActivityCaller;

public class ConcreteActivityCallerTest extends
		ActivityInstrumentationTestCase2<StartActivity> {

	public ConcreteActivityCallerTest() {
		super(StartActivity.class);
	}

	ConcreteActivityCaller target;
	Instrumentation instrumentation;
	Activity activity;

	protected void setUp() throws Exception {
		activity = getActivity();
		target = new ConcreteActivityCaller(activity);
		instrumentation = getInstrumentation();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStartActivity() {

		Intent intent = new Intent("com.cnblogs.keyindex.UserAcitivity.sigin");
		target.startActivity(intent);
		boolean condition = activity.isFinishing();
		instrumentation.waitForIdleSync();
		assertTrue(condition);
	}

}
