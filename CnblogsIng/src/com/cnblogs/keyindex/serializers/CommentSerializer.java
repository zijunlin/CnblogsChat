package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;

public class CommentSerializer {

	
	
	
	
	
	
	/**
	 * ªÒ»°∆¿¬€
	 * 
	 * @param subHtml
	 * @return
	 */
	public List<FlashMessage> serialieComments(String subHtml) {
		if (subHtml == null)
			return null;
		String result = subHtml;
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		FlashMessage message;
		String[] resultList = result.split("<li id=");
		for (int i = 0; i < resultList.length; i++) {
			message = serializerMdoel(resultList[i]);
			if (message != null)
				list.add(message);
		}

		return list;
	}

	private FlashMessage serializerMdoel(String html) {
		FlashMessage message = new FlashMessage();

		try {

			message.setAuthorName(getName(html));
			message.setGeneralTime(getGeneralTime(html));
			message.setSendContent(getContent(html));
			return message;

		} catch (Exception e) {
			return null;
		}
	}

	private String getContent(String subHtml) throws Exception {
		int begin = 0, end = 0;
		String tag="</a>£∫";
		begin = subHtml.indexOf(tag);
		end=subHtml.indexOf("<a", begin);
		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		return subHtml.substring(begin + tag.length(), end);
	}

	private String getGeneralTime(String subHtml) throws Exception {
		int begin = 0, end = 0;
		String tag=">";
		end = subHtml.indexOf("class=\"ing_comment_time\"");
		end = subHtml.indexOf("</a>", end);
		begin = subHtml.lastIndexOf(tag, end);

		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		return subHtml.substring(begin + tag.length(), end);
	}

	private String getName(String subHtml) throws Exception {
		int begin = 0, end = 0;
		end = subHtml.indexOf("</a>£∫");
		String tag = ">";
		begin = subHtml.lastIndexOf(tag, end);

		if (end == -1 || begin == -1)
			throw new Exception("Not Found");

		String name = subHtml.substring(begin + tag.length(), end);
		return name;
	}
}
