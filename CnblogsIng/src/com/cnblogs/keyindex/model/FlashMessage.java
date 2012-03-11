/**
 * 闪存实体类
 */
package com.cnblogs.keyindex.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class FlashMessage {

	private String author;
	private String content;
	private String generalTime;
	private String ingId;
	private String headImageUrl;

	private boolean isShining = false;
	private boolean isNewPerson = false;
	private boolean defineHeaderImage=true;
	private final String DEFAULT_HEADER_IMG_URL = "http://pic.cnblogs.com/face/sample_face.gif";

	private List<FlashMessage> comments;

	public FlashMessage() {
		comments = new ArrayList<FlashMessage>();
	}

	
	public boolean HasDefineHeaderImage()
	{
		return defineHeaderImage;
	}
	
	public boolean IsNewPerson() {
		return isNewPerson;
	}

	public void setNewPerson(boolean value) {
		isNewPerson = value;
	}

	/**
	 * 是否获得闪存星星
	 * 
	 * @return
	 */
	public boolean IsShining() {
		return isShining;
	}

	public void setShine(boolean value) {
		isShining = value;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String url) {
		headImageUrl = url;
		if(url.contentEquals(DEFAULT_HEADER_IMG_URL))
		{
			defineHeaderImage=false;
		}
	}
	public List<FlashMessage> getCommentsMessage() {
		return comments;
	}

	public void addOneComment(FlashMessage value) {
		comments.add(value);
	}

	public void addAllComments(Collection<FlashMessage> list) {
		if (list != null)
			comments.addAll(list);
	}

	public void addAllComments(int postion, Collection<FlashMessage> list) {
		if (postion >= 0 && postion < comments.size())
			comments.addAll(postion, list);
	}

	public void clearComments() {
		comments.clear();
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

}
