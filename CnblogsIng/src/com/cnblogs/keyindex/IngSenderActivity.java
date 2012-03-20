package com.cnblogs.keyindex;

import java.util.Date;


import org.apache.http.cookie.Cookie;

import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.business.MessageSenderService;


import com.loopj.android.http.PersistentCookieStore;

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
	private static final String FEED_ID_KEY = "FeedId";
	private static final String RETURN_TO_AUTHOR = "Author";
	private final String COOLKIE_NAME = ".DottextCookie";
	private static final String LOGIN_ACTION = "com.cnblogs.keyindex.UserAcitivity.sigin";
	private String IngId;
	private String ToAcuthor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message_sender);
		sender = new MessageSenderService();
		sender.InitPipeline(this);
		sender.setPipeLineListener(this);
		IngId = this.getIntent().getStringExtra(FEED_ID_KEY);
		ToAcuthor = this.getIntent().getStringExtra(RETURN_TO_AUTHOR);
		initViews();
	}

	private void initViews() {
		btnOk = (Button) findViewById(R.id.btnSend);
		btnOk.setOnClickListener(onSendMessages);
		txtInput = (EditText) findViewById(R.id.txtInputMsg);
		txtMessage = (TextView) findViewById(R.id.txOperMessage);
		pgbSending = (ProgressBar) findViewById(R.id.pgbDownloadMsg);
		txtInput.setText(ToAcuthor == null ? "" : "@" + ToAcuthor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		authorization();
	}

	private void authorization() {
		if (hasAuthorization()) {
			btnOk.setEnabled(true);
			txtMessage.setText("");
		} else {

			btnOk.setEnabled(false);
			txtMessage.setText(R.string.lblNeedAuthenticate);

			Intent intent = new Intent();
			intent.setAction(LOGIN_ACTION);
			startActivity(intent);
			finish();
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
			// ÏûÏ¢Ä£°å
			msgTemplate = getString(R.string.tmpThought);
			sender.setSendMessage(txtInput.getText().toString(), msgTemplate);
			sender.setMessageId(IngId);
			sender.Start();
		}
	};

	private String FlashMessageAction = "com.cnblogs.keyindex.FlashMessageActivity.view";

	public void showMessage(int resId) {
		txtMessage.setText(getString(resId));
	}

	public boolean hasAuthorization() {
		PersistentCookieStore cookieStore = new PersistentCookieStore(
				this.getApplicationContext());

		for (Cookie item : cookieStore.getCookies()) {
			if (item.getName().contains(COOLKIE_NAME)
					&& !item.isExpired(new Date())) {
				return true;
			}
		}
		return false;
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

	private void insertToCache() {
		// List<FlashMessage>
		// list=CnblogsIngContext.getContext().getFlashMessageContainer();
		// if(list!=null)
		// {
		// list.add(0, object)
		// }
	}

	@Override
	public void onFailure(BusinessPipeline context) {
		pgbSending.setVisibility(View.INVISIBLE);
		btnOk.setEnabled(true);
		txtInput.setEnabled(true);
		txtMessage.setText(R.string.lblSendError);

	}
}
