package com.cnblogs.keyindex.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.serializers.MessageSerializer;
import com.cnblogs.keyindex.serializers.Serializer;
import com.cnblogs.keyindex.serializers.SerializerFactory;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.os.Message;

/**
 * �����б���ȡ����
 * @author IndexKey
 *
 */
public class IngListService extends BusinessPipeline  {

	private List<FlashMessage> messagesList;
	private final String DEFAULT_LIST_TYPE = "all";
	private final int DEFAULT_PAGE_INDEX = 1;
	private final int DEFAULT_PAGE_SIZE = 50;
	private String uri;

	@Override
	public void Start() {
		asynQueryList(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
	}

	
	public void asynQueryList(int pageIndex, int pageSize) {
		Map<String, String> forms = bulidFormsForList(DEFAULT_LIST_TYPE,
				pageIndex, pageSize);
		uri=mContext.getString(R.string.urlGetMessageList);
		downLoadMsgList(uri, forms);
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
				mHandler.sendEmptyMessage(R.string.msgGetingMessageList);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String response) {
				Serializer format = SerializerFactory
						.CreateSerializer(MessageSerializer.class.getName());
				messagesList = (List<FlashMessage>) format.format(response);
				if (messagesList != null && messagesList.size() > 0) {
					mHandler.sendEmptyMessage(R.string.msgGetMessageSuccess);

				} else {
					mHandler.sendEmptyMessage(R.string.msgGetMessageError);
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
		switch (msg.what) {
		case R.string.msgGetMessageSuccess:
			success();
			break;
		case R.string.msgGetMessageError:
			failure();
			break;
		case R.string.msgGetingMessageList:
			processing(msg.what);
			break;
		}
		return false;
	}

	public List<FlashMessage> getMsgList()
	{
		return messagesList;
	}
	

}