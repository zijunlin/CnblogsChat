package com.cnblogs.keyindex.chat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.formatters.Formatter;

public class Chat {

	private String author;
	private String content;
	private String generalTime;
	private String id;
	private String headImageUrl;
	private final String DEFAULT_HEADER_IMG_URL = "http://pic.cnblogs.com/face/sample_face.gif";

	private String contentUrl;

	private boolean isShining = false;
	private boolean isNewPerson = false;
	private boolean needLoadComments = false;

	private List<Comment> comments;

	public Chat() {
		comments = new ArrayList<Comment>();
	}

	public boolean IsDefaultHeaderImage() {
		return headImageUrl.contentEquals(DEFAULT_HEADER_IMG_URL);
	}

	/**
	 * 获取是否为新手
	 * 
	 * @return True 表示新会员
	 */
	public boolean IsNewPerson() {
		return isNewPerson;
	}

	/**
	 * 设置是否为新手
	 * 
	 * @param value
	 *            True表示新会员
	 */
	public void setIsNewPerson(boolean value) {
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

	/**
	 * 设置闪存幸运星
	 * 
	 * @param value
	 *            True表示已获得闪存幸运星
	 */
	public void setIsShining(boolean value) {
		isShining = value;
	}

	/**
	 * 设置闪存中URL 的资源地址
	 * 
	 * @param value
	 */
	public void setContentUrl(String value) {
		contentUrl = value;
	}

	/**
	 * 获取闪存中URL的资源地址
	 * 
	 * @return
	 */
	public String getContentUrl() {
		return contentUrl;
	}

	/**
	 * 获取作者头像URL地址
	 * 
	 * @return
	 */
	public String getHeadImageUrl() {
		return headImageUrl;
	}

	/**
	 * 设置作者头像URL地址
	 * 
	 * @param url
	 */
	public void setHeadImageUrl(String url) {
		headImageUrl = url;
	}

	/**
	 * 获取评论信息
	 * 
	 * @return
	 */
	public List<Comment> getCommentsMessage() {
		return comments;
	}

	/**
	 * 添加评论
	 * 
	 * @param value
	 */
	public void addComment(Comment value) {
		value.setParentId(id);
		comments.add(value);
	}

	/**
	 * 添加多条评论到列表尾部
	 * 
	 * @param list
	 */
	public void addAllComments(Collection<Comment> list) {
		if (list != null) {
			for (Comment item : list) {
				item.setParentId(id);
			}
			comments.addAll(list);

		}
	}

	/**
	 * 添加多条评论道指定位置
	 * 
	 * @param postion
	 *            指定的位置
	 * @param list
	 */
	public void addAllComments(int postion, Collection<Comment> list) {
		if (postion >= 0 && postion < comments.size() && list != null) {
			for (Comment item : list) {
				item.setParentId(id);
			}
			comments.addAll(postion, list);
		}
	}

	/**
	 * 清楚评论
	 */
	public void clearComments() {
		comments.clear();
	}

	public String getChatId() {
		return id;
	}

	public void setChatId(String value) {
		id = value;
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

	public String getContent() {
		return content;
	}

	public void setContent(String value) {
		content = value;
	}

	public void setNeedLoadComments(boolean needLoadComments) {
		this.needLoadComments = needLoadComments;
	}

	public boolean isNeedLoadComments() {
		return needLoadComments;
	}

	public boolean hasComments() {
		return comments.size() > 0;
	}

	public static List<Chat> parse(String content) {
		Formatter formatter = FormatterFactory.getInstance().getFormatter(
				Chat.class.getName());
		@SuppressWarnings("unchecked")
		List<Chat> list = (List<Chat>) formatter.format(content);
		return list;
	}

}
