package com.cnblogs.keyindex;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.adapter.MessageAdapter.ImageViewChangeListener;
import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.ImageLoader;
import com.cnblogs.keyindex.business.IngListService;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class IngListActivity extends Activity implements OnRefreshListener,
		ImageViewChangeListener {

	private PullToRefreshListView lstMsg;
	private MessageAdapter adapter;
	private TextView txtResultMessage;
	private ImageLoader loader;

	private BusinessPipeline businessService;
	private int pageIndex = 1;
	private int pageSize = 150;
	private final String MAIN_ACTIVITY_ACITON = "com.cnblogs.keyindex.CnblogsActivity.view";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		initViews();
		initBussinessService();
		businessService.Start();
		loader=new ImageLoader();

	}

	private void initViews() {

		lstMsg = (PullToRefreshListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnRefreshListener(this);
		txtResultMessage = (TextView) findViewById(R.id.txOperMessage);
	}

	private void initBussinessService() {
		businessService = new IngListService();
		businessService.InitPipeline(this);
		businessService.setPipeLineListener(callback);
	}

	private IPipelineCallback callback = new IPipelineCallback() {

		@Override
		public void onMaking(int messageId, String message) {
			txtResultMessage.setText(getString(messageId));

		}

		@Override
		public void onSuccess(BusinessPipeline context) {

			adapter = new MessageAdapter(IngListActivity.this,
					((IngListService) context).getMsgList(),loader);
			adapter.setOnImageViewChangeListener(IngListActivity.this);
			lstMsg.setAdapter(adapter);
			lstMsg.onRefreshComplete();

		}

		@Override
		public void onFailure(BusinessPipeline context) {

		}

	};

	@Override
	public void onRefresh() {

		((IngListService) businessService).asynQueryList(pageIndex, pageSize);

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(MAIN_ACTIVITY_ACITON);
			startActivity(intent);
			this.finish();
		}
		return super.dispatchKeyEvent(event);
	}

	public void changeListImageView(Drawable imageDrawable, int location) {
		ImageView view = (ImageView) lstMsg.findViewWithTag(location);
		if (view != null)
			view.setImageDrawable(imageDrawable);
	}

	@Override
	public void onImageViewChange(Drawable imageDrawable, int location) {
		changeListImageView(imageDrawable, location);

	}

}
