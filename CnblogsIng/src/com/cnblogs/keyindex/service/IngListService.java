package com.cnblogs.keyindex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnblogs.keyindex.IngListActivity;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.serializers.MessageSerializer;
import com.cnblogs.keyindex.serializers.Serializer;
import com.cnblogs.keyindex.serializers.SerializerFactory;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;

public class IngListService implements Callback {

	private Handler ingHandler;
	private IngListActivity activity;
	private List<FlashMessage> messagesList;
	private AsyncHttpClient httpClient;

	private final String DEFAULT_LIST_TYPE = "all";


	public IngListService(IngListActivity ingListActivity) {
		activity = ingListActivity;
		ingHandler = new Handler(this);
		httpClient = new AsyncHttpClient();

	}


	public void getIngList(int pageIndex, int pageSize) {
		activity.onDownloadingIng();
		Map<String, String> forms = bulidFormsForList(DEFAULT_LIST_TYPE, pageIndex,
				pageSize);

		downLoadMsgList(activity.getString(R.string.urlGetMessageList), forms);
	}

	private void downLoadMsgList(final String uri,
			final Map<String, String> forms) {
		RequestParams requestParams = new RequestParams(forms);
		httpClient.post(uri, requestParams, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onStart() {
				ingHandler.sendEmptyMessage(R.string.msgGetingMessageList);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String response) {
				Serializer format = SerializerFactory
						.CreateSerializer(MessageSerializer.class.getName());
				messagesList = (List<FlashMessage>) format.format(response);
				if (messagesList != null && messagesList.size() > 0) {
					ingHandler.sendEmptyMessage(R.string.msgGetMessageSuccess);

				} else {
					ingHandler.sendEmptyMessage(R.string.msgGetMessageError);
				}
			}

		});

	}

	private Map<String, String> bulidFormsForList(String listType,
			int pageIndex, int pageSize) {
		Map<String, String> forms = new HashMap<String, String>();
		forms.put("IngListType", listType);
		forms.put("PageIndex", String.valueOf(pageIndex));
		forms.put("Tag", "");
		forms.put("PageSize", String.valueOf(pageSize));
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
