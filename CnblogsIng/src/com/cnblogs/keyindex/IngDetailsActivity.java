package com.cnblogs.keyindex;

import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.FlashMessage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IngDetailsActivity extends Activity {

	private ImageView imgHeader;
	private ImageView imgNewPerson;
	private ImageView imgShining;
	private TextView txtAuther;
	private TextView txtContent;
	private FlashMessage currentFlashMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message_detail);
		getCurrentFlashMessage();
		findViews();
		bindData();
		bindHeader();
	}

	private void getCurrentFlashMessage() {
		int positiaon = this.getIntent().getIntExtra("Position", 0);
		currentFlashMessage = CnblogsIngContext.getContext()
				.getFlashMessageContainer().get(positiaon);
	}

	private void findViews() {
		imgHeader = (ImageView) findViewById(R.id.imgHeader);
		imgNewPerson = (ImageView) findViewById(R.id.imgNewPerson);
		imgShining = (ImageView) findViewById(R.id.imgShining);
		txtAuther = (TextView) findViewById(R.id.txtFlashMsgItemAuther);
//		txtContent = (TextView) findViewById(R.id.txtFlashMsgItemContent);
	}

	private void bindData() {
		if (currentFlashMessage == null)
			return;
		txtAuther.setText(currentFlashMessage.getAuthorName());
//		txtContent.setText(currentFlashMessage.getSendContent());
		changeVisiblility(currentFlashMessage.IsNewPerson(), imgNewPerson);
		changeVisiblility(currentFlashMessage.IsShining(), imgShining);

	}

	private void changeVisiblility(boolean visibility, View view) {
		view.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
	}

	private void bindHeader() {
		Bitmap img = this.getIntent().getParcelableExtra("HeanderImage");
		imgHeader.setImageBitmap(img);
	}

}
