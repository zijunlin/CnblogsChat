package com.cnblogs.keyindex.model;

import java.util.HashMap;
import java.util.Map;


/**
 * ��key -vlaue ��ʽ�洢 ��asp.net ��վ��Ҫ�õ��ı�����
 * @author IndexKey
 *
 */
public class AspDotNetForms{

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

	public void setEvent(String value) {
		forms.put(eventKey, value);
	}

	public String getEvent() {
		return forms.get(eventKey);
	}

	public void setEventTaget(String value) {
		forms.put(eventTagetKey, value);
	}

	public String getEventTaget() {
		return forms.get(eventTagetKey);
	}

	public void setEventArg(String value) {
		forms.put(eventArgKey, value);
	}

	public String getEventArg() {
		return forms.get(eventArgKey);
	}


	public String getViewStateValue(String key) {
		return forms.get(key);
	}


	public Map<String,String> getForms() {
	
		return forms;
	}

}
