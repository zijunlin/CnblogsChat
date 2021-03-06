package com.cnblogs.keyindex.formatters;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.cnblogs.indexkey.formatter.IFormatter;
import com.cnblogs.keyindex.model.FlashMessage;

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
 class JsoupMessageFormatter implements IFormatter{


	@Override
	public  List<FlashMessage> format(String response) {
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		// 耗时操作
		Document doc = Jsoup.parse(response);

		FlashMessage model;
		Elements ul = doc.select("li[class]");
		for (Element item : ul) {
			model =  (FlashMessage) serializerFlashMessage(item);
			if (model != null) {
				list.add(model);
			}
		}

		return  list;
	}

	private FlashMessage serializerFlashMessage(Element element) {
		try {
			final FlashMessage model = new FlashMessage();
			model.setHeadImageUrl(element.select("div.feed_avatar")
					.select("img").attr("src"));
			String feedId = element.select("div.feed_body").attr("id");
			model.setFeedId(feedId.replace("feed_content_", ""));
			model.setSendContent(element.select("span.ing_body").text());
			model.setAuthorName(element.select("div.feed_body").select("a")
					.first().text());
			model.setNewPerson(!element.select("img.ing_icon_newbie").isEmpty());
			model.setShine(!element.select("img.ing_icon_lucky").isEmpty());
			model.setGeneralTime(element.select("a.ing_time").text());
			model.setHasCommnets(hasComments(element));
			return model;
		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

	private boolean hasComments(Element element) {

		//某些闪存评论 采用了ajax 方式加载
		if (!element.select("script").isEmpty()) {
			return true;
		}
		
		//Cnblogs 里部分Comment 没有利用Ajax 加载，
		if (element.select("div.ing_comments").select("li").select("a").size() >0 ) {
			return true;
		}

	
		return false;

	}

	

}
