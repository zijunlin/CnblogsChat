package com.cnblogs.keyindex.business;

import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.repository.FlashMessageRepository;

import android.content.Context;
import android.os.Handler;

public class FlashMessageProvider {

	private Context mContext;
	private Handler mHandler;
	private FlashMessageRepository msgRepository;

	public FlashMessageProvider(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
		msgRepository = new FlashMessageRepository(mContext);
	}

	public List<FlashMessage> getFlashMessages(int pageIndex, int size) {
		return msgRepository.queryMessageBy(pageIndex, size);
	}

}
