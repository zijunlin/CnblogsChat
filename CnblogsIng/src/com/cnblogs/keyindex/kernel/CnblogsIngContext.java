/*
 * ����԰���������Ļ�������Ҫ����洢����asp.net ��վʱ��Ҫ�ṩ�Ļ�����Ϣ������ cookie �� asp.net forms.
 */
package com.cnblogs.keyindex.kernel;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.CookieStore;

import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.model.AspDotNetForms;
import com.cnblogs.keyindex.model.Section;

public class CnblogsIngContext {

	private static CnblogsIngContext instance = null;

	private CookieStore cookieStore = null;
	private AspDotNetForms baseforms = null;

	private CnblogsIngContext() {
	}

	public static CnblogsIngContext getContext() {
		if (instance == null) {
			synchronized (CnblogsIngContext.class) {
				if (instance == null) {
					instance = new CnblogsIngContext();
				}
			}
		}
		return instance;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore value) {
		cookieStore = value;
	}

	public boolean IsAuthorization() {
		return cookieStore != null;
	}

	public void setBaseForms(String eventKey, String eventTaget,
			String eventArg, String viewState) {
		baseforms = new AspDotNetForms();
		baseforms.setEvent(eventKey);
		baseforms.setEventArg(eventArg);
		baseforms.setEventTaget(eventTaget);
		baseforms.setViewState(viewState);
	}

	public AspDotNetForms getBaseForms() {
		return baseforms;
	}

	public void setBaseForms(AspDotNetForms value) {
		baseforms = value;
	}

	public List<Section> getAllSection() {
		List<Section> list = new ArrayList<Section>();
//		list.add(new Section("�����б�",
//				"com.cnblogs.keyindex.FlashMessageActivity.view",
//				R.drawable.message_star));
		list.add(new Section("��Ա��¼",
				"com.cnblogs.keyindex.UserAcitivity.sigin", R.drawable.log_in));
		list.add(new Section("��������",
				"com.cnblogs.keyindex.FlashMessageActivity.Sender",
				R.drawable.send_message));
		return list;
	}
}
