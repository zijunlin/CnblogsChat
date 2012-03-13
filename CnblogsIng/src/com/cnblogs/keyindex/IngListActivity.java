package com.cnblogs.keyindex;

import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.adapter.MessageAdapter.ImageViewChangeListener;
import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.ImageLoader;
import com.cnblogs.keyindex.business.IngListService;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.FlashMessage;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IngListActivity extends Activity implements OnRefreshListener,
		ImageViewChangeListener {

	private PullToRefreshListView lstMsg;
	private MessageAdapter adapter;
	private View loadingView;
	private ImageLoader loader;

	private BusinessPipeline businessService;
	private int pageIndex = 1;
	private int pageSize = 25;
	private int insertPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		initViews();

		loader = new ImageLoader();
		adapter = new MessageAdapter(IngListActivity.this, loader);
		adapter.setOnImageViewChangeListener(IngListActivity.this);
		initBussinessService();
		businessService.Start();
	}

	private void initViews() {

		lstMsg = (PullToRefreshListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnRefreshListener(this);
		loadingView = findViewById(R.id.loading);
		loadingView.setVisibility(View.VISIBLE);
	}

	private void initBussinessService() {
		businessService = new IngListService();
		businessService.InitPipeline(this);
		businessService.setPipeLineListener(callback);
	}

	private IPipelineCallback callback = new IPipelineCallback() {

		@Override
		public void onMaking(int messageId, String message) {

		}

		@Override
		public void onSuccess(BusinessPipeline context) {

			loadingView.setVisibility(View.GONE);
			List<FlashMessage> list = ((IngListService) context).getMsgList();
			insertFlashMessage(list);
			lstMsg.setAdapter(adapter);
			lstMsg.onRefreshComplete();
			

		}

		@Override
		public void onFailure(BusinessPipeline context) {

		}

	};

	private void insertFlashMessage(List<FlashMessage> list) {

		List<FlashMessage> currentFlashMessage = CnblogsIngContext.getContext()
				.getFlashMessageContainer();
		if (currentFlashMessage == null)
			return;
		if (currentFlashMessage.isEmpty()) {
			currentFlashMessage.addAll(list);
			return;
		}
		if (insertPosition == 0
				&& !currentFlashMessage.get(0).getFeedId()
						.contentEquals(list.get(0).getFeedId()))
			currentFlashMessage.addAll(insertPosition, list);

	}

	@Override
	public void onRefresh() {

		// 插入到队列前
		insertPosition = 0;
		((IngListService) businessService).asynQueryList(pageIndex, pageSize);

	}

	public void changeListImageView(Drawable imageDrawable, int location) {
		ImageView view = (ImageView) lstMsg.findViewWithTag(location);
		if (view != null)
			view.setImageDrawable(imageDrawable);
	}

	/**
	 * List 内 头像变化通知
	 */
	@Override
	public void onImageViewChange(Drawable imageDrawable, int location) {
		changeListImageView(imageDrawable, location);
	}

}
