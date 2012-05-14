package com.cnblogs.keyindex.chat.model;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.formatters.Formatter;

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
	public static final String STATE_KEY = "__VIEWSTATE";
	public static final String EVENT_KEY = "__EVENTVALIDATION";
	public static final String EVENT_TAGET_KEY = "__EVENTTARGET";
	public static final String EVENT_ARG_KEY = "__EVENTARGUMENT";

	public ViewStateForms() {
		forms = new WeakHashMap<String, String>();
		forms.put(EVENT_KEY, "");
		forms.put(EVENT_TAGET_KEY, "");
		forms.put(STATE_KEY, "");
		forms.put(EVENT_ARG_KEY, "");
	}

	public void setViewState(String value) {
		forms.put(STATE_KEY, value);
	}

	public String getViewState() {
		return forms.get(STATE_KEY);
	}

	public void setEvent(String value) {
		forms.put(EVENT_KEY, value);
	}

	public String getEvent() {
		return forms.get(EVENT_KEY);
	}

	public void setEventTaget(String value) {
		forms.put(EVENT_TAGET_KEY, value);
	}

	public String getEventTaget() {
		return forms.get(EVENT_TAGET_KEY);
	}

	public void setEventArg(String value) {
		forms.put(EVENT_ARG_KEY, value);
	}

	public String getEventArg() {
		return forms.get(EVENT_ARG_KEY);
	}

	public String getValueByKey(String key) {
		return forms.get(key);
	}

	public Map<String, String> getForms() {
		return forms;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static ViewStateForms parse(String content) {

		Formatter formatter = FormatterFactory.getInstance().getFormatter(
				ViewStateForms.class.getName());
		ViewStateForms model = (ViewStateForms) formatter.format(content);
		return model;
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
