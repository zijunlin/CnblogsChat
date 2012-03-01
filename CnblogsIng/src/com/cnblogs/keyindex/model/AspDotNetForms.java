package com.cnblogs.keyindex.model;

import java.util.HashMap;
import java.util.Map;


/**
 * 以key -vlaue 形式存储 ，asp.net 网站需要用到的表单数据
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
