package com.cnblogs.keyindex.business;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.repository.FlashMessageRepository;

import android.content.Context;
import android.os.Handler;

public class FlashMessageProvider {

	private Context mContext;
	private Handler mHandler;
	private FlashMessageRepository msgRepository;
	private List<FlashMessage> flashMsgList;

	public FlashMessageProvider(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
		msgRepository = new FlashMessageRepository(mContext);
		flashMsgList = new ArrayList<FlashMessage>();
	}

	/**
	 * Get the current query Flash Messages
	 * 
	 * @return
	 */
	public List<FlashMessage> getCurrentFlashMessages() {
		return flashMsgList;
	}

	public List<FlashMessage> getFlashMessages(int pageIndex, int size) {
		return msgRepository.queryMessage(pageIndex, size);
	}

	public List<FlashMessage> getOlderFlashMessages(String feedId, int size) {
		return msgRepository.queryByCompareCondition(">", size,
				Integer.getInteger(feedId));
	}

	public List<FlashMessage> getLatestFlashMessages(String feedId,
			int limitSize) {

		if (feedId.contentEquals("-1")) {
			return msgRepository.queryMessage(0, limitSize);
		} else {

			return msgRepository.queryByCompareCondition("<", limitSize,
					Integer.getInteger(feedId));
		}
	}
}
