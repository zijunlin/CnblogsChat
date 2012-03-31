package com.cnblogs.keyindex.model;

public class Comment {

	private String author;
	private String content;
	private String generalTime;
	private String commentId;
	private String parentId;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGeneralTime() {
		return generalTime;
	}
	public void setGeneralTime(String generalTime) {
		this.generalTime = generalTime;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
