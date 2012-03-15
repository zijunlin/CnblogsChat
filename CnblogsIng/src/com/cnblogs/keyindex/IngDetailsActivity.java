package com.cnblogs.keyindex;

import java.util.List;

import com.cnblogs.keyindex.adapter.CommentAdapter;
import com.cnblogs.keyindex.business.BusinessPipeline;
import com.cnblogs.keyindex.business.CommentLoader;
import com.cnblogs.keyindex.business.IPipelineCallback;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.FlashMessage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IngDetailsActivity extends Activity implements IPipelineCallback {

	private ImageView imgHeader;
	private ImageView imgNewPerson;
	private ImageView imgShining;
	private TextView txtAuther;
	private TextView txtContent;
	private TextView txtTime;
	private FlashMessage currentFlashMessage;
	private ListView lstComment;
	private ProgressBar pgbLoading;
	private CommentLoader businessService;
	private CommentAdapter adapter;

	private int defaultCommentCount = 45;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_message_detail);
		getCurrentFlashMessage();
		findViews();
		bindData();
		bindHeader();
		getComments();
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
		txtContent = (TextView) findViewById(R.id.txtFlashMsgItemContent);
		txtTime = (TextView) findViewById(R.id.txtFlashMsgItemTime);
		lstComment = (ListView) findViewById(R.id.lstComments);
		pgbLoading = (ProgressBar) findViewById(R.id.loading);
	}

	private void bindData() {
		if (currentFlashMessage == null)
			return;
		txtAuther.setText(currentFlashMessage.getAuthorName());
		txtContent.setText(currentFlashMessage.getSendContent());
		txtTime.setText(currentFlashMessage.getGeneralTime());
		changeVisiblility(currentFlashMessage.IsNewPerson(), imgNewPerson);
		changeVisiblility(currentFlashMessage.IsShining(), imgShining);

		adapter = new CommentAdapter(this.getApplicationContext(),
				currentFlashMessage.getCommentsMessage());
		lstComment.setAdapter(adapter);
	}

	private void changeVisiblility(boolean visibility, View view) {
		view.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
	}

	private void bindHeader() {
		Bitmap img = this.getIntent().getParcelableExtra("HeanderImage");
		imgHeader.setImageBitmap(img);
	}

	private void getComments() {
		pgbLoading.setVisibility(View.VISIBLE);
		businessService = new CommentLoader();
		businessService.InitPipeline(this);
		businessService.setRequestIngId(currentFlashMessage.getFeedId());
		businessService.setRequestIngCount(defaultCommentCount);
		if (currentFlashMessage.HasComments()) {
			businessService.Start();
		} else {
			pgbLoading.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onMaking(int messageId, String message) {

	}

	@Override
	public void onSuccess(BusinessPipeline context) {
		pgbLoading.setVisibility(View.INVISIBLE);
		List<FlashMessage> list = ((CommentLoader) context).GetComments();
		addNewComment(list);
		adapter.notifyDataSetChanged();
	}

	private void addNewComment(List<FlashMessage> list) {
		if (currentFlashMessage.getCommentsMessage().size() != 0) {
			currentFlashMessage.getCommentsMessage().clear();
		}
		currentFlashMessage.addAllComments(list);

	}

	@Override
	public void onFailure(BusinessPipeline context) {

		pgbLoading.setVisibility(View.INVISIBLE);
	}

}
