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
 * 利用Jsoup 接口的 Select选择器
 * <p>
 * 与Css Jquery的路径选择器相似
 * </p>
 * ，获取HTMl标签中对应的属性和值
 * 
 * @author linzijun
 * 
 */
class ChatFormatter implements Formatter {

	private static final String liTag = "li[class]";

	/**
	 * 如果无法转换则返回一个长度为0的Chat 列表
	 */
	@Override
	public List<Chat> format(String response) {
		List<Chat> chats = new ArrayList<Chat>();
		if(response==null)
			return chats;
		// 耗时操作
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

		// 某些闪存评论 采用了ajax 方式加载
		if (!element.select("script").isEmpty()) {
			return true;
		}

		// Cnblogs 里部分Comment 没有利用Ajax 加载，
		if (element.select("div.ing_comments").select("li").select("a").size() > 0) {
			return true;
		}

		return false;

	}

}
