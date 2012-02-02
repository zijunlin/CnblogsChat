package com.cnblogs.keyindex.serializers;

import com.cnblogs.keyindex.net.HttpResult;
import com.cnblogs.keyindex.response.res.AspDotNetForms;
import com.cnblogs.keyindex.response.res.Resource;

public class AspDotNetFormsSerializer implements Serializer {

	@Override
	public Resource format(HttpResult response) {

		if (response == null)
			return null;
		String result = response.getContentHtml();
		AspDotNetForms model = new AspDotNetForms();

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
