package com.cnblogs.keyindex.chat.formatters.test;

import java.util.List;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.formatters.FormatterFactory;
import com.cnblogs.keyindex.chat.formatters.Formatter;
import com.cnblogs.keyindex.chat.model.Comment;

import android.test.AndroidTestCase;

public class CommentFormatterTest extends AndroidTestCase {

	Formatter formatter;

	protected void setUp() throws Exception {
		FormatterFactory factory = FormatterFactory.getInstance();
		factory.clear();
		String[] modelClassNames = mContext.getResources().getStringArray(
				R.array.ModelClassNames);
		String[] formatterClassNames = mContext.getResources().getStringArray(
				R.array.FormatterClassNames);
		factory.bindModelToFormatter(modelClassNames, formatterClassNames);
		formatter = FormatterFactory.getInstance().getFormatter(
				Comment.class.getName());
		super.setUp();
	}

	public void test_Null_Parse() {
		String content = null;
		@SuppressWarnings("unchecked")
		List<Comment> list = (List<Comment>) formatter.format(content);
		assertEquals(list.size(), 0);
	}

	public void test_CanNot_Parse() {
		String content = "sgasegweag";
		@SuppressWarnings("unchecked")
		List<Comment> list = (List<Comment>) formatter.format(content);
		assertEquals(list.size(), 0);
	}

	
// TODO 	
//	public void test_Parse() {
//		StringBuilder contentBuilder = new StringBuilder();
//		contentBuilder.append("{");
//		contentBuilder.append("\"d\":");
//		String content = "<ul id=\"comment_block_263568\"><li id=\"comment_276577\"><a title=\"��Ӧ��5-10 09:18\" "
//				+ "onclick=\"ing_reply(263568,276577);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276577\" title=\"Angkor����ҳ\" href=\"/u/wujiakun/\">Angkor</a>"
//				+ "��XML��ʽ<a class=\"ing_comment_time\" title=\"��Ӧ�� 5-10 09:18:56\">42����ǰ</a>"
//				+ "</li><li id=\"comment_276578\"><a title=\"��Ӧ��5-10 09:22\""
//				+ " onclick=\"ing_reply(263568,276578);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276578\" title=\"know@more����ҳ\" href=\"/u/know/\">know@more</a>"
//				+ "��������ʲô��һ���Ǳ�������� �� �Էָ����ָ���ַ�������<a class=\"ing_comment_time\""
//				+ "title=\"��Ӧ�� 5-10 09:22:13\">39����ǰ</a></li><li id=\"comment_276579\">"
//				+ "<a title=\"��Ӧ��5-10 09:24\" onclick=\"ing_reply(263568,276579);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\">"
//				+ "</a><a id=\"comment_author_276579\" title=\"xiaotie����ҳ\" href=\"/u/xiaotie/\">xiaotie</a>"
//				+ "��@know@more���������ʺź�����Ǹ�����<a class=\"ing_comment_time\" title=\"��Ӧ�� 5-10 09:24:54\">36����ǰ</a>"
//				+ "</li><li id=\"comment_276580\"><a title=\"��Ӧ��5-10 09:34\""
//				+ " onclick=\"ing_reply(263568,276580);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276580\" title=\"know@more����ҳ\" href=\"/u/know/\">know@more</a>"
//				+ "��@xiaotie���ö��������ݱ���<a class=\"ing_comment_time\" title=\"��Ӧ�� 5-10 09:34:32\">27����ǰ</a></li></ul>";
//		contentBuilder.append(content);
//		contentBuilder.append("}");
//
//		@SuppressWarnings("unchecked")
//		List<Comment> list = (List<Comment>) formatter.format(content);
//		boolean condition = list.size() > 0;
//		assertTrue(condition);
//		Comment comment = list.get(1);
//		assertEquals("����ٳ��", comment.getAuthor());
//		assertEquals("@��ҶƮ��??? ", comment.getContent());
//	}
}
