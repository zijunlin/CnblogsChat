package com.cnblogs.keyindex.model;

public class Section {


	private String name;
	private int logoUriResId;
	private String activityAction;

	public Section(String name, String action) {
		this(name, action, -1);
	}

	public Section(String name, String action, int logoResId) {
		this.name = name;
		logoUriResId = logoResId;
		activityAction = action;
		

	}

	

	public void setName(String value) {
		name = value;
	}

	public String getName() {
		return name;
	}

	public void setAction(String value) {
		activityAction = value;
	}

	public String getAction() {
		return activityAction;
	}

	public void setLogoUri(int resId) {
		logoUriResId = resId;
	}

	public int getLogoUri() {
		return logoUriResId;
	}

}
