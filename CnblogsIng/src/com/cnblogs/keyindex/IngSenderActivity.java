package com.cnblogs.keyindex;

import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.MessageSenderService;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngSenderActivity extends Activity implements IPipelineCallback {

	private Button btnOk;
	private EditText txtInput;
	private TextView txtMessage;
	private ProgressBar pgbSending;
	private String msgTemplate;
	private MessageSenderService sender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message_sender);
		sender = new MessageSenderService();
		sender.InitPipeline(this);
		sender.setPipeLineListener(this);
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
			txtMessage.setText(R.string.lblNeedAuthenticate);
		} else {
			btnOk.setEnabled(true);
			txtMessage.setText("");
		}
	}

	private View.OnClickListener onSendMessages = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (txtInput.getText().toString().length() == 0) {
				txtInput.setError(getString(R.string.lblNeedInput));
				return;
			}
			pgbSending.setVisibility(View.VISIBLE);
			btnOk.setEnabled(false);
			txtInput.setEnabled(false);
			//��Ϣģ��
			msgTemplate=getString(R.string.tmpThought);
			sender.setSendMessage(txtInput.getText().toString(),msgTemplate);
			sender.Start();
		}
	};

	

	private String FlashMessageAction = "com.cnblogs.keyindex.FlashMessageActivity.view";

	public void showMessage(int resId) {
		txtMessage.setText(getString(resId));
	}

	@Override
	public void onMaking(int messageId, String message) {
		showMessage(messageId);

	}

	@Override
	public void onSuccess(BusinessPipeline context) {
		pgbSending.setVisibility(View.INVISIBLE);
		btnOk.setEnabled(true);
		txtInput.setEnabled(true);
		txtMessage.setText("");

		Intent intent = new Intent(FlashMessageAction);
		startActivity(intent);
		this.finish();

	}

	@Override
	public void onFailure(BusinessPipeline context) {
		pgbSending.setVisibility(View.INVISIBLE);
		btnOk.setEnabled(true);
		txtInput.setEnabled(true);
		txtMessage.setText(R.string.lblSendError);

	}
}
