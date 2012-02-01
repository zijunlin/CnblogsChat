package com.cnblogs.keyindex.kernel;


import java.util.List;
import org.apache.http.client.CookieStore;

import android.content.Context;

import com.cnblogs.keyindex.model.Section;
import com.cnblogs.keyindex.model.res.Resource;

public class CnblogsIngContext {

	private static CnblogsIngContext instance = null;

	private CookieStore cookieStore = null;
	private SectionTable sections;
	private Resource baseForms;


	private CnblogsIngContext() {	
		
	}

	public void initContext()
	{
		sections=new SectionTable();
		SectionRegister register=new SectionRegister();
		register.onEnroll(this);
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

	
	public List<Section> getAllSection()
	{
		return sections.getAllSction();
	}
	
	protected void enrollSection(String name,int logoResId,String action)
	{
		sections.regSection(new Section(name, action, logoResId));
	}
	
	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore value) {
		cookieStore = value;
	}
	
	
	public Resource getAspDotNetForms()
	{
		return baseForms;
	}
	public void setAspDotNetForms(Resource forms)
	{
		baseForms=forms;
	}
}
