package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.util.Log;

import com.cnblogs.keyindex.model.FlashMessage;

public class CommentSerializer implements Serializer {

	@Override
	public Object format(String response) {

		if (response == null)
			return null;
		List<FlashMessage> list = new ArrayList<FlashMessage>();

		String result = toHtmlString(response);
		Document doc = Jsoup.parse(result);

		FlashMessage comment;

		Elements ul = doc.select("li[id]");
		for (Element item : ul) {
			comment = serializerComment(item);
			if (comment != null)
				list.add(comment);
		}

		return list;
	}

	private String toHtmlString(String response) {
		JSONObject jo;
		try {
			jo = new JSONObject(response);

			String htmlStr = jo.getString("d");

			return htmlStr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	private FlashMessage serializerComment(Element element) {
		try {
			final FlashMessage model = new FlashMessage();

			String feedId = element.select("li").attr("id");
			model.setFeedId(feedId.replace("comment_", ""));
			model.setAuthorName(element.select(
					"#comment_author_" + model.getFeedId()).text());

			model.setGeneralTime(element.select("a.ing_comment_time").text());

			String content =element.ownText();
			
			model.setSendContent(content.substring(1));

			return model;
		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

}
