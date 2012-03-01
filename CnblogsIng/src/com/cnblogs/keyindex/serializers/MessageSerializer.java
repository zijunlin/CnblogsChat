package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;

public class MessageSerializer implements Serializer {

	@Override
	public List<FlashMessage> format(String response) {
		if (response == null)
			return null;
		String result = response;
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		FlashMessage message;
		String[] resultList = result.split("<li class=\"entry_");
		int begin, end;
		String tag = null;
		for (int i = 0; i < resultList.length; i++) {
			try {
				message = new FlashMessage();
				end = resultList[i].indexOf("</a>：");
				tag = "target=\"_blank\">";
				begin = resultList[i].lastIndexOf(tag, end);

				if (end == -1 || begin == -1)
					continue;

				String name = resultList[i]
						.substring(begin + tag.length(), end);
				message.setAuthorName(name);

				end = resultList[i].indexOf("</span>", end);

				begin = resultList[i].lastIndexOf("<span", end);
				String content = resultList[i].substring(begin, end);

				message.setSendContent(splitAndFilterString(content,
						content.length()));

				tag = "target=\"_blank\">";
				begin = resultList[i].indexOf(tag, end);
				end = resultList[i].indexOf("</a>", begin);
				String time = resultList[i]
						.substring(begin + tag.length(), end);
				message.setGeneralTime(time);
				list.add(message);
			} catch (Exception e) {

			}

		}

		return list;
	}

	private String splitAndFilterString(String content, int length) {
		if (content == null || content.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

}
