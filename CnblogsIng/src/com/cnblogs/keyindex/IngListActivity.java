package com.cnblogs.keyindex;

import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.service.IngListService;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

public class IngListActivity extends Activity implements OnRefreshListener {

	private PullToRefreshListView lstMsg;
	private ProgressBar pgbDownLoadMsg;
	private MessageAdapter adapter;
	private TextView txtResultMessage;

	private IngListService ingListService;
	private int pageIndex = 1;
	private int pageSize = 70;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		ingListService = new IngListService(this);
		initViews();
		ingListService.getIngList(pageIndex, pageSize);
	}

	private void initViews() {

		lstMsg = (PullToRefreshListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnRefreshListener(this);
		pgbDownLoadMsg = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
		txtResultMessage = (TextView) findViewById(R.id.txOperMessage);
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
		lstMsg.onRefreshComplete();
	}

	public void onDownloadError(int messageId) {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		showMessageText(messageId);
	}

	@Override
	public void onRefresh() {

		ingListService.getIngList(pageIndex, pageSize);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(
					"com.cnblogs.keyindex.CnblogsActivity.view");
			startActivity(intent);
		}
		return super.dispatchKeyEvent(event);
	}
}
