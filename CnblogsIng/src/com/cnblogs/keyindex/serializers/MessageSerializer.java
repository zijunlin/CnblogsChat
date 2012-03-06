package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.cnblogs.keyindex.model.FlashMessage;

/*
 * 
 * ��Ҫ�ع���ȥ�ظ����� 2012/03/6
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
	 * ��html ��ǩ�л�ȡ ���������. ����������һ��\<\/a\>����ǩ���õ��յ� ע���ǩ���ð�š����� Ȼ���Դ���������
	 * "target=\"_blank\">"�õ���� ���ȡ�����յ���ִ�
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             �޷��ҵ�������
	 */
	private String getName(String subHtml) throws Exception {
		int begin = 0, end = 0;
		end = subHtml.indexOf("</a>��");
		String tag = "\">";
		begin = subHtml.lastIndexOf(tag, end);

		if (end == -1 || begin == -1)
			throw new Exception("Not Found");

		String name = subHtml.substring(begin + tag.length(), end);
		return name;
	}

	/**
	 * ��html��ǩ�л�ȡ�������ݡ� ����������һ��\</span\>��ǩ����Ϊ�յ� Ȼ����������\<span ��ǩ��Ϊ��㡣
	 * ȡ�Ӵ�����������ʽ��ȥhtml ��ǩ
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             �޷��ҵ�����
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
	 * ����������ʽ�Ƴ�html ��ǩ 
	 * @param content
	 * @param length
	 * @return
	 */
	private String splitAndFilterString(String content, int length) {
		if (content == null || content.trim().equals("")) {
			return "";
		}
		// ȥ������htmlԪ��,
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
	 * ��ȡ���͵Ĵ��ʱ�� ��������class="ing_time" �ַ�����Ȼ���Դ�Ϊ���������һ��</a>��Ϊ�յ�
	 * �����յ���������"target=\"_blank\">" ��Ϊ��� ȡ�Ӵ�
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             �޷���ȡ����ʱ��
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
	 * ��ȡ�Ƿ������ˣ� ����class=\"ing_icon_newbie\"
	 * 
	 * @param subHtml
	 * @return
	 */
	private boolean isNewPerson(String subHtml) {
		String tag = "class=\"ing_icon_newbie\"";

		return subHtml.contains(tag);
	}

	/**
	 * ��ȡ�Ƿ����������ǣ� ����class="ing_icon_lucky"
	 * 
	 * @param subHtml
	 * @return
	 */
	private boolean hasShining(String subHtml) {
		String tag = "class=\"ing_icon_lucky\"";

		return subHtml.contains(tag);
	}

	/**
	 * ��ȡ����ͷ��Url ��������src="http://pic.cnblogs.com/face/ �����ʵλ�ã� Ȼ����ʰ��һ��\" alt=
	 * ��Ϊ�յ�λ��
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             �޷���ȡͷ����Դ
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
	 * ��ȡ����Id ���Ȳ���id="feed_content_ ��Ϊ��� Ȼ���ҵ��Դ�Ϊ��һ����\"> ��Ϊ�յ� ȡ�Ӵ�
	 * 
	 * @param subHtml
	 * @return
	 * @throws Exception
	 *             �޷��ҵ�ID
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
	 * �ж��Ƿ������ۣ� ����
	 * <ul id="comment_block_FeedID">
	 * �Ҳ�����
	 * <li style="display: none; "/>
	 * 
	 * @param feedId
	 *            ����ID
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
	 * ��ȡ�������ۣ��÷�������Html����
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
