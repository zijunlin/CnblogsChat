package com.cnblogs.keyindex.repository;

import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;

public interface IFlashMessageRepository {

	List<FlashMessage> getFlashMessageList(int pageSize, int pageIndex);

	FlashMessage getFlashMessage(String feedId);

	void SaveFlashMessage(FlashMessage model,String parentId);

}
