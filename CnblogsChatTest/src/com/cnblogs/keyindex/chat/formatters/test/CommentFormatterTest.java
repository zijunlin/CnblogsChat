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
//		String content = "<ul id=\"comment_block_263568\"><li id=\"comment_276577\"><a title=\"回应于5-10 09:18\" "
//				+ "onclick=\"ing_reply(263568,276577);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276577\" title=\"Angkor的主页\" href=\"/u/wujiakun/\">Angkor</a>"
//				+ "：XML格式<a class=\"ing_comment_time\" title=\"回应于 5-10 09:18:56\">42分钟前</a>"
//				+ "</li><li id=\"comment_276578\"><a title=\"回应于5-10 09:22\""
//				+ " onclick=\"ing_reply(263568,276578);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276578\" title=\"know@more的主页\" href=\"/u/know/\">know@more</a>"
//				+ "：具体是什么？一般是保存二进制 或 以分隔符分割的字符串数据<a class=\"ing_comment_time\""
//				+ "title=\"回应于 5-10 09:22:13\">39分钟前</a></li><li id=\"comment_276579\">"
//				+ "<a title=\"回应于5-10 09:24\" onclick=\"ing_reply(263568,276579);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\">"
//				+ "</a><a id=\"comment_author_276579\" title=\"xiaotie的主页\" href=\"/u/xiaotie/\">xiaotie</a>"
//				+ "：@know@more：具体是问号后面的那个东东<a class=\"ing_comment_time\" title=\"回应于 5-10 09:24:54\">36分钟前</a>"
//				+ "</li><li id=\"comment_276580\"><a title=\"回应于5-10 09:34\""
//				+ " onclick=\"ing_reply(263568,276580);return false;\" href=\"###\">"
//				+ "<img alt=\"\" src=\"http://static.cnblogs.com/images/quote.gif\"></a>"
//				+ "<a id=\"comment_author_276580\" title=\"know@more的主页\" href=\"/u/know/\">know@more</a>"
//				+ "：@xiaotie：用二进制数据保存<a class=\"ing_comment_time\" title=\"回应于 5-10 09:34:32\">27分钟前</a></li></ul>";
//		contentBuilder.append(content);
//		contentBuilder.append("}");
//
//		@SuppressWarnings("unchecked")
//		List<Comment> list = (List<Comment>) formatter.format(content);
//		boolean condition = list.size() > 0;
//		assertTrue(condition);
//		Comment comment = list.get(1);
//		assertEquals("天生俪姿", comment.getAuthor());
//		assertEquals("@落叶飘：??? ", comment.getContent());
//	}
}
