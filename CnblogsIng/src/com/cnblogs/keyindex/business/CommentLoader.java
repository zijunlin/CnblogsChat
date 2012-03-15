package com.cnblogs.keyindex.business;

import java.util.List;

import org.apache.http.entity.StringEntity;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.serializers.CommentSerializer;
import com.cnblogs.keyindex.serializers.SerializerHttpResponseHandler;

import android.content.Context;
import android.os.Message;

public class CommentLoader extends BusinessPipeline {

	private String url;

	private String currentIngId;
	private int commentShowCount = 15;
	private List<FlashMessage> listComment;

	public void setRequestIngId(String id) {
		currentIngId = id;
	}

	public void setRequestIngCount(int value) {
		commentShowCount = 15;
	}

	public List<FlashMessage> GetComments() {
		return listComment;
	}

	@Override
	public void InitPipeline(Context context) {
		super.InitPipeline(context);
		url = context.getString(R.string.urlComments);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case R.string.msgGetMessageSuccess: {
			success();
		}
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

	@Override
	public void Start() {

		try {
			downloadComment(url);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void downloadComment(final String url) throws Throwable {

		httpClient.addHeader("X_REQUESTED_WITH", "XMLHttpRequest");

		StringEntity se = new StringEntity("{ingId:" + currentIngId
				+ ",showcount:" + commentShowCount + "}");

		httpClient.post(mContext, url, se, "application/json; charset=utf-8",
				new SerializerHttpResponseHandler() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSerializerSuccess(Object response) {
						listComment = (List<FlashMessage>) response;
						if (listComment != null && listComment.size() > 0) {
							mHandler.sendEmptyMessage(R.string.msgGetMessageSuccess);

						} else {
							mHandler.sendEmptyMessage(R.string.msgGetMessageError);
						}
					}

					@Override
					public void onStart() {
						this.setSerializer(CommentSerializer.class.getName());
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
					}

				});
	}

}
