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
	 * ��ȡ�Ƿ�Ϊ����
	 * 
	 * @return True ��ʾ�»�Ա
	 */
	public boolean IsNewPerson() {
		return isNewPerson;
	}

	/**
	 * �����Ƿ�Ϊ����
	 * 
	 * @param value
	 *            True��ʾ�»�Ա
	 */
	public void setIsNewPerson(boolean value) {
		isNewPerson = value;
	}

	/**
	 * �Ƿ�����������
	 * 
	 * @return
	 */
	public boolean IsShining() {
		return isShining;
	}

	/**
	 * ��������������
	 * 
	 * @param value
	 *            True��ʾ�ѻ������������
	 */
	public void setIsShining(boolean value) {
		isShining = value;
	}

	/**
	 * ����������URL ����Դ��ַ
	 * 
	 * @param value
	 */
	public void setContentUrl(String value) {
		contentUrl = value;
	}

	/**
	 * ��ȡ������URL����Դ��ַ
	 * 
	 * @return
	 */
	public String getContentUrl() {
		return contentUrl;
	}

	/**
	 * ��ȡ����ͷ��URL��ַ
	 * 
	 * @return
	 */
	public String getHeadImageUrl() {
		return headImageUrl;
	}

	/**
	 * ��������ͷ��URL��ַ
	 * 
	 * @param url
	 */
	public void setHeadImageUrl(String url) {
		headImageUrl = url;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public List<Comment> getCommentsMessage() {
		return comments;
	}

	/**
	 * �������
	 * 
	 * @param value
	 */
	public void addComment(Comment value) {
		value.setParentId(id);
		comments.add(value);
	}

	/**
	 * ��Ӷ������۵��б�β��
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
	 * ��Ӷ������۵�ָ��λ��
	 * 
	 * @param postion
	 *            ָ����λ��
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
	 * �������
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
