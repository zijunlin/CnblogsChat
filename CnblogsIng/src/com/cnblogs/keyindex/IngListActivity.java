package com.cnblogs.keyindex;


import java.util.List;

import com.cnblogs.keyindex.adapter.MessageAdapter;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.service.IngListService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngListActivity extends Activity implements	OnClickListener {

	private ImageView imgTitleLogo;
	private EditText txtInput;
	private Button btnSend;
	private ListView lstMsg;
	private ProgressBar pgbDownLoadMsg;
	private List<FlashMessage> messagesList;
	private MessageAdapter adapter;
	private TextView txtResultMessage;
	private int lastItem = 0;
	private int isRefreshing = 0;

	private IngListService ingListService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message);
		ingListService = new IngListService(this);
		initViews();
	}

	private void initViews() {
		imgTitleLogo = (ImageView) findViewById(R.id.imgTitleLogo);
		txtInput = (EditText) findViewById(R.id.txtInputMsg);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
		lstMsg = (ListView) findViewById(R.id.lstFlashMessages);
		pgbDownLoadMsg = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
		txtResultMessage = (TextView) findViewById(R.id.txOperMessage);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ingListService.getIngList();
	}

//	@Override
//	public boolean handleMessage(Message msg) {
//
//		txtResultMessage.setText(getString(msg.what));
//		switch (msg.what) {
//		case R.string.msgSendSuccess:
//			sendSuccess();
//			break;
//		case R.string.msgSendError:
//			faildSend();
//		case R.string.msgGetMessageSuccess:
//			pgbDownLoadMsg.setVisibility(View.INVISIBLE);
//			bindMessage();
//		default:
//			break;
//		}
//		return false;
//	}

	private void faildSend() {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		btnSend.setEnabled(true);
		txtInput.setEnabled(true);
		txtInput.setError("∑¢ÀÕ ß∞‹");

	}

	private void sendSuccess() {
		pgbDownLoadMsg.setVisibility(View.INVISIBLE);
		btnSend.setEnabled(true);
		txtInput.setEnabled(true);
		FlashMessage model = new FlashMessage();
		model.setAuthorName("your");
		model.setSendContent(txtInput.getText().toString());
		model.setGeneralTime("∏’∏’");
		messagesList.add(0, model);
		txtInput.setText("");
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSend:
//			sendMessage(txtInput.getText().toString());
			break;
		default:
			break;
		}

	}

//	private void sendMessage(String message) {
//		pgbDownLoadMsg.setVisibility(View.VISIBLE);
//		btnSend.setEnabled(false);
//		txtInput.setEnabled(false);
//		final List<BasicNameValuePair> forms = bulidForm(message);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				CnblogsContext context = CnblogsContext.getContext();
//				HttpHelper httpHelper = new HttpHelper();
//				httpHelper.setCookieStore(context.getCookieStore());
//				handler.sendEmptyMessage(R.string.msgSending);
//				String result = httpHelper.postMethod(
//						getString(R.string.urlMessage), forms);
//
//				if (result != null
//						&& result.contentEquals("{\"IsSuccess\":true}")) {
//					handler.sendEmptyMessage(R.string.msgSendSuccess);
//
//				} else {
//					handler.sendEmptyMessage(R.string.msgSendError);
//				}
//
//			}
//		}).start();
//	}

//	private List<BasicNameValuePair> bulidForm(String message) {
//		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
//		forms.add(new BasicNameValuePair("content", message));
//		forms.add(new BasicNameValuePair("publicFlag", "1"));
//		return forms;
//	}

	/****************************************************************************/
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
