package com.cnblogs.keyindex.business;

public interface IPipelineCallback {

	/**
	 * 后台业务进行中
	 * @param messageId
	 * @param message
	 */
	void onMaking(int messageId, String message);

	/**
	 * 处理成功
	 */
	void onSuccess(BusinessPipeline context);

	/**
	 * 处理失败
	 */
	void onFailure(BusinessPipeline context);

}
