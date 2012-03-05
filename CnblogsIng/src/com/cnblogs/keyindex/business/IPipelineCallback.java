package com.cnblogs.keyindex.business;

public interface IPipelineCallback {

	/**
	 * ��̨ҵ�������
	 * @param messageId
	 * @param message
	 */
	void onMaking(int messageId, String message);

	/**
	 * ����ɹ�
	 */
	void onSuccess(BusinessPipeline context);

	/**
	 * ����ʧ��
	 */
	void onFailure(BusinessPipeline context);

}
