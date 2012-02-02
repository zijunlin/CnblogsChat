package com.cnblogs.keyindex.response.res;

import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;

public class FlashMessageCollection extends Resource {

	private List<FlashMessage> list;

	public List<FlashMessage> getAllFlashMessage() {
		return list;
	}

	public void setAllFlashMessage(List<FlashMessage> value) {
		list = value;
	}
}
