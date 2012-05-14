package com.cnblogs.keyindex.chat.formatters;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.cnblogs.keyindex.chat.model.Chat;

/**
 * ����Jsoup �ӿڵ� Selectѡ����
 * <p>
 * ��Css Jquery��·��ѡ��������
 * </p>
 * ����ȡHTMl��ǩ�ж�Ӧ�����Ժ�ֵ
 * 
 * @author linzijun
 * 
 */
class ChatFormatter implements Formatter {

	private static final String liTag = "li[class]";

	/**
	 * ����޷�ת���򷵻�һ������Ϊ0��Chat �б�
	 */
	@Override
	public List<Chat> format(String response) {
		List<Chat> chats = new ArrayList<Chat>();
		if(response==null)
			return chats;
		// ��ʱ����
		Document doc = Jsoup.parse(response);

		Chat model;
		Elements ulTag = doc.select(liTag);
		for (Element item : ulTag) {
			model = (Chat) serializerChat(item);
			if (model != null) {
				chats.add(model);
			}
		}
		return chats;
	}

	private Chat serializerChat(Element htmlChat) {
		try {
			final Chat model = new Chat();
			model.setHeadImageUrl(htmlChat.select("div.feed_avatar")
					.select("img").attr("src"));
			String feedId = htmlChat.select("div.feed_body").attr("id");
			model.setChatId(feedId.replace("feed_content_", ""));
			model.setContent(htmlChat.select("span.ing_body").text());
			model.setAuthorName(htmlChat.select("div.feed_body").select("a")
					.first().text());
			model.setIsNewPerson(!htmlChat.select("img.ing_icon_newbie")
					.isEmpty());
			model.setIsShining(!htmlChat.select("img.ing_icon_lucky").isEmpty());
			model.setGeneralTime(htmlChat.select("a.ing_time").text());
			model.setNeedLoadComments(hasComments(htmlChat));
			return model;
		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

	private boolean hasComments(Element element) {

		// ĳЩ�������� ������ajax ��ʽ����
		if (!element.select("script").isEmpty()) {
			return true;
		}

		// Cnblogs �ﲿ��Comment û������Ajax ���أ�
		if (element.select("div.ing_comments").select("li").select("a").size() > 0) {
			return true;
		}

		return false;

	}

}
