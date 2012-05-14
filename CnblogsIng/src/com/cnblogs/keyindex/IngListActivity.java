package com.cnblogs.keyindex;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.business.FlashMessageProvider;
import com.cnblogs.keyindex.kernel.EventActivity;
import com.cnblogs.keyindex.kernel.MessageEvent;
import com.cnblogs.keyindex.model.FlashMessage;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class IngListActivity extends EventActivity implements OnRefreshListener2 {

	private PullToRefreshListView lstMsg;
	private MessageAdapter adapter;
	private View loadingView;
	private FlashMessageProvider msgProvider;

	private List<FlashMessage> currentFlashMessages;

	private final int firstPageIndex = 0;
	private int pageSize = 25;

	private static final String DETAIL_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.Details";
	private static final String SENDER_ACTION = "com.cnblogs.keyindex.FlashMessageActivity.Sender";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		init();
		bindDate();
		addEventHandler(R.string.msgRefreshComplete, onRefreshComplete);
	}

	private void init() {
		initViews();
		msgProvider = new FlashMessageProvider(this.getApplicationContext(),
				mHandler);
		currentFlashMessages = new ArrayList<FlashMessage>();
		adapter = new MessageAdapter(this.getApplicationContext(),
				currentFlashMessages);
	}

	private void initViews() {
		lstMsg = (PullToRefreshListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnRefreshListener(this);
		lstMsg.getRefreshableView().setOnItemClickListener(
				flashMessageClickListener);
		loadingView = findViewById(R.id.loading);
		loadingView.setVisibility(View.VISIBLE);
		((ImageButton) findViewById(R.id.btnImgSender))
				.setOnClickListener(SenderListener);
	}

	/**
	 * First bind flash message to the ListView
	 */
	private void bindDate() {
		List<FlashMessage> list = msgProvider.getFlashMessages(firstPageIndex,
				pageSize);
		int startPosition = 0;
		insertFlashMessage(startPosition, list);
		lstMsg.getRefreshableView().setAdapter(adapter);
		loadingView.setVisibility(View.GONE);
	}

	private MessageEvent onRefreshComplete=new MessageEvent() {
		
		@Override
		public void EventHandler(Object sender, Message msgEventArg) {
			lstMsg.onRefreshComplete();
			
		}
	};
	
	
	/**
	 * refresh the new FlashMessage
	 */
	@Override
	public void onPullDownToRefresh() {
		new Thread() {

			@Override
			public void run() {
				String firstFeedId = "-1";
				if (currentFlashMessages.size() > 0) {
					firstFeedId = currentFlashMessages.get(0).getFeedId();
				}
				List<FlashMessage> list = msgProvider.getLatestFlashMessages(
						firstFeedId, pageSize);
				insertFlashMessage(0, list);
				mHandler.sendEmptyMessage(R.string.msgRefreshComplete);
			}

		}.start();

	}

	/**
	 * 
	 * get the old the FlashMessage
	 */
	@Override
	public void onPullUpToRefresh() {

		final FlashMessage model = currentFlashMessages.get(currentFlashMessages
				.size() - 1);

		new Thread() {

			@Override
			public void run() {
				if (model != null) {
					List<FlashMessage> list = msgProvider
							.getOlderFlashMessages(model.getFeedId(), pageSize);
					insertFlashMessage(currentFlashMessages.size() - 1, list);
				}
				mHandler.sendEmptyMessage(R.string.msgRefreshComplete);
			}

		}.start();

	}

	private View.OnClickListener SenderListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SENDER_ACTION);
			startActivity(intent);
		}
	};

	private AdapterView.OnItemClickListener flashMessageClickListener = new AdapterView.OnItemClickListener() {

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
	};

	/**
	 * add the FlashMessages to Specified postion;
	 * 
	 * @param postion
	 * @param list
	 */
	public void insertFlashMessage(int postion, List<FlashMessage> list) {
		if (list != null && list.size() > 0) {
			currentFlashMessages.addAll(postion, list);
			adapter.notifyDataSetChanged();
		}
	}

}
