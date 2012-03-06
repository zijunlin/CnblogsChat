package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.cnblogs.keyindex.model.FlashMessage;

/*
 * 
 * 需要重构出去重复代码 2012/03/6
 */
public class MessageSerializer implements Serializer {

	private CommentSerializer comment=new CommentSerializer();
	
	@Override
	public List<FlashMessage> format(String response) {
		if (response == null)
			return null;
		String result = response;
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		FlashMessage message;
		String[] resultList = result.split("<li class=\"entry_");
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
			message.setFeedId(getFeedId(html));
			message.setGeneralTime(getGeneralTime(html));
			message.setHeadImageUrl(getHeadImg(html));
			message.setNewPerson(isNewPerson(html));
			message.setSendContent(getContent(html));
			message.setShine(hasShining(html));
			if(hasComments(message.getFeedId(), html))
			{
				String commentsHtml=getCommentsMessageHtml(message.getFeedId(),html);
				message.addAllComments(comment.serialieComments(commentsHtml));
			}
			return message;

		} catch (Exception e) {
			Log.e("Cnblogs", e.getMessage());
			return null;
		}

	}

	/**
	 * 从html 标签中获取 闪存的作者. 首先搜索第一个\<\/a\>：标签，得到终点 注意标签后的冒号‘：’ 然后以此往回搜索
	 * "target=\"_blank\">"得到起点 最后取起点和终点的字串
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             无法找到作者名
	 */
	private String getName(String subHtml) throws Exception {
		int begin = 0, end = 0;
		end = subHtml.indexOf("</a>：");
		String tag = "\">";
		begin = subHtml.lastIndexOf(tag, end);

		if (end == -1 || begin == -1)
			throw new Exception("Not Found");

		String name = subHtml.substring(begin + tag.length(), end);
		return name;
	}

	/**
	 * 从html标签中获取闪存内容。 首先搜索第一个\</span\>标签，作为终点 然后往会搜索\<span 标签作为起点。
	 * 取子串利用正则表达式除去html 标签
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             无法找到内容
	 */
	private String getContent(String subHtml) throws Exception {
		int begin = 0, end = 0;
		end = subHtml.indexOf("</span>");
		begin = subHtml.lastIndexOf("<span", end);
		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		String content = subHtml.substring(begin, end);

		return splitAndFilterString(content, content.length());
	}

	/**
	 * 利用正则表达式移除html 标签 
	 * @param content
	 * @param length
	 * @return
	 */
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

	/**
	 * 获取发送的大概时间 首先搜索class="ing_time" 字符串，然后以此为起点搜索第一个</a>作为终点
	 * 再以终点往回搜索"target=\"_blank\">" 作为起点 取子串
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             无法获取发送时间
	 */
	private String getGeneralTime(String subHtml) throws Exception {
		int begin = 0, end = 0;
		String tag = "target=\"_blank\">";
		end = subHtml.indexOf("class=\"ing_time\"");
		end = subHtml.indexOf("</a>", end);
		begin = subHtml.lastIndexOf(tag, end);

		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		return subHtml.substring(begin + tag.length(), end);
	}

	/**
	 * 获取是否是新人， 搜索class=\"ing_icon_newbie\"
	 * 
	 * @param subHtml
	 * @return
	 */
	private boolean isNewPerson(String subHtml) {
		String tag = "class=\"ing_icon_newbie\"";

		return subHtml.contains(tag);
	}

	/**
	 * 获取是否有闪存星星， 搜索class="ing_icon_lucky"
	 * 
	 * @param subHtml
	 * @return
	 */
	private boolean hasShining(String subHtml) {
		String tag = "class=\"ing_icon_lucky\"";

		return subHtml.contains(tag);
	}

	/**
	 * 获取作者头像Url 首先搜索src="http://pic.cnblogs.com/face/ 获得其实位置， 然后收拾第一个\" alt=
	 * 作为终点位置
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             无法获取头像资源
	 */
	private String getHeadImg(String subHtml) throws Exception {
		int begin = 0, end = 0;
		String tag = "src=\"http://pic.cnblogs.com/face/";
		String host = "http://pic.cnblogs.com/face/";
		begin = subHtml.indexOf(tag);
		end = subHtml.indexOf("\" alt=", begin);
		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		String urlPath = subHtml.substring(begin + tag.length(), end);
		return host + urlPath;
	}

	/**
	 * 获取闪存Id 首先查找id="feed_content_ 作为起点 然后找到以此为第一个的\"> 作为终点 取子串
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             无法找到ID
	 */
	private String getFeedId(String subHtml) throws Exception {
		int begin = 0, end = 0;
		String tag = "id=\"feed_content_";
		begin = subHtml.indexOf(tag);
		end = subHtml.indexOf("\">", begin);
		if (end == -1 || begin == -1)
			throw new Exception("Not Found");
		return subHtml.substring(begin + tag.length(), end);
	}

	/**
	 * 判断是否有评论， 包含
	 * <ul id="comment_block_FeedID">
	 * 且不包含
	 * <li style="display: none; "/>
	 * 
	 * @param feedId
	 *            闪存ID
	 * @param subHtml
	 * @return
	 */
	private boolean hasComments(String feedId, String subHtml) {
		if (feedId == null || subHtml == null)
			return false;
		String tag = String.format("<ul id=\"comment_block_%1$s", feedId);

		int begin = 0, end = 0;

		begin = subHtml.indexOf(tag);
		end = subHtml.indexOf("<li style=\"display:", begin);

		return end == -1;

	}

	/**
	 * 获取闪存评论，该方法包含Html代码
	 * 
	 * @param feedId
	 * @param subHtml
	 * @return
	 */
	private String getCommentsMessageHtml(String feedId, String subHtml) {
		int begin = 0, end = 0;

		String tag = String.format("<ul id=\"comment_block_%1$s", feedId);
		begin = subHtml.indexOf(tag);
		end = subHtml.indexOf("</ul>", begin);
		return subHtml.substring(begin + tag.length(), end);
	}

	

}
