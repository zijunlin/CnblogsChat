package com.cnblogs.keyindex;


import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.service.IngListService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngListActivity extends Activity {

	private ListView lstMsg;
	private ProgressBar pgbDownLoadMsg;
	private MessageAdapter adapter;
	private TextView txtResultMessage;

	private IngListService ingListService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		ingListService = new IngListService(this);
		initViews();
	}

	private void initViews() {

		lstMsg = (ListView) findViewById(R.id.lstFlashMessages);
		pgbDownLoadMsg = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
		txtResultMessage = (TextView) findViewById(R.id.txOperMessage);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ingListService.getIngList();
	}

	public void showMessageText(int resId) {
		txtResultMessage.setText(getString(resId));
	}

	public void onDownloadingIng() {
		pgbDownLoadMsg.setVisibility(View.VISIBLE);
		
	}

	public void onDownloadSuccess(List<FlashMessage> list) {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		adapter = new MessageAdapter(this, list);
		lstMsg.setAdapter(adapter);
	}

	public void onDownloadError(int messageId) {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		showMessageText(messageId);
	}

}
