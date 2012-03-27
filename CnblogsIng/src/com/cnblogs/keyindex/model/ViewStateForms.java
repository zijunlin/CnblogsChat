package com.cnblogs.keyindex.model;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 以key -vlaue 形式存储 ，asp.net 网站需要用到的表单数据
 * 
 * @author IndexKey
 * 
 */
public class ViewStateForms implements Parcelable {

	/**
	 * the intent key for Extra;
	 */
	public static final String VIEW_STATE_KEY = "viewStateKey";


	private WeakHashMap<String, String> forms;
	private final String viewStateKey = "__VIEWSTATE";
	private final String eventKey = "__EVENTVALIDATION";
	private final String eventTagetKey = "__EVENTTARGET";
	private final String eventArgKey = "__EVENTARGUMENT";

	public ViewStateForms() {
		forms = new WeakHashMap<String, String>();
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

	public Map<String, String> getForms() {

		return forms;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(forms);

	}

	public static final Parcelable.Creator<ViewStateForms> CREATOR = new Creator<ViewStateForms>() {

		@Override
		public ViewStateForms[] newArray(int size) {
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ViewStateForms createFromParcel(Parcel source) {
			ViewStateForms model = new ViewStateForms();
			model.forms = new WeakHashMap<String, String>(
					source.readHashMap(HashMap.class.getClassLoader()));

			return model;
		}
	};
}
