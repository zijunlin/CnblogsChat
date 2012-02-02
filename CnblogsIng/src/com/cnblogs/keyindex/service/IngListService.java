package com.cnblogs.keyindex.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.cnblogs.keyindex.IngListActivity;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.response.res.FlashMessageCollection;
import com.cnblogs.keyindex.serializers.MessageSerializer;

import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;

public class IngListService implements Callback {

	private Handler ingHandler;
	private IngListActivity activity;
	private List<FlashMessage> messagesList;
	private CnblogsIngContext context;

	public IngListService(IngListActivity ingListActivity) {
		activity = ingListActivity;
		ingHandler = new Handler(this);
		context = CnblogsIngContext.getContext();

	}

	public void getIngList() {
		activity.onDownloadingIng();
		List<BasicNameValuePair> forms = bulidFormsForList("all",1,50);
		downLoadMsgList(activity.getString(R.string.urlGetMessageList), forms);
	}

	private void downLoadMsgList(final String uri,
			final List<BasicNameValuePair> forms) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				ingHandler.sendEmptyMessage(R.string.msgGetingMessageList);
				ResourceExplorer re=new ResourceExplorer();
				re.getResource(uri, forms, context.getCookieStore())
						.serializerResult(MessageSerializer.class.getName());
				if (messagesList != null)
					messagesList.clear();
				messagesList = ((FlashMessageCollection) re
						.getResponseResource()).getAllFlashMessage();
				if (messagesList != null && messagesList.size() > 0) {
					ingHandler.sendEmptyMessage(R.string.msgGetMessageSuccess);

				} else {
					ingHandler.sendEmptyMessage(R.string.msgGetMessageError);
				}

			}
		}).start();
	}

	private List<BasicNameValuePair> bulidFormsForList(String listType,
			int pageIndex, int pageSize) {
		List<BasicNameValuePair> forms = new ArrayList<BasicNameValuePair>();
		forms.add(new BasicNameValuePair("IngListType", listType));
		forms.add(new BasicNameValuePair("PageIndex", String.valueOf(pageIndex)));
		forms.add(new BasicNameValuePair("Tag", ""));
		forms.add(new BasicNameValuePair("PageSize", String.valueOf(pageSize)));
		return forms;
	}

	@Override
	public boolean handleMessage(Message msg) {

		activity.showMessageText(msg.what);
		if (msg.what == R.string.msgGetMessageSuccess) {
			activity.onDownloadSuccess(messagesList);
		}
		return false;
	}
}
