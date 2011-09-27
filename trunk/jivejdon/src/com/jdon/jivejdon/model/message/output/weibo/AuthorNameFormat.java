/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.model.message.output.weibo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.message.weibo.InFilterAuthor;
import com.jdon.util.Debug;

/**
 * 1.进入管理界面 /jivejdon/admin/
 * 
 * 2.全局过滤 --> 添加过虑器（底部）
 * 
 * 3.输入com.jdon.jivejdon.model.message.output.weibo.AuthorNameFormat
 * 
 * 4在”可用过滤器“中出现'AuthorNameFormat' 选择'AuthorNameFormat'，点按”安装“即可
 * 
 * 5.将AuthorNameFormat调整到QuoteRegexFilter之前
 * 
 * 
 * @author banq
 * @see InFilterAuthor
 */
public class AuthorNameFormat implements MessageRenderSpecification {
	private final static String module = AuthorNameFormat.class.getName();

	private String authorURL = "<a href='/jivejdon/profile.jsp?user=#user#' "
			+ " onmouseover='loadWLJSWithP(this, initUsersW)' class='Users ajax_userId=#user#'" + " id='users' >#user#</a>";

	public ForumMessage render(ForumMessage message) {
		try {
			MessageVO messageVO = message.getMessageVO();
			messageVO.setBody(convertTags(messageVO.getBody()));
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	private String convertTags(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		// InFilterAuthor
		String patt = "(\\[author\\])([^\\[]+)(\\[/author\\])";
		Pattern p = Pattern.compile(patt);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			m.appendReplacement(sb, authorURL.replaceAll("#user#", m.group(2)));
			result = m.find();
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public String getAuthorURL() {
		return authorURL;
	}

	public void setAuthorURL(String authorURL) {
		this.authorURL = authorURL;
	}

}
