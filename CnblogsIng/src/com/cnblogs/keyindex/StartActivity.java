package com.cnblogs.keyindex;

import java.util.Date;

import org.apache.http.cookie.Cookie;

import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.InitContext;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity implements IPipelineCallback {

	private ProgressBar pgbLoading;
	private TextView txtMessage;
	private BusinessPipeline beginService;
	private static final String LOGIN_ACTION = "com.cnblogs.keyindex.UserAcitivity.sigin";
	private static final String ING_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.view";
	private final String COOLKIE_NAME = ".DottextCookie";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		pgbLoading = (ProgressBar) findViewById(R.id.pgbInit);
		txtMessage = (TextView) findViewById(R.id.txtMessage);
		beginService = new InitContext();
		beginService.InitPipeline(this);
		beginService.setPipeLineListener(this);
		beginService.Start();
	}

	@Override
	public void onMaking(int messageId, String message) {
		txtMessage.setText(Html.fromHtml(message));

	}

	@Override
	public void onSuccess(BusinessPipeline context) {

		Intent intent = new Intent();
		intent.setAction(LOGIN_ACTION);
		PersistentCookieStore cookieStore = new PersistentCookieStore(
				this.getApplicationContext());

		for (Cookie item : cookieStore.getCookies()) {
			if (item.getName().contains(COOLKIE_NAME)
					&& !item.isExpired(new Date())) {
				intent.setAction(ING_ACTION);
				break;
			}
		}

		startActivity(intent);
		finish();

	}

	@Override
	public void onFailure(BusinessPipeline context) {
		pgbLoading.setVisibility(View.INVISIBLE);

	}

}