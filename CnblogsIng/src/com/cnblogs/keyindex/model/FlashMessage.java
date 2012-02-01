package com.cnblogs.keyindex.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlashMessage {

	private String author;
	private String content;
	private Date sendTime;
	private String generalTime;
	private String ingId;

	public String getFeedId() {
		return ingId;

	}

	public void setFeedId(String id) {
		ingId = id;
	}

	public String getGeneralTime() {
		return generalTime;
	}

	public void setGeneralTime(String value) {
		generalTime = value;
	}

	public String getAuthorName() {
		return author;
	}

	public void setAuthorName(String name) {
		author = name;
	}

	public String getSendContent() {
		return content;
	}

	public void setSendContent(String value) {
		content = value;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public String getSendTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return sdf.format(sendTime);
	}

	public void setSendTime(Date time) {
		sendTime = time;
	}

	public void setSendTimeStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			sendTime = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
