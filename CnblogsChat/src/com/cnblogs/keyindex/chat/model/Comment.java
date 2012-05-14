package com.cnblogs.keyindex.chat.model;

import java.util.List;

import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.formatters.Formatter;

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

	public static List<Comment> parse(String content) {
		Formatter formatter = FormatterFactory.getInstance().getFormatter(
				Comment.class.getName());
		@SuppressWarnings("unchecked")
		List<Comment> list = (List<Comment>) formatter.format(content);

		return list;

	}
}
