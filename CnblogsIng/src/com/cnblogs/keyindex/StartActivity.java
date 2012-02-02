package com.cnblogs.keyindex;

import com.cnblogs.keyindex.service.InitService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity {

	private ProgressBar pgbLoading;
	private TextView txtMessage;
	private InitService beginService;
	private final String MAIN_ACTION = "com.cnblogs.keyindex.CnblogsActivity.view";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		pgbLoading = (ProgressBar) findViewById(R.id.pgbInit);
		txtMessage = (TextView) findViewById(R.id.txtMessage);
		beginService=new InitService(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		beginService.startInit();
	}
	
	//展示提示消息
	public void onMessageChanged(int resId)
	{
		String message = getString(resId);
		txtMessage.setText(Html.fromHtml(message));
	}
	
	public void onStartCatalog() {
		Intent intent = new Intent(MAIN_ACTION);
		startActivity(intent);
		finish();
	}

	public void onError() {
		pgbLoading.setVisibility(View.INVISIBLE);
	}

	

}