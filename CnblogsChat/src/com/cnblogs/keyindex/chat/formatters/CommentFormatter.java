package com.cnblogs.keyindex.chat.formatters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.util.Log;

import com.cnblogs.keyindex.chat.model.Comment;

class CommentFormatter implements Formatter {

	/**
	 * 如果无法转换则返回 一个长度为0的Comments列表 
	 */
	@Override
	public List<Comment> format(String response) {

		List<Comment> list = new ArrayList<Comment>();
		if (response == null)
			//返回一个 为0 的数组。 方便Client段判断
			return list;
		String result = toHtmlString(response);
		Document doc = Jsoup.parse(result);
		Comment comment;
		Elements ul = doc.select("li[id]");
		for (Element item : ul) {
			comment = serializer(item);
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

	private Comment serializer(Element element) {
		try {
			final Comment model = new Comment();

			String feedId = element.select("li").attr("id");
			model.setCommentId(feedId.replace("comment_", ""));
			model.setAuthor(element.select(
					"#comment_author_" + model.getCommentId()).text());

			model.setGeneralTime(element.select("a.ing_comment_time").text());

			String content = element.ownText();

			model.setContent(content.substring(1));

			return model;
		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

}
