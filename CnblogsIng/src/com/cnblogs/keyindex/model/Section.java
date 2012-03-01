package com.cnblogs.keyindex.model;

public class Section {

	private String name;
	private String activityAction;
	private int logoResId;

	public Section() {
	}

	public Section(String name, String action, int logo) {
		this.name = name;
		activityAction = action;
		logoResId = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getAction() {
		return activityAction;
	}

	public void setAction(String value) {
		activityAction = value;
	}

	public int getLogoResId() {
		return logoResId;
	}

	public void setLogoResId(int value) {
		logoResId = value;
	}
}
