package com.cnblogs.keyindex.chat.formatters;

import com.cnblogs.keyindex.chat.model.ViewStateForms;

class ViewStateFormsFormatter implements Formatter {

	/**
	 * 如果无法格式化则返回null
	 */
	@Override
	public ViewStateForms format(String response) {

		if (response == null)
			return null;
		String result = response;
		ViewStateForms model = new ViewStateForms();

		String viewKey = "id=\"__VIEWSTATE\" value=\"";
		int startIndex = result.indexOf(viewKey);
		if (startIndex == -1)
			return null;

		int endIndex = result.indexOf("\" />", startIndex);
		String viewState = result.substring(startIndex + viewKey.length(),
				endIndex);
		model.setViewState(viewState);

		String eventKey = "id=\"__EVENTVALIDATION\" value=\"";
		startIndex = result.indexOf(eventKey);
		endIndex = result.indexOf("\" />", startIndex);
		String eventVali = result.substring(startIndex + eventKey.length(),
				endIndex);

		model.setEvent(eventVali);
		model.setEventArg("");
		model.setEventTaget("");

		return model;

	}

}
