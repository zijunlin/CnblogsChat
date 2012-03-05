/**
 * …¡¥Ê µÃÂ¿‡
 */
package com.cnblogs.keyindex.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;

public class FlashMessage {

	private String author;
	private String content;
	private Date sendTime;
	private String generalTime;
	private String ingId;
	private String headImageUrl;
	private Bitmap headImage;
	private boolean isShining=false;

	private List<FlashMessage> followMessage;

	public FlashMessage() {
		followMessage = new ArrayList<FlashMessage>();
	}
	
	public boolean IsShining()
	{
		return isShining;
	}
	
	public void setShine(boolean value)
	{
		isShining=value;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String url) {
		headImageUrl = url;
	}

	public Bitmap getHeadImage() {
		return headImage;
	}

	public void setHeadImage(Bitmap value) {
		headImage = value;
	}

	public List<FlashMessage> getFollowMessage() {
		return followMessage;
	}

	public void addOneFollowMessage(FlashMessage value) {
		followMessage.add(value);
	}

	public void addAllFollowMessage(Collection<FlashMessage> list) {
		followMessage.addAll(list);
	}

	public void addAllFollowMessage(int postion, Collection<FlashMessage> list) {
		if (postion >= 0 && postion < followMessage.size())
			followMessage.addAll(postion, list);
	}

	public void clearFollowMessage() {
		followMessage.clear();
	}

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
			e.printStackTrace();
		}
	}

}
