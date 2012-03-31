package com.cnblogs.keyindex;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.adapter.MessageAdapter.ImageViewChangeListener;
import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.FlashMessageProvider;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.ImageLoader;
import com.cnblogs.keyindex.business.IngListService;
import com.cnblogs.keyindex.model.FlashMessage;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class IngListActivity extends Activity implements OnRefreshListener,
		ImageViewChangeListener, OnItemClickListener, Callback {

	private PullToRefreshListView lstMsg;
	private MessageAdapter adapter;
	private View loadingView;
	private ImageLoader loader;

	private FlashMessageProvider msgProvider;
	private Handler mHandler;
	private List<FlashMessage> currentFlashMessages;

	private final int firstPageIndex = 0;
	private int currentPageIndex = 0;
	private int pageSize = 25;
	private int insertPosition = 0;

	private static final String DETAIL_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.Details";
	private static final String SENDER_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.Sender";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		init();
		bindFlashMessage();
	}

	private void init() {
		initViews();
		mHandler = new Handler(this);
		msgProvider = new FlashMessageProvider(this.getApplicationContext(),
				mHandler);
		currentFlashMessages = new ArrayList<FlashMessage>();

		loader = new ImageLoader();
		adapter = new MessageAdapter(IngListActivity.this, loader);
		adapter.setOnImageViewChangeListener(IngListActivity.this);
	}

	private void initViews() {

		lstMsg = (PullToRefreshListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnRefreshListener(this);
		lstMsg.getRefreshableView().setOnItemClickListener(this);
		loadingView = findViewById(R.id.loading);
		loadingView.setVisibility(View.VISIBLE);
		((ImageButton) findViewById(R.id.btnImgSender))
				.setOnClickListener(SenderListener);
	}

	private void bindFlashMessage() {
		currentFlashMessages = msgProvider.getFlashMessages(firstPageIndex,
				pageSize);
		if (currentFlashMessages.size() > 0) {
			// adapter
			lstMsg.setAdapter(adapter);
			loadingView.setVisibility(View.GONE);
		}
		else	
		{
			// TODO call the service download message 
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onFlashMessageLoadComplete(List<FlashMessage> list, int positon) {

		if (list != null && list.size() > 0) {
			currentFlashMessages.addAll(positon, list);
			adapter.notifyDataSetChanged();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private View.OnClickListener SenderListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(SENDER_ACTION);
			startActivity(intent);
		}
	};

	private IPipelineCallback callback = new IPipelineCallback() {

		@Override
		public void onMaking(int messageId, String message) {

		}

		@Override
		public void onSuccess(BusinessPipeline context) {

			loadingView.setVisibility(View.GONE);
			List<FlashMessage> list = ((IngListService) context).getMsgList();
			insertFlashMessage(list);
			adapter.setList(CnblogsIngContext.getContext()
					.getFlashMessageContainer());
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
		if (currentFlashMessage == null) {
			CnblogsIngContext.getContext().setFlashMessageList(list);
			return;
		}
		if (currentFlashMessage.isEmpty()) {
			currentFlashMessage.addAll(list);
			return;
		}
		if (insertPosition == 0
				&& !currentFlashMessage.get(0).getFeedId()
						.contentEquals(list.get(0).getFeedId())) {
			currentFlashMessage.clear();
			currentFlashMessage.addAll(insertPosition, list);
		}

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

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			List<FlashMessage> list = CnblogsIngContext.getContext()
					.getFlashMessageContainer();
			if (list != null) {
				list.clear();
			}
			this.finish();
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(DETAIL_ACTION);
		// 减一因为ListView有头部HeaderView在
		intent.putExtra("Position", position - 1);

		ImageView imgView = (ImageView) view.findViewById(R.id.imgHeader);
		Bitmap img = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
		intent.putExtra("HeanderImage", img);
		startActivity(intent);
	}

}
