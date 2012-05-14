package com.cnblogs.keyindex.chat.net.response;



import com.cnblogs.keyindex.chat.model.ViewStateForms;

public class VsfProcessPesponse implements ProcessResponse {

	
	@Override
	public Object processDownloaded(String respone) {
		ViewStateForms forms = formatRespone(respone);
		return forms;
	}

	private ViewStateForms formatRespone(String respone) {
		ViewStateForms model = ViewStateForms.parse(respone);
		return model;
	}

}
