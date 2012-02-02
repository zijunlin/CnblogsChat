package com.cnblogs.keyindex;

import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.Section;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngSenderActivity extends Activity {

	private Button btnOk;
	private EditText txtInput;
	private TextView txtMessage;
	private ProgressBar pgbSending;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message_sender);
		initViews();
	}

	private void initViews() {
		btnOk = (Button) findViewById(R.id.btnSend);
		btnOk.setOnClickListener(onSendMessages);
		txtInput = (EditText) findViewById(R.id.txtInputMsg);
		txtMessage = (TextView) findViewById(R.id.txOperMessage);
		pgbSending = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
	}

	@Override
	protected void onResume() {
		super.onResume();
		authorization();
	}

	private void authorization() {
		if (!CnblogsIngContext.getContext().IsAuthorization()) {
			btnOk.setEnabled(false);
			txtMessage.setText("请先登录");
		} else {
			btnOk.setEnabled(true);
			txtMessage.setText("");
		}
	}

	private View.OnClickListener onSendMessages = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (txtInput.getText().toString().length() == 0) {
				txtInput.setError("不能为空");
				return;
			}
			pgbSending.setVisibility(View.VISIBLE);
			btnOk.setEnabled(false);
			txtInput.setEnabled(false);
		}
	};

	public void onFaildSend() {
		pgbSending.setVisibility(View.INVISIBLE);
		btnOk.setEnabled(true);
		txtInput.setEnabled(true);
		txtMessage.setText("发送失败");

	}

	public void onSuccessedSend() {
		pgbSending.setVisibility(View.INVISIBLE);
		btnOk.setEnabled(true);
		txtInput.setEnabled(true);
		txtMessage.setText("");
		Section section = CnblogsIngContext.getContext().getSectionByName(
				"com.cnblogs.keyindex.FlashMessageActivity.view");
		if (section != null) {
			Intent intent = new Intent(section.getAction());
			startActivity(intent);
			this.finish();
		}
	}

	// private void sendMessage(String message) {
	// pgbDownLoadMsg.setVisibility(View.VISIBLE);
	// btnSend.setEnabled(false);
	// txtInput.setEnabled(false);
	// final List<BasicNameValuePair> forms = bulidForm(message);
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// CnblogsContext context = CnblogsContext.getContext();
	// HttpHelper httpHelper = new HttpHelper();
	// httpHelper.setCookieStore(context.getCookieStore());
	// handler.sendEmptyMessage(R.string.msgSending);
	// String result = httpHelper.postMethod(
	// getString(R.string.urlMessage), forms);
	//
	// if (result != null
	// && result.contentEquals("{\"IsSuccess\":true}")) {
	// handler.sendEmptyMessage(R.string.msgSendSuccess);
	//
	// } else {
	// handler.sendEmptyMessage(R.string.msgSendError);
	// }
	//
	// }
	// }).start();
	// }

	// private List<BasicNameValuePair> bulidForm(String message) {
	// List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
	// forms.add(new BasicNameValuePair("content", message));
	// forms.add(new BasicNameValuePair("publicFlag", "1"));
	// return forms;
	// }

	// @Override
	// public boolean handleMessage(Message msg) {
	//
	// txtResultMessage.setText(getString(msg.what));
	// switch (msg.what) {
	// case R.string.msgSendSuccess:
	// sendSuccess();
	// break;
	// case R.string.msgSendError:
	// faildSend();
	// case R.string.msgGetMessageSuccess:
	// pgbDownLoadMsg.setVisibility(View.INVISIBLE);
	// bindMessage();
	// default:
	// break;
	// }
	// return false;
	// }
}
