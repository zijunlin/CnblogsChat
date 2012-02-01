package com.cnblogs.keyindex.model.res;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

public class AspDotNetForms extends Resource{

	private HashMap<String, String> forms;
	public final String viewStateKey = "__VIEWSTATE";
	public final String eventKey = "__EVENTVALIDATION";
	public final String eventTagetKey = "__EVENTTARGET";
	public final String eventArgKey = "__EVENTARGUMENT";
	
	
	
	public AspDotNetForms()
	{
		forms=new HashMap<String, String>();
		forms.put(eventKey, "");
		forms.put(eventTagetKey, "");
		forms.put(viewStateKey, "");
		forms.put(eventArgKey, "");
	}

	public void setViewState(String value) {
		forms.put(viewStateKey, value);
	}

	public String getViewState() {
		return forms.get(viewStateKey);
	}

	public BasicNameValuePair getViewStateForm() {
		return getFormByKey(viewStateKey);
	}

	public void setEvent(String value) {
		forms.put(eventKey, value);
	}

	public String getEvent() {
		return forms.get(eventKey);
	}

	public BasicNameValuePair getEventForm() {
		return getFormByKey(eventKey);
	}

	public void setEventTaget(String value) {
		forms.put(eventTagetKey, value);
	}

	public String getEventTaget() {
		return forms.get(eventTagetKey);
	}

	public BasicNameValuePair getEventTagetForm() {
		return getFormByKey(eventTagetKey);
	}

	public void setEventArg(String value) {
		forms.put(eventArgKey, value);
	}

	public String getEventArg() {
		return forms.get(eventArgKey);
	}

	public BasicNameValuePair getEventArgForm() {
		return getFormByKey(eventArgKey);
	}

	public String getViewStateValue(String key) {
		return forms.get(key);
	}

	public BasicNameValuePair getFormByKey(String key) {
		return new BasicNameValuePair(key, forms.get(key));
	}

	public List<BasicNameValuePair> getForms() {
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		list.add(getEventArgForm());
		list.add(getEventForm());
		list.add(getEventTagetForm());
		list.add(getViewStateForm());
		return list;
	}

}
