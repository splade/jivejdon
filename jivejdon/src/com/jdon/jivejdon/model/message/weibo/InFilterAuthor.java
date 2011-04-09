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
package com.jdon.jivejdon.model.message.weibo;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.message.props.InFilterPosterIP;
import com.jdon.util.Debug;

/**
 * pick up
 * 
 * @banq , then send a short message to banq
 * 
 * @author banq
 * 
 */

public class InFilterAuthor implements MessageRenderSpecification {
	private final static String module = InFilterPosterIP.class.getName();

	public final static String PRE_AUTHOR = "@";

	public ForumMessage render(ForumMessage message) {
		try {
			String username = getUsernameFromBody(message);
			if (username == null)
				return message;

			if (message.getDomainEvents() != null)
				message.getDomainEvents().sendShortMessage(message.getAccount(), username);
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	public String getUsernameFromBody(ForumMessage message) {
		MessageVO messageVO = message.getMessageVO();
		if (messageVO.getBody().indexOf(PRE_AUTHOR) == -1)
			return null;

		String buff2 = messageVO.getBody();
		String buff = buff2.substring(buff2.indexOf("@"), buff2.length() > 12 ? 12 : buff2.length());
		String[] as = buff.split("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]");
		if (as.length < 2)
			return null;
		return as[1];
	}

}
