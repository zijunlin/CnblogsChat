package com.cnblogs.keyindex.serializers;

import java.util.ArrayList;
import java.util.List;

import com.cnblogs.keyindex.model.FlashMessage;
import com.cnblogs.keyindex.model.res.AspDotNetForms;

public class HtmlEntitySerialization {

	public static AspDotNetForms serialieAspDotNetState(String html) {
		
	}

	public static List<FlashMessage> serialieFlashMessage(String html) {
		if (html == null)
			return null;
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		FlashMessage message;
		String[] htmlList = html.split("<li class=\"entry_");
		int begin, end;
		String tag = null;
		for (int i = 0; i < htmlList.length; i++) {
			try {
				message = new FlashMessage();
				end = htmlList[i].indexOf("</a>：");
				tag = "target=\"_blank\">";
				begin = htmlList[i].lastIndexOf(tag, end);

				if (end == -1 || begin == -1)
					continue;

				String name = htmlList[i].substring(begin + tag.length(), end);
				message.setAuthorName(name);

				
				end = htmlList[i].indexOf("</span>", end);
				
				begin= htmlList[i].lastIndexOf("<span", end);
				String content = htmlList[i].substring(begin ,
						end);
				
				message.setSendContent(splitAndFilterString(content,content.length()));

				tag = "target=\"_blank\">";
				begin = htmlList[i].indexOf(tag, end);
				end = htmlList[i].indexOf("</a>", begin);
				String time = htmlList[i].substring(begin + tag.length(), end);
				message.setGeneralTime(time);
				list.add(message);
			} catch (Exception e) {

			}

		}

		return list;
	}

	
	/** 
     * 删除input字符串中的html格式 
     *  
     * @param input 
     * @param length 
     * @return 
     */  
    public static String splitAndFilterString(String input, int length) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
        }  
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
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
}
