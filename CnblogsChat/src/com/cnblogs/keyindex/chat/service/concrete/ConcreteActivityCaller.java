package com.cnblogs.keyindex.chat.service.concrete;

import com.cnblogs.keyindex.chat.service.ActivityCaller;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

public class ConcreteActivityCaller implements ActivityCaller {

	private Activity activity;

	@Inject
	public ConcreteActivityCaller(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void startActivity(Intent intent) {
		activity.startActivity(intent);
		activity.finish();
	}

	@Override
	public void startActivity(final Intent intent, int delayMillis) {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				activity.startActivity(intent);
				activity.finish();
			}

		}, delayMillis);
	}

}
