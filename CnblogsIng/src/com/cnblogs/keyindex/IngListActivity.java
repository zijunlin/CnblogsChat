package com.cnblogs.keyindex;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.net.HttpHelper;
import com.cnblogs.keyindex.serializers.HtmlEntitySerialization;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngListActivity extends Activity implements Callback,
		OnClickListener {

	private ImageView imgTitleLogo;
	private EditText txtMessage;
	private Button btnSend;
	private ListView lstMsg;
	private CnblogsIngContext cnblogsContext;
	private ProgressBar pgbDownLoadMsg;
	private Handler handler;
	private List<FlashMessage> messagesList;
	private MessageAdapter adapter;
	private TextView txtResultMessage;
	private int lastItem = 0;
	private int isRefreshing = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);

		imgTitleLogo = (ImageView) findViewById(R.id.imgTitleLogo);
		txtMessage = (EditText) findViewById(R.id.txtInputMsg);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
		lstMsg = (ListView) findViewById(R.id.lstFlashMessages);
		lstMsg.setOnScrollListener(flushList);
		pgbDownLoadMsg = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
		txtResultMessage = (TextView) findViewById(R.id.txOperMessage);
		cnblogsContext = CnblogsContext.getContext();
		handler = new Handler(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initView();
		downLoadMsgList();
	}

	private OnScrollListener flushList = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			lastItem = firstVisibleItem + visibleItemCount - 1;
			if (isRefreshing == 0 && totalItemCount > 0
					&& lastItem == totalItemCount - 1) {
				isRefreshing = 1;

				downLoadMsgList();
			}

		}
	};

	private void initView() {
		if (cnblogsContext.getCookieStore() == null) {
			txtMessage.setHint(getString(R.string.lblNoAuthenticate));
		} else {
			txtMessage.setHint("");
		}
	}

	@Override
	public boolean handleMessage(Message msg) {

		txtResultMessage.setText(getString(msg.what));
		switch (msg.what) {
		case R.string.msgSendSuccess:
			sendSuccess();
			break;
		case R.string.msgSendError:
			faildSend();
		case R.string.msgGetMessageSuccess:
			pgbDownLoadMsg.setVisibility(View.INVISIBLE);
			bindMessage();
		default:
			break;
		}
		return false;
	}

	private void bindMessage() {
		adapter = new MessageAdapter(this, messagesList);
		lstMsg.setAdapter(adapter);
		isRefreshing = 0;

	}

	private void faildSend() {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		btnSend.setEnabled(true);
		txtMessage.setEnabled(true);
		txtMessage.setError("∑¢ÀÕ ß∞‹");

	}

	private void sendSuccess() {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		btnSend.setEnabled(true);
		txtMessage.setEnabled(true);
		FlashMessage model = new FlashMessage();
		model.setAuthorName("your");
		model.setSendContent(txtMessage.getText().toString());
		model.setGeneralTime("∏’∏’");
		messagesList.add(0, model);
		txtMessage.setText("");
		adapter.notifyDataSetChanged();

	}

	private void downLoadMsgList() {
		pgbDownLoadMsg.setVisibility(View.VISIBLE);
		if (cnblogsContext.getCookieStore() == null)
			return;
		final List<BasicNameValuePair> forms = bulidFormsForList();
		new Thread(new Runnable() {

			@Override
			public void run() {

				CnblogsContext context = CnblogsContext.getContext();
				HttpHelper httpHelper = new HttpHelper();
				httpHelper.setCookieStore(context.getCookieStore());
				handler.sendEmptyMessage(R.string.msgGetingMessageList);
				String result = httpHelper.postMethod(
						getString(R.string.urlGetMessageList), forms);

				if (messagesList != null)
					messagesList.clear();
				messagesList = HtmlEntitySerialization
						.serialieFlashMessage(result);
				if (messagesList != null && messagesList.size() > 0) {
					handler.sendEmptyMessage(R.string.msgGetMessageSuccess);

				} else {
					handler.sendEmptyMessage(R.string.msgGetMessageError);
				}

			}
		}).start();
	}

	private List<BasicNameValuePair> bulidFormsForList() {
		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
		forms.add(new BasicNameValuePair("IngListType", "all"));
		forms.add(new BasicNameValuePair("PageIndex", "1"));
		forms.add(new BasicNameValuePair("Tag", ""));
		forms.add(new BasicNameValuePair("PageSize", "40"));
		return forms;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSend:
			sendMessage(txtMessage.getText().toString());
			break;

		default:
			break;
		}

	}

	private void sendMessage(String message) {
		pgbDownLoadMsg.setVisibility(View.VISIBLE);
		btnSend.setEnabled(false);
		txtMessage.setEnabled(false);
		final List<BasicNameValuePair> forms = bulidForm(message);
		new Thread(new Runnable() {

			@Override
			public void run() {

				CnblogsContext context = CnblogsContext.getContext();
				HttpHelper httpHelper = new HttpHelper();
				httpHelper.setCookieStore(context.getCookieStore());
				handler.sendEmptyMessage(R.string.msgSending);
				String result = httpHelper.postMethod(
						getString(R.string.urlMessage), forms);

				if (result != null
						&& result.contentEquals("{\"IsSuccess\":true}")) {
					handler.sendEmptyMessage(R.string.msgSendSuccess);

				} else {
					handler.sendEmptyMessage(R.string.msgSendError);
				}

			}
		}).start();
	}

	private List<BasicNameValuePair> bulidForm(String message) {
		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
		forms.add(new BasicNameValuePair("content", message));
		forms.add(new BasicNameValuePair("publicFlag", "1"));
		return forms;
	}

}
