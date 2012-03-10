package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.cnblogs.keyindex.model.FlashMessage;

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
public class JsoupMessageSerializer implements Serializer {

	@Override
	public Object format(String response) {
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		// ��ʱ����
		Document doc = Jsoup.parse(response);

		FlashMessage model;
		Elements ul = doc.select("li[class]");
		for (Element item : ul) {
			model = serializerFlashMessage(item);
			if (model != null) {
				list.add(model);
			}
		}

		return list;
	}

	private FlashMessage serializerFlashMessage(Element element) {
		try {
			FlashMessage model = new FlashMessage();
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
			return model;
		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

//	private List<FlashMessage> serializerComments(Elements elements) {
//
//		if (!hasComments(elements))
//			return null;
//
//		List<FlashMessage> list = new ArrayList<FlashMessage>();
//		FlashMessage comment;
//		for (Element item : elements) {
//			comment = serializerComment(item);
//			if (comment != null) {
//				list.add(comment);
//			}
//		}
//
//		return list;
//	}
//
//	private boolean hasComments(Elements elements) {
//
//		if (elements.select("li[id]").isEmpty())
//			return false;
//		else
//			return true;
//	}
//
//	private FlashMessage serializerComment(Element element) {
//		try {
//			FlashMessage model = new FlashMessage();
//			String feedId = element.attr("id");
//			model.setFeedId(feedId.replace("comment_", ""));
//
//			model.setAuthorName(element.select("a[id]").text());
//			model.setGeneralTime(element.select("ing_comment_time").text());
//
//			// element.text()���ȡ���ߣ�ʱ�䣬���ݣ����Խ����ߺ�ʱ���滻Ϊ���ַ���
//			model.setSendContent(element.text()
//					.replace(model.getAuthorName(), "")
//					.replace(model.getGeneralTime(), ""));
//
//			return model;
//		} catch (Exception e) {
//			Log.e("Cnblogs", e.getMessage());
//			return null;
//		}
//	}

}
